@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cocktailmaster.FakeRepositoryProvider
import com.example.cocktailmaster.LocalApiRepository
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.model.CocktailIngredient_Data
import com.example.cocktailmaster.ui.component.AddEditIngredientDialog
import com.example.cocktailmaster.ui.component.CenterMessage
import com.example.cocktailmaster.ui.component.IngredientListItem
import com.example.cocktailmaster.ui.component.LoadingMessage
import com.example.cocktailmaster.ui.component.MyDropDownMenu
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme
import com.example.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel
import com.example.cocktailmaster.util.CocktailMasterPreviewAnnotation

@Composable
fun AddCocktailIngredientScreen(
    viewModel: AddCocktailIngredientScreenViewModel,
    ownedIngredientList: List<CocktailIngredient_UI>,
    onAddOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
) {
    val isShowingDialog = remember { mutableStateOf(false) }
    val currentIngredient = remember { mutableStateOf<CocktailIngredient_UI?>(null) }
    val userInputName = remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewState = viewModel.viewState.collectAsState().value
    val ownedIngredientList_LogName by remember(ownedIngredientList) {
        derivedStateOf {
            ownedIngredientList.map { cocktailingredientUi ->
                cocktailingredientUi.longName
            }
        }
    }
    val categories by remember(viewState) {
        derivedStateOf {
            listOf("すべて") + LinkedHashSet(viewState.ingredientList.map { it.category }).toMutableList()
        }
    }
    var userSelectCategory by remember { mutableStateOf(categories[0]) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text("絞り込み: ", modifier = Modifier.padding(end = 8.dp))
                MyDropDownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    items = categories,
                    selectedVal = userSelectCategory,
                ) {
                    println(it)
                    userSelectCategory = it
                }
            }
            LazyColumn {
                items(
                    viewState.ingredientList,
                    key = { it.longName + it.id }
                ) { ingredient_ui ->
                    if (ingredient_ui.category == userSelectCategory || userSelectCategory == "すべて") {
                        IngredientListItem(
                            ingredient_UI = ingredient_ui,
                            tailIcon = Icons.Default.Add,
                            enableContextMenu = false,
                            showOwnedCountBadge = true,
                            ownedCount = checkContainsCount(
                                ownedIngredientList_LogName,
                                ingredient_ui.longName
                            ),
                            onIconTapAction = {
                                currentIngredient.value = it
                                isShowingDialog.value = true
                                println(ownedIngredientList)
                            },
                        )
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
//                        viewModel.fetchAllIngredientsFromAPI()
                    }
                )
            } else if (!viewState.isLoading && viewState.ingredientList.isEmpty()) {
                CenterMessage(message = stringResource(R.string.not_found_ingredient))
            }
        }

        if (isShowingDialog.value) {
            AddEditIngredientDialog(
                isShowingDialog = isShowingDialog,
                currentIngredient = currentIngredient.value!!,
            ) {
//                viewModel.addOwnedIngredient(ingredient_ui)
                it.id = 0
                onAddOwnedIngredient(it.toDataModel())
                isShowingDialog.value = false
                userInputName.value = ""
                Toast.makeText(
                    context,
                    context.getString(R.string.added_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

fun checkContainsCount(list: List<String>, target: String): Int {
    val count = list.stream().filter {
        it == target
    }.count()
    return count.toInt()
}

@CocktailMasterPreviewAnnotation
@Composable
fun AddCocktailIngredientScreenPreview() {
    FakeRepositoryProvider {
        val fakeApiRepository = LocalApiRepository.current
        val viewModel = AddCocktailIngredientScreenViewModel(
            apiRepository = fakeApiRepository,
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