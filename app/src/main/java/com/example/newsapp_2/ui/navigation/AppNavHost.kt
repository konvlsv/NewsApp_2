package com.example.newsapp_2.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp_2.ui.common.components.NewsBottomBar
import com.example.newsapp_2.ui.news.ArticlesScreen
import com.example.newsapp_2.ui.subscribe.SubscribeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NewsBottomBar(
                destinations = TopLevelDestination.entries,
                currentDestination = currentDestination,
                onNavigateToDestination = { destination ->
                    navController.navigate(destination.route) {
                        // Очищаем стек до стартового экрана, чтобы избежать накопления вкладок
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Избегаем повторного открытия одного и того же экрана при множественном клике
                        launchSingleTop = true
                        // Восстанавливаем состояние экрана при повторном нажатии
                        restoreState = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = TopLevelDestination.NEWS.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(TopLevelDestination.NEWS.route){
                ArticlesScreen()
            }
            composable(TopLevelDestination.SUBSCRIBE.route){
                SubscribeScreen()
            }
        }
    }
}