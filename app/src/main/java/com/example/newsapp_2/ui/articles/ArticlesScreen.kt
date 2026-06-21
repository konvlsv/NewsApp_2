package com.example.newsapp_2.ui.articles

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.ui.common.components.ErrorScreen
import com.example.newsapp_2.ui.common.components.LoadingScreen
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun ArticlesScreen(
    onNavigateToDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel = hiltViewModel()
) {
    val articles = viewModel.userState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = modifier
    ) {
        when (val currentArticles = articles.value) {
            null -> LoadingScreen()
            else -> {
                ArticlesScreenContent(
                    onNavigateToDetails = onNavigateToDetails,
                    articles = currentArticles,
                    onRefresh = viewModel::refreshArticles,
                    isRefreshing = isRefreshing
                )
            }
        }
    }
}

@Composable
fun ArticlesScreenContent(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    onNavigateToDetails: (id: String) -> Unit,
    articles: List<Article>,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        modifier = modifier,
        onRefresh = onRefresh
    ) {
        LazyColumn() {
            items(articles.size) { index ->
                Text(
                    text = articles[index].title,
                    modifier = Modifier.clickable {
                        onNavigateToDetails(articles[index].id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesScreenContentPreview() {
    NewsApp_2Theme() {
        ArticlesScreenContent(
            onNavigateToDetails = {},
            onRefresh = {},
            isRefreshing = false,
            articles = listOf(
                Article("1", "Article 1"),
                Article("2", "Article 2"),
                Article("3", "Article 3"),
            )
        )
    }
}