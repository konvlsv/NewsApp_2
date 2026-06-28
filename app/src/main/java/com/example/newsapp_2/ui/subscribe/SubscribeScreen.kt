package com.example.newsapp_2.ui.subscribe

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp_2.ui.common.theme.NewsApp_2Theme

@Composable
fun SubscribeScreen(
    modifier: Modifier = Modifier,
    viewModel: SubscribeViewModel = hiltViewModel()
) {
    val text by viewModel.text.collectAsState()
    SubscribeScreenContent(text, modifier)
}

@Composable
fun SubscribeScreenContent(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun SubscribeScreenPreview() {
    NewsApp_2Theme() {
        SubscribeScreenContent(
            text = "Subscribe"
        )
    }
}