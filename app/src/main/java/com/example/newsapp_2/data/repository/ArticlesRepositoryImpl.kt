package com.example.newsapp_2.data.repository

import com.example.newsapp_2.data.database.dao.NewsDao
import com.example.newsapp_2.data.database.model.ArticleEntity
import com.example.newsapp_2.data.network.NewsNetworkDataSource
import com.example.newsapp_2.data.network.model.NetworkArticle
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val network: NewsNetworkDataSource
) : ArticleRepository {
    override fun getArticlesStream(): Flow<List<Article>> {
        return newsDao.getArticles().map {
            it.toArticles()
        }
    }

    override suspend fun refreshArticles(): Result<Unit> {
        return try {
            val articleEntities = network.getArticles().toArticleEntities()
            newsDao.insertArticles(articleEntities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getArticleByIdStream(id: String): Flow<Article?> {
        return newsDao.getArticleById(id).map {
            it?.toArticle()
        }
    }
}


private fun List<NetworkArticle>.toArticleEntities(): List<ArticleEntity> {
    return this
        .filter { networkArticle ->
            !networkArticle.id.isNullOrBlank()
                    && !networkArticle.link.isNullOrBlank()
                    && !networkArticle.title.isNullOrBlank()
                    && !networkArticle.description.isNullOrBlank()
                    && !networkArticle.imageUrl.isNullOrBlank()
        }
        .map { networkArticle ->
            ArticleEntity(
                id = networkArticle.id!!,
                link = networkArticle.link!!,
                title = networkArticle.title!!,
                description = networkArticle.description!!,
                keywords = networkArticle.keywords ?: emptyList(),
                creator = networkArticle.creator ?: emptyList(),
                language = networkArticle.language ?: "",
                country = networkArticle.country ?: emptyList(),
                category = networkArticle.category ?: emptyList(),
                pubDate = networkArticle.pubDate ?: "",
                imageUrl = networkArticle.imageUrl!!,
                sourceName = networkArticle.sourceName ?: ""
            )
        }
}

private fun List<ArticleEntity>.toArticles(): List<Article> {
    return map { it.toArticle() }
}

private fun ArticleEntity.toArticle(): Article {
    return Article(
        id = id,
        link = link,
        title = title,
        description = description,
        keywords = keywords,
        creator = creator,
        language = language,
        country = country,
        category = category,
        pubDate = pubDate,
        imageUrl = imageUrl,
        sourceName = sourceName,
    )
}