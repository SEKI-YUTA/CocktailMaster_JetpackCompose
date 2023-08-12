@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController

@Composable
fun MyTopAppBar(navController: NavHostController) {
    TopAppBar(
        navigationIcon = {
//            if(navController.backQueue.size > 2) {
//                IconButton(
//                    onClick = {
//                        navController.popBackStack()
//                    }
//                ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = null)
//                }
//            }
        },
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