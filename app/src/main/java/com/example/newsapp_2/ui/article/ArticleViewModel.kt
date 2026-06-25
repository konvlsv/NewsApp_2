package com.example.newsapp_2.ui.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.domain.usecases.GetArticle
import com.example.newsapp_2.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getArticle: GetArticle
) : ViewModel() {
    private val userId = checkNotNull(savedStateHandle.toRoute<Screens.Details>().id)

    val article: StateFlow<Article?> = getArticle.getArticleByIdStream(userId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}