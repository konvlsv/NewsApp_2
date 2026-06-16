package com.example.newsapp_2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp_2.ui.articles.ArticlesScreen
import com.example.newsapp_2.ui.detail.DetailsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold() { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Articles,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            composable<Screens.Articles>{
                ArticlesScreen(
                    onNavigateToDetails = { id ->
                        navController.navigate(Screens.Details(id))
                    }
                )
            }
            composable<Screens.Details>{
                DetailsScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}