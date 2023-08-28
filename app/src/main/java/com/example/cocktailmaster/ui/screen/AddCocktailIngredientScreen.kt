@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.ConstantValues
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.AddEditIngredientDialog
import com.example.cocktailmaster.ui.component.CenterMessage
import com.example.cocktailmaster.ui.component.IngredientListItem
import com.example.cocktailmaster.ui.component.LoadingMessage
import com.example.cocktailmaster.ui.component.MyDropDownMenu
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.viewmodels.MainViewModel

@Composable
fun AddCocktailIngredientScreen(navController: NavHostController, viewModel: MainViewModel) {
    var isShowingDialog = remember { mutableStateOf(false) }
    var currentIngredient = remember { mutableStateOf<CocktailIngredient_UI?>(null) }
    var userInputName = remember { mutableStateOf("") }
    val categories = listOf("すべて") + ConstantValues.ingredientCategories
    var userSelectCategory by remember { mutableStateOf(categories[0]) }
    val ingredientList = viewModel.ingredientList.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val isFetchfailed = viewModel.isFetchFailed.collectAsState().value
    val context = LocalContext.current
    viewModel.setCurrentScreen(Screen.AddCocktailIngredientScreen)
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text("絞り込み: ", modifier = Modifier.padding(end = 8.dp))
                MyDropDownMenu(
                    categories = categories,
                    selectedValue = userSelectCategory,
                ) {
                    println(it)
                    userSelectCategory = it
                }
            }
            LazyColumn {
                items(ingredientList) { ingredient_ui ->
                    if(ingredient_ui.category == userSelectCategory || userSelectCategory == "すべて") {
                        IngredientListItem(
                            ingredient_UI = ingredient_ui,
                            tailIcon = Icons.Default.Add,
                            onIconTapAction = {
                                currentIngredient.value = it
                                isShowingDialog.value = true
                            },
                        )
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
                        viewModel.fetchAllIngredientsFromAPI()
                    }
                )
            } else if (!isLoading && ingredientList.isEmpty()) {
                CenterMessage(message = stringResource(R.string.not_found_ingredient))
            }
        }

        if (isShowingDialog.value) {
            AddEditIngredientDialog(
                isShowingDialog = isShowingDialog,
                currentIngredient = currentIngredient.value!!,
            ) { ingredient_ui ->
                viewModel.addOwnedIngredient(ingredient_ui)
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