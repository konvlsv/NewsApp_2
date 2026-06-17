package com.example.newsapp_2.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {

}

@Composable
fun DetailsScreenContent(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
){

}

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview(){
    NewsApp_2Theme() {
        DetailsScreenContent(
            onBack = {}
        )
    }
}