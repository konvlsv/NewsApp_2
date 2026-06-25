package com.example.newsapp_2.domain.repository

import com.example.newsapp_2.domain.models.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsStream(): Flow<List<Article>>
    suspend fun refreshNews(): Result<Unit>
    fun getArticleByIdStream(id: String): Flow<Article?>
}