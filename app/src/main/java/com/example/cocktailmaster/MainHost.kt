@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.MyTopAppBar
import com.example.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.example.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.example.cocktailmaster.ui.screen.TopScreen
import com.example.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.MainViewModel
import com.example.cocktailmaster.ui.viewmodels.TopScreenViewModel

@Composable
fun MainHost(
    mainViewState: MainViewModel.MainViewModelViewState,
    onAddOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onEditOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onDeleteOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
) {
    val context = LocalContext.current
    val apiRepository = LocalApiRepository.current
    val ownedIngredientRepository = LocalOwnedIngredientRepository.current
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                MyTopAppBar(
                    navController = navController
                )
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.TopScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable(Screen.TopScreen.name) {
                val topScreenViewModel = viewModel<TopScreenViewModel>(
                    factory = TopScreenViewModel.provideFactory(
                        ownedIngredientRepository = ownedIngredientRepository
                    )
                )
                TopScreen(
                    viewModel = topScreenViewModel,
                    ownedIngredientList = mainViewState.ownedIngredientList,
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
                    onEditOwnedIngredient = onEditOwnedIngredient,
                    onDeleteOwnedIngredient = onDeleteOwnedIngredient
                )
            }
            composable(Screen.AddCocktailIngredientScreen.name) {
                val addCocktailIngredientScreenViewModel =
                    viewModel<AddCocktailIngredientScreenViewModel>(
                        factory = AddCocktailIngredientScreenViewModel.provideFactory(
                            apiRepository = apiRepository
                        )
                    )
                AddCocktailIngredientScreen(
                    viewModel = addCocktailIngredientScreenViewModel,
                    ownedIngredientList = mainViewState.ownedIngredientList,
                    onAddOwnedIngredient = onAddOwnedIngredient
                )
            }
            composable(Screen.CraftableCocktailListScreen.name) {
                val craftableCocktailListScreenViewModel =
                    viewModel<CraftableCocktailListScreenViewModel>(
                        factory = CraftableCocktailListScreenViewModel.provideFactory(
                            apiRepository = apiRepository,
                            ingredientList = mainViewState.ownedIngredientList
                        )
                    )
                CraftableCocktailListScreen(viewModel = craftableCocktailListScreenViewModel)
            }
        }
    }
}