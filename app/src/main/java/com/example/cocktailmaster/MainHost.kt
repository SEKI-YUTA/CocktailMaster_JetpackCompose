@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailmaster.data.repository.CocktailApiRepository_Impl
import com.example.cocktailmaster.data.repository.OwnedLiqueurRepository_Impl
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.MyTopAppBar
import com.example.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.example.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.example.cocktailmaster.ui.screen.TopScreen
import com.example.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.MainViewModel
import com.example.cocktailmaster.ui.viewmodels.TopScreenViewModel

// ここでナビゲーションのルーティングとかをしている
@Composable
fun MainHost() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>(factory = MainViewModel.provideFactory(context = context))
    val ownedIngredientList = mainViewModel.ownedCocktailIngredients.collectAsState().value

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
                val topScreenViewModel = viewModel<TopScreenViewModel>(
                    factory =TopScreenViewModel.provideFactory(
                        ownedLiqueurRepository = OwnedLiqueurRepository_Impl(context = context),
                        onUpdateOwnedLiqueur = {
                            mainViewModel.updateOwnedIngredientList(it)
                        }
                    )
                )
                TopScreen(
                    viewModel = topScreenViewModel,
                    navigateToAddIngredient = {
                        navController.navigate(Screen.AddCocktailIngredientScreen.name) {
                            launchSingleTop = true
                        }
                    },
                    navigateToCraftableCocktail = {
                        navController.navigate(Screen.CraftableCocktailListScreen.name) {
                            launchSingleTop = true
                        }
                    },
                    onDeleteOwnedIngredient = {
                        mainViewModel.deleteOwnedIngredient(it)
                    },
                    onEditOwnedIngredient = {
                        mainViewModel.editOwnedIngredient(it)
                    }
                )
            }
            composable(Screen.AddCocktailIngredientScreen.name) {
                val addCocktailIngredientScreenViewModel = viewModel<AddCocktailIngredientScreenViewModel>(
                    factory = AddCocktailIngredientScreenViewModel.provideFactory()
                )
                AddCocktailIngredientScreen(
                    viewModel = addCocktailIngredientScreenViewModel,
                    ownedIngredientList = ownedIngredientList,
                    onAddOwnedIngredient = {
                        mainViewModel.addOwnedIngredient(it)
                    }
                )
            }
            composable(Screen.CraftableCocktailListScreen.name) {
                val craftableCocktailListScreenViewModel = viewModel<CraftableCocktailListScreenViewModel>(
                    factory = CraftableCocktailListScreenViewModel.provideFactory(
                        apiRepository = CocktailApiRepository_Impl(),
                        ingredientList = ownedIngredientList
                    )
                )
                CraftableCocktailListScreen(viewModel = craftableCocktailListScreenViewModel)
            }
        }
    }
}