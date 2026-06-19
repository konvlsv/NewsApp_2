package com.example.newsapp_2.data.source.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("article_id") val id: String = "0",
    val title: String = "Empty title",
)