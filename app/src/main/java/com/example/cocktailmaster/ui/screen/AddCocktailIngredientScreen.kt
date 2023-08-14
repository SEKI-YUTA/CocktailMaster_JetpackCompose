package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cocktailmaster.ui.MainViewModel
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.IngredientListItem

@Composable
fun AddCocktailIngredientScreen(navController: NavHostController, viewModel: MainViewModel) {
    val ingredientList = viewModel.ingredientList.collectAsState().value
    viewModel.setCurrentScreen(Screen.AddCocktailIngredientScreen)
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(ingredientList) { ingredient_ui ->
                IngredientListItem(ingredient_UI = ingredient_ui, tailIcon = Icons.Default.Add) {
                    println("tapped: ${ingredient_ui.name}")
                    viewModel.addOwnedIngredient(ingredient_ui)
                }
            }
        }
    }
}