package com.example.newsapp_2.data.repository

import com.example.newsapp_2.data.source.local.ArticleDao
import com.example.newsapp_2.data.source.local.ArticleEntity
import com.example.newsapp_2.data.source.network.retrofit.RetrofitArticlesApi
import com.example.newsapp_2.data.network.model.NetworkArticle
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleApiService: RetrofitArticlesApi
): ArticleRepository {
    override fun getArticlesStream(): Flow<List<Article>?> {
        return articleDao.getArticles().map {
            it?.toArticles()
        }
    }

    override suspend fun refreshArticles(): Result<Unit> {
        return try {
            val articleEntities = articleApiService.getArticles().results.toArticleEntities()
            articleDao.insertArticles(articleEntities)
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
    override fun getArticleByIdStream(id: String): Flow<Article?> {
        return articleDao.getArticleById(id).map {
            it?.toArticle()
        }
    }
}

private fun NetworkArticle.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title
    )
}

private fun List<NetworkArticle>.toArticleEntities(): List<ArticleEntity> {
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