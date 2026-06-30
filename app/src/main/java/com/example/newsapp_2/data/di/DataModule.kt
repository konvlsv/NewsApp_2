package com.example.newsapp_2.data.di

import com.example.newsapp_2.data.util.ConnectivityManagerNetworkMonitor
import com.example.newsapp_2.data.util.NetworkMonitor
import com.example.newsapp_2.data.util.TimeZoneBroadcastMonitor
import com.example.newsapp_2.data.util.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    internal abstract fun bindsTimeZoneMonitor(
        timeZoneMonitor: TimeZoneBroadcastMonitor,
    ): TimeZoneMonitor
}