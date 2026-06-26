package com.example.newsapp_2.ui.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp_2.domain.models.Article
import com.example.newsapp_2.ui.common.components.ArticleImage
import com.example.newsapp_2.ui.common.preview.getPreviewArticle
import com.example.newsapp_2.ui.common.theme.AppTheme

@Composable
fun ArticleCard(
    article: Article,
    onOpenInBrowser: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(AppTheme.dimens.paddingLarge)
    ) {
        ArticleImage(article.imageUrl)
        Text(text = article.sourceName)
        Text(text = article.title)
        Text(text = article.description)
        Text(text = article.pubDate)
        Button(onClick = onOpenInBrowser) {
            Text(text = "Open in browser")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = getPreviewArticle(),
        onOpenInBrowser = {},
    )
}