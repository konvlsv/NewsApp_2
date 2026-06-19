package com.example.newsapp_2.domain.usecases

import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticles @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    fun getArticlesStream(): Flow<List<Article>?> = articleRepository.getArticlesStream()

    suspend fun refreshArticles() = articleRepository.refreshArticles()
}