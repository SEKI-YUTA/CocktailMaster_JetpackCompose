package com.yuta.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.yuta.cocktailmaster.FakeRepositoryProvider
import com.yuta.cocktailmaster.LocalApiRepository
import com.yuta.cocktailmaster.R
import com.yuta.cocktailmaster.ui.component.CenterMessage
import com.yuta.cocktailmaster.ui.component.CocktailListItem
import com.yuta.cocktailmaster.ui.component.LoadingMessage
import com.yuta.cocktailmaster.ui.component.MyDropDownMenu
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.CraftableCocktailListScreenViewModel
import com.yuta.cocktailmaster.util.CocktailMasterPreviewAnnotation

@Composable
fun CraftableCocktailListScreen(viewModel: CraftableCocktailListScreenViewModel) {
    val viewState = viewModel.viewState.collectAsState().value
    val craftableList by remember(viewState) {
        derivedStateOf {
            viewState.craftableCocktailList
        }
    }
    val categories by remember(craftableList) {
        derivedStateOf {
            listOf("すべて") + LinkedHashSet(craftableList.map { it.category }).toMutableList()
        }
    }
    var userSelectCategory by remember { mutableStateOf(categories[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                items(
                    craftableList,
                    key = { it.name + it.cocktailId }
                ) { cocktail_UI ->
                    if (userSelectCategory == "すべて" || userSelectCategory == cocktail_UI.category) {
                        CocktailListItem(cocktail_UI = cocktail_UI)
                    }
                }
            }

            if (viewState.isLoading) {
                LoadingMessage()
            } else if (viewState.isFetchFailed) {
                CenterMessage(
                    message = stringResource(R.string.fetch_failed_message),
                    icon = Icons.Default.Refresh,
                    iconTapAction = {
                    }
                )
            } else if (!viewState.isLoading && craftableList.isEmpty()) {
                CenterMessage(message = stringResource(R.string.craftable_cocktail_not_found))
            }
        }
    }
}


@CocktailMasterPreviewAnnotation
@Composable
fun CraftableCocktailListScreenPreview() {
    FakeRepositoryProvider {
        val fakeApiRepository = LocalApiRepository.current
        val viewModel = CraftableCocktailListScreenViewModel(
            apiRepository = fakeApiRepository,
            ingredientList = emptyList()
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