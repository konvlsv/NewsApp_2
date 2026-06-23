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
    val id: String? = null,
    val link: String? = null,
    val title: String? = null,
    val description: String? = null,
    val keywords: List<String>? = null,
    val creator: List<String>? = null,
    val language: String? = null,
    val country: List<String>? = null,
    val category: List<String>? = null,
    val pubDate: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null,
    @SerialName("source_name")
    val sourceName: String? = null,
)