package com.example.newsapp_2.ui.news

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp_2.R
import com.example.newsapp_2.domain.models.NewsCategory

@Composable
fun CategoriesTabs(
    selectedCategory: NewsCategory,
    onCategorySelected: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = NewsCategory.entries.toTypedArray()
    PrimaryScrollableTabRow(
        selectedTabIndex = selectedCategory.ordinal,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tabs = {
            categories.forEach { category ->
                Tab(
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) },
                    text = {
                        Text(
                            text = when (category) {
                                NewsCategory.ALL -> stringResource(R.string.category_all)
                                NewsCategory.BREAKING -> stringResource(R.string.category_breaking)
                                NewsCategory.BUSINESS -> stringResource(R.string.category_business)
                                NewsCategory.CRIME -> stringResource(R.string.category_crime)
                                NewsCategory.DOMESTIC -> stringResource(R.string.category_domestic)
                                NewsCategory.EDUCATION -> stringResource(R.string.category_education)
                                NewsCategory.ENTERTAINMENT -> stringResource(R.string.category_entertainment)
                                NewsCategory.ENVIRONMENT -> stringResource(R.string.category_environment)
                                NewsCategory.FOOD -> stringResource(R.string.category_food)
                                NewsCategory.HEALTH -> stringResource(R.string.category_health)
                                NewsCategory.LIFESTYLE -> stringResource(R.string.category_lifestyle)
                                NewsCategory.POLITICS -> stringResource(R.string.category_politics)
                                NewsCategory.SCIENCE -> stringResource(R.string.category_science)
                                NewsCategory.SPORTS -> stringResource(R.string.category_sports)
                                NewsCategory.TECHNOLOGY -> stringResource(R.string.category_technology)
                                NewsCategory.TOP -> stringResource(R.string.category_top)
                                NewsCategory.TOURISM -> stringResource(R.string.category_tourism)
                                NewsCategory.WORLD -> stringResource(R.string.category_world)
                                NewsCategory.OTHER -> stringResource(R.string.category_other)
                            }
                        )
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CategoriesTabsPreview() {
    CategoriesTabs(
        selectedCategory = NewsCategory.ALL,
        onCategorySelected = {}
    )
}