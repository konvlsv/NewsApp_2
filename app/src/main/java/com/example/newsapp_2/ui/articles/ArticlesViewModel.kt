package com.example.newsapp_2.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.usecases.GetArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticles: GetArticles
): ViewModel() {
    val userState: StateFlow<List<Article>> = getArticles.getArticlesStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun refreshArticles() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getArticles.refreshArticles()
                .onSuccess {
                    _isRefreshing.value = false
                }.onFailure {exception ->
                    _isRefreshing.value = false
                    val errorText = when (exception) {
                        is java.io.IOException -> "Нет подключения к интернету"
                        else -> "Не удалось обновить новости: ${exception.localizedMessage}"
                    }
                    _errorMessage.emit(errorText)
                }
        }
    }
    init {
        viewModelScope.launch {
            getArticles.refreshArticles()
        }
    }
}