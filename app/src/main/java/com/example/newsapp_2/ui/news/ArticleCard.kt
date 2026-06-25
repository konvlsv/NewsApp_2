package com.example.newsapp_2.ui.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.ui.common.preview.getPreviewArticle

@Composable
fun ArticleCard(
    article: Article,
    onOpenInBrowser: () -> Unit,
    onOpenDetails: () -> Unit
) {

}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = getPreviewArticle(),
        onOpenInBrowser = {},
        onOpenDetails = {}
    )
}