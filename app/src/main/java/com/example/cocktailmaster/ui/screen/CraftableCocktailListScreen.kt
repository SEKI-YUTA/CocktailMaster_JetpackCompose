package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cocktailmaster.R
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.CenterMessage
import com.example.cocktailmaster.ui.component.CocktailListItem
import com.example.cocktailmaster.ui.component.LoadingMessage
import com.example.cocktailmaster.ui.component.MyDropDownMenu
import com.example.cocktailmaster.ui.viewmodels.MainViewModel

@Composable
fun CraftableCocktailListScreen(viewModel: MainViewModel) {
    val craftableCocktailList = viewModel.craftableCocktailList.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val isFetchfailed = viewModel.isFetchFailed.collectAsState().value
    val categories by remember(craftableCocktailList) {
        derivedStateOf {
            listOf("すべて") + LinkedHashSet(craftableCocktailList.map { it.category }).toMutableList()
        }
    }
    var userSelectCategory by remember { mutableStateOf(categories[0]) }

    viewModel.setCurrentScreen(Screen.CraftableCocktailListScreen)
    LaunchedEffect(key1 = true) {
        viewModel.findCraftableCocktail()
    }
    Box {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text("絞り込み: ", modifier = Modifier.padding(end = 8.dp))
                MyDropDownMenu(
                    items = categories,
                    selectedVal = userSelectCategory,
                ) {
                    userSelectCategory = it
                }
            }
            Text(
                text = stringResource(id = R.string.can_make_cocktail_str),
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                style = TextStyle(fontSize = 22.sp)
            )
            LazyColumn {
                items(craftableCocktailList) { cocktail_UI ->
                    if(userSelectCategory == "すべて" || userSelectCategory == cocktail_UI.category) {
                        CocktailListItem(cocktail_UI = cocktail_UI)
                    }
                }
            }

            if (isLoading) {
                LoadingMessage()
            } else if(isFetchfailed) {
                CenterMessage(
                    message = stringResource(R.string.fetch_failed_message),
                    icon = Icons.Default.Refresh,
                    iconTapAction = {
                        viewModel.findCraftableCocktail()
                    }
                )
            } else if (!isLoading && craftableCocktailList.isEmpty()) {
                CenterMessage(message = stringResource(R.string.craftable_cocktail_not_found))
            }
        }
    }
}