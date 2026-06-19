package com.example.newsapp_2.data.source.remote

import retrofit2.http.GET

interface ArticleApiService {

    @GET("api/1/latest")
    suspend fun getArticles(): ArticlesResponse
}