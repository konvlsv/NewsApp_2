package com.example.newsapp_2.data.repository

import com.example.newsapp_2.data.database.dao.NewsDao
import com.example.newsapp_2.data.database.model.ArticleEntity
import com.example.newsapp_2.data.network.NewsNetworkDataSource
import com.example.newsapp_2.data.network.model.NetworkArticle
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val network: NewsNetworkDataSource
) : NewsRepository {
    override fun getNewsStream(): Flow<List<Article>> {
        return newsDao.getAllNews().map {
            it.toArticles()
        }
    }

    override suspend fun refreshNews(): Result<Unit> {
        return try {
            val articleEntities = network.getNews().toArticleEntities()
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
    return this.mapNotNull { networkArticle ->
        val id = networkArticle.id
        val link = networkArticle.link
        val title = networkArticle.title
        val description = networkArticle.description
        val imageUrl = networkArticle.imageUrl

        if (id.isNullOrBlank() ||
            link.isNullOrBlank() ||
            title.isNullOrBlank() ||
            description.isNullOrBlank() ||
            imageUrl.isNullOrBlank()
        ) {
            return@mapNotNull null
        }

        ArticleEntity(
            id = id,
            link = link,
            title = title,
            description = description,
            keywords = networkArticle.keywords ?: emptyList(),
            creator = networkArticle.creator ?: emptyList(),
            language = networkArticle.language ?: "",
            country = networkArticle.country ?: emptyList(),
            category = networkArticle.category ?: emptyList(),
            pubDate = networkArticle.pubDate ?: "",
            imageUrl = imageUrl,
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