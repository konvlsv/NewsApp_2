package com.example.newsapp_2.data.network.di

import com.example.newsapp_2.data.network.NewsNetworkDataSource
import com.example.newsapp_2.data.network.retrofit.RetrofitNewsNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourcesModule {
    @Binds
    @Singleton
    fun bindNewsNetworkDataSource(impl: RetrofitNewsNetwork): NewsNetworkDataSource
}