package com.example.newsapp_2.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Articles : Screens

    @Serializable
    data object Details : Screens
}