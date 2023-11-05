package com.example.cocktailmaster.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.FakeRepositoryProvider
import com.example.cocktailmaster.LocalOwnedIngredientRepository
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.ui.component.CenterMessage
import com.example.cocktailmaster.ui.component.IngredientListItem
import com.example.cocktailmaster.ui.component.LoadingMessage
import com.example.cocktailmaster.ui.component.MenuButton
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme
import com.example.cocktailmaster.ui.viewmodels.TopScreenViewModel
import com.example.cocktailmaster.util.CocktailMasterPreviewAnnotation

/*
所有中の酒をリスト表示して
追加画面と現在所有中の酒で作ることができるカクテルを表示する画面へ遷移できる
*/
@Composable
fun TopScreen(
    viewModel: TopScreenViewModel,
    ownedIngredientList: List<CocktailIngredient_UI>,
    navigateToCraftableCocktail: () -> Unit = {},
    navigateToAddIngredient: () -> Unit = {},
    onDeleteOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onEditOwnedIngredient: (CocktailIngredient_Data) -> Unit = {}
) {
    val viewState = viewModel.viewState.collectAsState().value
//    val ownedIngredients = viewModel.ownedCocktailIngredients.collectAsState().value
//    val networkConnected = viewModel.isNetworkConnected.collectAsState().value
//    val isOwnedIngredientListLoading = viewModel.isOwnedIngredientListLoading.collectAsState().value
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuButton(
                    text = stringResource(R.string.cocktail_list_str),
                    modifier = Modifier.weight(1f),
                    enabled = viewState.isNetworkConnected,
                    icon = Icons.Default.List
                ) {
                    navigateToCraftableCocktail()
                }
                Spacer(modifier = Modifier.width(16.dp))
                MenuButton(
                    text = stringResource(R.string.add_ingredient_str),
                    modifier = Modifier.weight(1f),
                    enabled = viewState.isNetworkConnected,
                    icon = Icons.Default.Add
                ) {
                    navigateToAddIngredient()
                }
            }
            Text(
                text = stringResource(R.string.owned_ingredient_str),
                style = TextStyle(fontSize = 22.sp),
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(ownedIngredientList) { ingredient_UI ->
                    IngredientListItem(
                        ingredient_UI = ingredient_UI,
                        tailIcon = null,
                        onIconTapAction = {},
                        onDeleteAction = { ingredient ->
                            onDeleteOwnedIngredient(ingredient.toDataModel())
                        },
                        onEditAction = { ingredient ->
                            onEditOwnedIngredient(ingredient.toDataModel())
                        }
                    )
                }
            }
            if (viewState.isLoading) {
                LoadingMessage()
            } else if (ownedIngredientList.isEmpty()) {
                CenterMessage(message = stringResource(R.string.owned_ingredient_not_found))
            }
        }
    }
}


@CocktailMasterPreviewAnnotation
@Composable
fun TopScreenPreview() {
    FakeRepositoryProvider {
        val fakeOwnedIngredientRepository = LocalOwnedIngredientRepository.current
        val viewModel = TopScreenViewModel(
            fakeOwnedIngredientRepository,
        )
        CocktailMasterTheme {
            Surface {
                TopScreen(
                    viewModel = viewModel,
                    ownedIngredientList = DemoData.ingredientList.map { it.toUIModel() },
                    navigateToCraftableCocktail = {},
                    navigateToAddIngredient = {},
                    onDeleteOwnedIngredient = {},
                    onEditOwnedIngredient = {}
                )
            }
        }
    }
}