package com.example.newsapp_2.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsDataResponse(
    val status: String,
    val totalResults: Int,
    val results: List<NetworkArticle>,
    val nextPage: String? = null
)

@Serializable
data class NetworkArticle(
    @SerialName("article_id")
    val id: String,
    val link: String,
    val title: String,
    val description: String,
    val keywords: List<String>,
    val creator: List<String>,
    val language: String,
    val country: List<String>,
    val category: List<String>,
    val pubDate: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("source_name")
    val sourceName: String,
)