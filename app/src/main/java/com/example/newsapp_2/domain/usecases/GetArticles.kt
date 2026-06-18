package com.example.newsapp_2.domain.usecases

import com.example.newsapp_2.domain.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetArticles @Inject constructor() {
    fun getArticlesStream(): Flow<List<Article>?>  {
        val articles = listOf(
            Article(1, "Article 1"),
            Article(2, "Article 2"),
            Article(3, "Article 3"),
        )
        return flowOf(articles)
    }

    suspend fun refreshArticles() {
        // todo: refresh articles
    }
}