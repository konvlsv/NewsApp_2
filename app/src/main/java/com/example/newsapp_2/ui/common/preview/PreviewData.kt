package com.example.newsapp_2.ui.common.preview

import com.example.newsapp_2.domain.models.Article

fun getPreviewNews(): List<Article>{
    val articles = mutableListOf<Article>()
    for (i in 1..10){
        articles.add(getPreviewArticle().copy(id = i.toString()))
    }
    return articles
}

fun getPreviewArticle(): Article = Article(
    id = "1",
    link = "link",
    title = "title",
    description = "description",
    keywords = listOf("keyword"),
    creator = listOf("creator"),
    language = "language",
    country = listOf("country"),
    category = listOf("category"),
    pubDate = "pubDate",
    imageUrl = "imageUrl",
    sourceName = "sourceName"
)