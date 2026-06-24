package com.example.newsapp_2.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val link: String,
    val title: String,
    val description: String,
    val keywords: List<String>,
    val creator: List<String>,
    val language: String,
    val country: List<String>,
    val category: List<String>,
    val pubDate: String,
    val imageUrl: String,
    val sourceName: String,
)
