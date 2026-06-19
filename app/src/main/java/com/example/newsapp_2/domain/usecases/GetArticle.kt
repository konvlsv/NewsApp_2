package com.example.newsapp_2.domain.usecases

import com.example.newsapp_2.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticle @Inject constructor(
    private val articleRepository: ArticleRepository
){
    fun getArticleByIdStream(id: String) = articleRepository.getArticleByIdStream(id)
}