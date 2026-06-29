package com.example.newsapp_2.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import androidx.tracing.trace
import com.example.newsapp_2.hilt.AppDispatchers
import com.example.newsapp_2.hilt.Dispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// реализация интерфейса NetworkMonitor для проверки подключения к интернету
class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher //Кастомный qualifier для прокидывания Dispatcher
) : NetworkMonitor {
    // Flow для проверки подключения к интернету, на него подписываются слушатели и реагируют на изменения
    override val isOnline: Flow<Boolean> = callbackFlow {

        trace("NetworkMonitor.callbackFlow") {
            //ConnectivityManager — это главный системный сервис для управления сетевыми соединениями (Wi-Fi, Mobile Data, Ethernet)
            val connectivityManager = context.getSystemService<ConnectivityManager>()
            if (connectivityManager == null) {
                channel.trySend(false) // Отправляем в поток, что интернета нет
                channel.close()        // Закрываем поток данных
                return@callbackFlow    // Выходим из лямбды
                // это нужно для корректной обработки ситуации, если системный сервис недоступен,
                // не "уронив" приложение (краша не будет), а просто закроется поток с состоянием false.
            }

            val callback = object : NetworkCallback() {
                //хранит имеющиеся сетевые соединения
                private val callbackNetworks = mutableSetOf<Network>()

                override fun onAvailable(network: Network) {
                    callbackNetworks += network // добавление доступной сети
                    channel.trySend(true) // отправка в поток true, что есть доступ к интернету
                }

                override fun onLost(network: Network) {
                    callbackNetworks -= network // удаление потерянной сети
                    channel.trySend( // отправка в поток false, что нет доступа к интернету
                        callbackNetworks.isNotEmpty() // проверка наличия доступных сетей
                    )
                }
            }

            // регистрацию слушателя (callback) в системе Android
            // для получения уведомлений о появлении или отсутствии сетевых соединений
            trace("NetworkMonitor.registerNetworkCallback") {
                // создается «фильтр». Если этого не сделать, система будет уведомлять о любых изменениях сетей (например, если включился Bluetooth-модем, который не дает интернета).
                val request = Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // Это ключевой флаг. Сообщает Android уведомлять только о тех сетях, которые теоретически могут открывать веб-сайты (имеют доступ к интернету).
                    .build()
                connectivityManager.registerNetworkCallback(request, callback)
            }

            // сразу отдает состояние сети не дожидаясь первого изменения
            channel.trySend(connectivityManager.isCurrentlyConnected())

            // против утечек памяти
            awaitClose { // ждет завершения потока, потом выполняет код внутри блока
                connectivityManager.unregisterNetworkCallback(callback) // удалить слушатель (callback) из системы Android
            }
        }
    }
        .flowOn(ioDispatcher) // гарантирует что поток будет работать в IO диспетчере
        .conflate() // выбрасывает промежуточные значения при быстром изменении

    // проверяет состояние сети «прямо сейчас»
    private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
        // Получаем описание активной сети (если она есть)
        val networkCapabilities = getNetworkCapabilities(activeNetwork) ?: return false
        // Проверяем, есть ли у этой сети доступ в интернет
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
