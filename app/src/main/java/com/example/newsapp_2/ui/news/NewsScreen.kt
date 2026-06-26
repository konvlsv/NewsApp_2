package com.example.newsapp_2.ui.news

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.newsapp_2.domain.models.NewsCategory
import com.example.newsapp_2.ui.common.components.LoadingScreen
import com.example.newsapp_2.ui.common.preview.getPreviewNews
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun ArticlesScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
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
        val currentArticles = articles.value
        when {
            currentArticles.isEmpty() -> LoadingScreen()
            else -> {
                ArticlesScreenContent(
                    articles = currentArticles,
                    onRefresh = viewModel::refreshArticles,
                    isRefreshing = isRefreshing,
                    selectedCategory = NewsCategory.ALL,
                    onCategorySelected = {}
                )
            }
        }
    }
}

@Composable
fun ArticlesScreenContent(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    articles: List<Article>,
    selectedCategory: NewsCategory,
    onCategorySelected: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        modifier = modifier.fillMaxSize(),
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                CategoriesTabs(
                    selectedCategory = selectedCategory,
                    onCategorySelected = onCategorySelected
                )
            }
            items(articles.size) { index ->
                ArticleCard(
                    article = articles[index],
                    onOpenInBrowser = { },
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
            onRefresh = {},
            isRefreshing = false,
            articles = getPreviewNews(),
            selectedCategory = NewsCategory.ALL,
            onCategorySelected = {}
        )
    }
}