package com.example.newsapp_2.data.network

import com.example.newsapp_2.data.network.model.NetworkArticle

interface NewsNetworkDataSource {
    suspend fun getNews(): List<NetworkArticle>
}