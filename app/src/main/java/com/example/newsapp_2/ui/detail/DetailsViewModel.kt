package com.example.newsapp_2.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.newsapp_2.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val detailRoute = savedStateHandle.toRoute<Screens.Details>()
    val userId = detailRoute.id
}