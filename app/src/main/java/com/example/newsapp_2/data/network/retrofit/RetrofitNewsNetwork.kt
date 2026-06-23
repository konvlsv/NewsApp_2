package com.example.newsapp_2.data.network.retrofit

import androidx.tracing.trace
import com.example.newsapp_2.BuildConfig
import com.example.newsapp_2.data.network.NewsNetworkDataSource
import com.example.newsapp_2.data.network.model.NetworkArticle
import com.example.newsapp_2.data.network.model.NetworkNews
import dagger.Lazy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitArticlesApi {
    @GET("api/1/latest")
    suspend fun getArticles(
        @Query("apikey") apiKey: String
    ): NetworkResponse<NetworkNews>
}

@Serializable
private data class NetworkResponse<T>(
    val data: T,
)

private const val BASE_URL: String = BuildConfig.NEWS_DATA_BASE_URL
private const val API_KEY: String = BuildConfig.NEWS_DATA_API_KEY

@Singleton
internal class RetrofitNewsNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Lazy<Call.Factory>,
): NewsNetworkDataSource {

    private val networkApi = trace("RetrofitNewsNetwork") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitArticlesApi::class.java)
    }

    override suspend fun getArticles(): List<NetworkArticle> =
        networkApi.getArticles(apiKey = API_KEY).data.results
}
