@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailmaster.ui.screen.TopScreen

// ここでナビゲーションのルーティングとかをしている
@Composable
fun MainHost() {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>(factory = MainViewModel.Factory)
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {},
                    actions = {
                        IconButton(
                            onClick = {
                                // 設定画面へ遷移
                            }
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        },
        
    ) {padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.TopScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable(Screen.TopScreen.name) {
                TopScreen(navController = navController, viewModel = mainViewModel)
            }
        }
    }
}