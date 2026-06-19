package com.example.newsapp_2.domain.repository

import com.example.newsapp_2.domain.models.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticlesStream(): Flow<List<Article>?>
    suspend fun refreshArticles()
}