package com.example.newsapp_2.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    message: String = "Loading",
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}