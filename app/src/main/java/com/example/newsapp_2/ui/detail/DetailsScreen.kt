package com.example.newsapp_2.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp_2.ui.articles.ArticlesScreenContent
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val userId = viewModel.userId
    Box(
        modifier = modifier
    ){
        DetailsScreenContent(
            onBack = onBack,
            userId = userId
        )
    }
}

@Composable
fun DetailsScreenContent(
    userId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = "User ID: $userId"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview(){
    NewsApp_2Theme() {
        DetailsScreenContent(
            onBack = {},
            userId = 1
        )
    }
}