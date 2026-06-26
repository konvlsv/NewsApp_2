package com.example.newsapp_2.domain.usecases

import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    fun getNewsStream(): Flow<List<Article>> = newsRepository.getNewsStream()

    suspend fun refreshNews(): Result<Unit> = newsRepository.refreshNews()
}