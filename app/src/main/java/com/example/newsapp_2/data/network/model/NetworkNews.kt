package com.example.newsapp_2.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkNews(
    val status: String,
    val totalResults: Int,
    val results: List<NetworkArticle>
)

@Serializable
data class NetworkArticle(
    @SerialName("article_id") val id: String = "0",
    val title: String = "Empty title",
)