@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.cocktailmaster.R
import com.example.cocktailmaster.ui.component.AddEditIngredientDialog
import com.example.cocktailmaster.ui.viewmodels.MainViewModel
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.CenterMessage
import com.example.cocktailmaster.ui.component.IngredientListItem
import com.example.cocktailmaster.ui.component.LoadingMessage
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI

@Composable
fun AddCocktailIngredientScreen(navController: NavHostController, viewModel: MainViewModel) {
    var isShowingDialog = remember { mutableStateOf(false) }
    var currentIngredient = remember { mutableStateOf<CocktailIngredient_UI?>(null) }
    var userInputName = remember { mutableStateOf("") }
    val ingredientList = viewModel.ingredientList.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val context = LocalContext.current
    viewModel.setCurrentScreen(Screen.AddCocktailIngredientScreen)
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            LazyColumn {
                items(ingredientList) { ingredient_ui ->
                    IngredientListItem(
                        ingredient_UI = ingredient_ui,
                        tailIcon = Icons.Default.Add,
                        onIconTapAction = {
                            println("tapped: ${ingredient_ui.name}")
                            currentIngredient.value = it
                            isShowingDialog.value = true
                        },
                    )
                }
            }
            if(isLoading) {
                LoadingMessage()
            }
            if(!isLoading && ingredientList.isEmpty()) {
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
                Toast.makeText(context, context.getString(R.string.added_message), Toast.LENGTH_SHORT).show()
            }
        }
    }
}