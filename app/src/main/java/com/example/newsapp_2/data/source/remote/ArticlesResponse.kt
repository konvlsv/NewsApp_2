package com.example.newsapp_2.data.source.remote

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val results: List<ArticleDto>
)