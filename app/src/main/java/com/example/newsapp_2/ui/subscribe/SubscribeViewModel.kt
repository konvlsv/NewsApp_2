package com.example.newsapp_2.ui.subscribe

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(): ViewModel() {
    val text: StateFlow<String> = MutableStateFlow("Subscribe").asStateFlow()
}