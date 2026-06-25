package com.example.newsapp_2.domain.usecases

import com.example.newsapp_2.domain.repository.NewsRepository
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val newsRepository: NewsRepository
){
    fun getArticleByIdStream(id: String) = newsRepository.getArticleByIdStream(id)
}