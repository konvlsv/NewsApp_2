package com.example.newsapp_2.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val article = viewModel.article.collectAsState()
    Box(
        modifier = modifier
    ) {
        val currentArticle = article.value
        if (currentArticle == null) {
            CircularProgressIndicator()
        } else {
            DetailsScreenContent(
                onBack = onBack,
                article = currentArticle
            )
        }
    }
}

@Composable
fun DetailsScreenContent(
    article: Article,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Article ID: ${article.id}"
        )
        Text(
            text = "Article Title: ${article.title}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    NewsApp_2Theme() {
        DetailsScreenContent(
            onBack = {},
            article = Article(
                id = "1",
                link = "link",
                title = "title",
                description = "description",
                keywords = listOf("keyword"),
                creator = listOf("creator"),
                language = "language",
                country = listOf("country"),
                category = listOf("category"),
                pubDate = "pubDate",
                imageUrl = "imageUrl",
                sourceName = "sourceName"
            )
        )
    }
}