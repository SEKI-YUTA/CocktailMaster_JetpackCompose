package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cocktailmaster.R
import com.example.cocktailmaster.ui.viewmodels.MainViewModel
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.CocktailListItem

@Composable
fun CraftableCocktailListScreen(navController: NavHostController, viewModel: MainViewModel) {
    val craftableCocktailList = viewModel.craftableCocktailList.collectAsState().value
    viewModel.findCraftableCocktail()
    viewModel.setCurrentScreen(Screen.CraftableCocktailListScreen)
    Box {
        Column {
            Text(
                text = stringResource(id = R.string.can_make_cocktail_str),
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 22.sp)
            )
            LazyColumn {
                items(craftableCocktailList) { cocktail_UI ->
                    CocktailListItem(cocktail_UI = cocktail_UI)
                }
            }
        }
    }
}