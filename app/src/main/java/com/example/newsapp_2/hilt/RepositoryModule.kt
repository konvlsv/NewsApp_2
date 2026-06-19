package com.example.newsapp_2.hilt

import com.example.newsapp_2.data.repository.ArticlesRepositoryImpl
import com.example.newsapp_2.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArticleRepository(
        articlesRepositoryImpl: ArticlesRepositoryImpl
    ): ArticleRepository
}