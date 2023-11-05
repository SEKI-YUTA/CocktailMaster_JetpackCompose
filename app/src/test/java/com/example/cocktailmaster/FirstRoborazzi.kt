package com.example.cocktailmaster

import androidx.compose.material3.Surface
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.cocktailmaster.ui.screen.AddCocktailIngredientScreen
import com.example.cocktailmaster.ui.screen.CraftableCocktailListScreen
import com.example.cocktailmaster.ui.screen.TopScreen
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme
import com.example.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.example.cocktailmaster.ui.viewmodels.TopScreenViewModel
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
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
                val viewModel = TopScreenViewModel(
                    ownedIngredientRepository = LocalOwnedIngredientRepository.current,
                    onUpdateOwnedIngredient = {}
                )
                CocktailMasterTheme {
                    Surface {
                        TopScreen(
                            viewModel = viewModel,
                            navigateToCraftableCocktail = {},
                            navigateToAddIngredient = {},
                            onDeleteOwnedIngredient = {},
                            onEditOwnedIngredient = {}
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
                    apiRepository = LocalApiRepository.current,
                )
                CocktailMasterTheme {
                    Surface {
                        AddCocktailIngredientScreen(
                            viewModel = viewModel,
                            ownedIngredientList = emptyList(),
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
                    ingredientList = emptyList(),
                )
                CocktailMasterTheme {
                    Surface {
                        CraftableCocktailListScreen(
                            viewModel = viewModel,
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