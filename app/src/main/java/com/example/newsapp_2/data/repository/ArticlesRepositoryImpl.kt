package com.example.newsapp_2.data.repository

import com.example.newsapp_2.data.source.local.ArticleDao
import com.example.newsapp_2.data.source.local.ArticleEntity
import com.example.newsapp_2.data.source.remote.ArticleApiService
import com.example.newsapp_2.data.source.remote.ArticleDto
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleApiService: ArticleApiService
): ArticleRepository {
    override fun getArticlesStream(): Flow<List<Article>?> {
        return articleDao.getArticles().map {
            it?.toArticles()
        }
    }

    override suspend fun refreshArticles() {
        try {
            val articleEntities = articleApiService.getArticles().results.toArticleEntities()
            articleDao.insertArticles(articleEntities)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun getArticleByIdStream(id: String): Flow<Article?> {
        return articleDao.getArticleById(id).map {
            it?.toArticle()
        }
    }
}

private fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title
    )
}

private fun List<ArticleDto>.toArticleEntities(): List<ArticleEntity> {
    return map { it.toArticleEntity() }
}

private fun List<ArticleEntity>.toArticles(): List<Article> {
    return map { it.toArticle() }
}

private fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        title = title
    )
}