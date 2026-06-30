package com.example.newsapp_2.data.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.tracing.trace
import com.example.newsapp_2.data.di.AppDispatchers
import com.example.newsapp_2.data.di.ApplicationScope
import com.example.newsapp_2.data.di.Dispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinTimeZone
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton  // singleton необходим из-за работы appScope что может привести к утечкам памяти
class TimeZoneBroadcastMonitor @Inject constructor(
    @ApplicationContext private val appContext: Context,
    @ApplicationScope appScope: CoroutineScope,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : TimeZoneMonitor {
    override val currentTimeZone: SharedFlow<TimeZone> = callbackFlow {
        trySend(TimeZone.currentSystemDefault())  //сразу выдает текущее значение не дожидаясь изменения

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action != Intent.ACTION_TIMEZONE_CHANGED) return // если событие не смена часового пояса - return
                val zoneIdFromIntent = if (VERSION.SDK_INT < VERSION_CODES.R) {
                    null // если версия android меньше 30 null, потому что раньше android не передавал часовой пояс
                } else {
                    // пытаемся достать строку (например, "Europe/Moscow")
                    intent.getStringExtra(Intent.EXTRA_TIMEZONE)?.let { timeZoneId ->
                        //Превращает строку в стандартный Java-объект часового пояса
                        val zoneId = ZoneId.of(timeZoneId, ZoneId.SHORT_IDS)
                        // Конвертация в формат библиотеки kotlinx-datetime
                        zoneId.toKotlinTimeZone()
                    }
                }

                // отправка полученного часового пояса или вызов из системы уже сформированного системой часового пояса
                trySend(zoneIdFromIntent ?: TimeZone.currentSystemDefault())
            }
        }
        trace("TimeZoneBroadcastReceiver.register") {
            // физическое подключение приложения к «эфиру» системных уведомлений Android
            appContext.registerReceiver(
                receiver,
                IntentFilter(//указывает системе, какие именно сообщения будут прослушиваться.
                    Intent.ACTION_TIMEZONE_CHANGED
                )
            )
        }

        // дублирование для защиты от бага если время изменилось а регистрация ресивера еще не прошла
        trySend(TimeZone.currentSystemDefault())

        awaitClose { // отписка ресивера если корутина завершилась
            appContext.unregisterReceiver(receiver)
        }
    }
        .distinctUntilChanged() // пропускает новое значение только в том случае, если оно отличается от предыдущего
        .conflate() // удаляет промежуточные (старые) значения и оставляет только последнее.
        .flowOn(ioDispatcher) // перенос на фоновый поток (IO)
        // превращает поток из Cold (холодного) в Hot (горячий) что экономит производительность
        .shareIn(appScope, SharingStarted.WhileSubscribed(5_000), 1)
}