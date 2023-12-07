package com.yuta.cocktailmaster

import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.yuta.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.yuta.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.yuta.cocktailmaster.ui.screen.TopScreen
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.yuta.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.yuta.cocktailmaster.ui.viewmodels.TopScreenViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@ExperimentalRoborazziApi
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel6Pro)
class FirstRoborazzi {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val roborazziOptions = RoborazziOptions(
        compareOptions = RoborazziOptions.CompareOptions(
            changeThreshold = 0f
        )
    )

    @Test
    fun captureTopScreen() {
        composeTestRule.setContent {
            FakeRepositoryProvider {
                val viewModel = TopScreenViewModel()
                CocktailMasterTheme {
                    Surface {
                        TopScreen(
                            viewModel = viewModel,
                            isOwnedIngredientListLoading = false,
                            ownedIngredientList = emptyList(),
                            navigateToCraftableCocktail = {},
                            navigateToAddIngredient = {},
                            onDeleteOwnedIngredient = {},
                            onEditOwnedIngredient = {},
                        )
                    }
                }
            }
        }
        composeTestRule.onAllNodes(isRoot()).printToLog("onRoot")
        composeTestRule.onRoot().captureRoboImage(
            roborazziOptions = roborazziOptions
        )
    }

    @Test
    fun captureAddIngredientScreen() {
        composeTestRule.setContent {
            FakeRepositoryProvider {
                val viewModel = AddCocktailIngredientScreenViewModel(
                    apiRepository = LocalApiRepository.current
                )
                CocktailMasterTheme {
                    Surface {
                        AddCocktailIngredientScreen(
                            viewModel = viewModel,
                            ownedIngredientList = emptyList()
                        )
                    }
                }
            }
        }
        composeTestRule.onAllNodes(isRoot()).printToLog("onRoot")
        composeTestRule.onRoot().captureRoboImage(
            roborazziOptions = roborazziOptions
        )
    }

    @Test
    fun captureCraftableCocktailScreen() {
        composeTestRule.setContent {
            FakeRepositoryProvider {
                val viewModel = CraftableCocktailListScreenViewModel(
                    apiRepository = LocalApiRepository.current,
                    ingredientList = emptyList()
                )
                CocktailMasterTheme {
                    Surface {
                        CraftableCocktailListScreen(
                            viewModel = viewModel,
                            windowWidthSizeClass = WindowWidthSizeClass.Compact
                        )
                    }
                }
            }
        }
        composeTestRule.onAllNodes(isRoot()).printToLog("onRoot")
        composeTestRule.onRoot().captureRoboImage(
            roborazziOptions = roborazziOptions
        )
    }
}
