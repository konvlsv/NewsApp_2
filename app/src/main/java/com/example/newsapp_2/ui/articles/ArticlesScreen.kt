package com.example.newsapp_2.ui.articles

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp_2.ui.theme.NewsApp_2Theme

@Composable
fun ArticlesScreen(
    onNavigateToDetails: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArticlesViewModel = hiltViewModel()
){

}

@Composable
fun ArticlesScreenContent(
    onNavigateToDetails: (id: Int) -> Unit,
    modifier: Modifier = Modifier,
){

}

@Preview(showBackground = true)
@Composable
fun ArticlesScreenContentPreview(){
    NewsApp_2Theme() {
        ArticlesScreenContent(
            onNavigateToDetails = {}
        )
    }
}