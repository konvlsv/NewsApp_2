package com.example.newsapp_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.example.newsapp_2.ui.navigation.AppNavHost
import com.example.newsapp_2.ui.theme.NewsApp_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsApp_2Theme {
                Surface() {
                    AppNavHost()
                }
            }
        }
    }
}