package com.example.newsapp_2.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp_2.data.database.NewsDatabase
import com.example.newsapp_2.data.database.dao.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase{
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase): NewsDao{
        return newsDatabase.newsDao
    }
}