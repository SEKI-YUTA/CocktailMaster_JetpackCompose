@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailmaster.ui.component.MyTopAppBar
import com.example.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.example.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.example.cocktailmaster.ui.screen.TopScreen
import com.example.cocktailmaster.ui.viewmodels.MainViewModel

// ここでナビゲーションのルーティングとかをしている
@Composable
fun MainHost() {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>(factory = MainViewModel.Factory)
    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                MyTopAppBar(
                    navController = navController
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
            composable(Screen.AddCocktailIngredientScreen.name) {
                AddCocktailIngredientScreen(navController = navController, viewModel = mainViewModel)
            }
            composable(Screen.CraftableCocktailListScreen.name) {
                CraftableCocktailListScreen(navController = navController, viewModel = mainViewModel)
            }
        }
    }
}