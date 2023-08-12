package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cocktailmaster.ui.MainViewModel
import com.example.cocktailmaster.ui.component.CocktailListItem

@Composable
fun CraftableCocktailListScreen(navController: NavHostController, viewModel: MainViewModel) {
    val craftableCocktailList = viewModel.craftableCocktailList.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(craftableCocktailList) { cocktail_UI ->
                CocktailListItem(cocktail_UI = cocktail_UI)
            }
        }
    }
}