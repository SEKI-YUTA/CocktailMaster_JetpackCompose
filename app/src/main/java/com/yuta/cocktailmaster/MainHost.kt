@file:OptIn(ExperimentalMaterial3Api::class)

package com.yuta.cocktailmaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yuta.cocktailmaster.data.AppContainer
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.ui.Screen
import com.yuta.cocktailmaster.ui.component.MyTopAppBar
import com.yuta.cocktailmaster.ui.component.SpotLight
import com.yuta.cocktailmaster.ui.component.TextAreaPosition
import com.yuta.cocktailmaster.ui.model.OnboardingItem
import com.yuta.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.yuta.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.yuta.cocktailmaster.ui.screen.TopScreen
import com.yuta.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.yuta.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.yuta.cocktailmaster.ui.viewmodels.MainViewModel
import com.yuta.cocktailmaster.ui.viewmodels.TopScreenViewModel
import com.yuta.cocktailmaster.util.AppUtil

@Composable
fun MainHost(
    appContainer: AppContainer,
    mainViewState: MainViewModel.MainViewModelViewState,
    networkConnectionStateChanged: (Boolean) -> Unit,
    onAddOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onEditOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onDeleteOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    setOnboardingItem: (item: OnboardingItem, index: Int) -> Unit,
    incrementOnboardingStep: () -> Unit,
    onMarkOnboardingFinished: (Boolean) -> Unit
) {
    val apiRepository = LocalApiRepository.current
    val ownedIngredientRepository = LocalOwnedIngredientRepository.current
    val navController = rememberNavController()
    var topAppBarSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    AppUtil.checkNetworkConnection(appContainer.context) {
        networkConnectionStateChanged(it)
    }
    Box {
        Scaffold(
            topBar = {
                Surface(shadowElevation = 4.dp) {
                    MyTopAppBar(
                        modifier = Modifier
                            .onGloballyPositioned {
                                topAppBarSize = it.size
                            },
                        navController = navController
                    )
                }
            }
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
                        factory = TopScreenViewModel.provideFactory()
                    )
                    val cocktailListButtonText = stringResource(
                        R.string.onboarding_cocktail_list_button
                    )
                    val addIngredientButtonText = stringResource(
                        R.string.onboarding_add_ingredient_button
                    )
                    val ownedIngredientListText = stringResource(
                        R.string.onboarding_owned_ingredient_list
                    )
                    TopScreen(
                        viewModel = topScreenViewModel,
                        isOwnedIngredientListLoading = mainViewState.isOwnedIngredientLoading,
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
                        onDeleteOwnedIngredient = onDeleteOwnedIngredient,
                        cocktailListButtonModifier = Modifier.onGloballyPositioned {
                            setOnboardingItem(
                                OnboardingItem(it.boundsInRoot(), cocktailListButtonText),
                                0
                            )
                        },
                        addIngredientButtonModifier = Modifier.onGloballyPositioned {
                            setOnboardingItem(
                                OnboardingItem(it.boundsInRoot(), addIngredientButtonText),
                                1
                            )
                        },
                        ownedIngredientListModifier = Modifier.onGloballyPositioned {
                            setOnboardingItem(
                                OnboardingItem(
                                    it.boundsInRoot(),
                                    ownedIngredientListText,
                                    TextAreaPosition.ABOVE
                                ),
                                2
                            )
                        }
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
            if (!mainViewState.isNetworkConnected) {
                Popup() {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AlertDialog(
                            title = {
                                Text(text = "ネット環境を確認してください")
                            },
                            onDismissRequest = {},
                            confirmButton = {}
                        )
                    }
                }
            }
        }

        val onboardingState = mainViewState.onboardingState
        if (!mainViewState.isOnboardingFinished &&
            onboardingState.currentOnboardingStep < onboardingState.items.size &&
            mainViewState.isAppStatusRead
        ) {
            val item = onboardingState.items[onboardingState.currentOnboardingStep]
            println(
                "onboardingState.currentOnboardingStep: ${onboardingState.currentOnboardingStep}"
            )
            println("onboardingState.items.size - 1: ${onboardingState.items.size - 1}")
            SpotLight(
                rect = item.pos,
                text = item.text,
                textAreaPosition = item.textAreaPosition,
                isLast = onboardingState.currentOnboardingStep == onboardingState.items.size - 1,
                onAreaTapped = {
                    incrementOnboardingStep()
                },
                onMarkOnboardingFinished = {
                    onMarkOnboardingFinished(it)
                }
            )
        }
    }
}
