package com.example.newsapp_2.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.usecases.GetArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticles: GetArticles
): ViewModel() {
    val userState: StateFlow<List<Article>?> = getArticles.getArticlesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun refreshArticles() {
        viewModelScope.launch {
            getArticles.refreshArticles()
        }
    }
    init {
        viewModelScope.launch {
            getArticles.refreshArticles()
        }
    }
}