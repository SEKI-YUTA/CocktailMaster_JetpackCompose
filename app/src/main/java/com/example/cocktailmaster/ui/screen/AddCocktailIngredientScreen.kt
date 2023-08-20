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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.cocktailmaster.ui.MainViewModel
import com.example.cocktailmaster.ui.Screen
import com.example.cocktailmaster.ui.component.IngredientListItem
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI

@Composable
fun AddCocktailIngredientScreen(navController: NavHostController, viewModel: MainViewModel) {
    var isShowingDialog = remember { mutableStateOf(false) }
    var currentIngredient = remember { mutableStateOf<CocktailIngredient_UI?>(null) }
    var userInputName = remember { mutableStateOf("") }
    val ingredientList = viewModel.ingredientList.collectAsState().value
    val context = LocalContext.current
    viewModel.setCurrentScreen(Screen.AddCocktailIngredientScreen)
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(ingredientList) { ingredient_ui ->
                IngredientListItem(
                    ingredient_UI = ingredient_ui,
                    tailIcon = Icons.Default.Add,
                    onIconTapAction = {
                        println("tapped: ${ingredient_ui.name}")
                        currentIngredient.value = it
                        isShowingDialog.value = true
//                    viewModel.addOwnedIngredient(ingredient_ui)
                    },
                    onDeleteAction = {}
                )
            }
        }

        if (isShowingDialog.value) {
            Dialog(
                onDismissRequest = {
                    isShowingDialog.value = false
                },
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Text(
                            "材料を追加",
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            "材料名: ${currentIngredient.value?.name}",
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Row(modifier = Modifier) {
                            TextField(value = userInputName.value,
                                placeholder = {
                                    Text("補足情報")
                                },
                                onValueChange = {
                                    userInputName.value = it
                                })
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = {
                                    // キャンセル
                                    isShowingDialog.value = false
                                    userInputName.value = ""
                                }
                            ) {
                                Text("キャンセル")
                            }
                            TextButton(
                                onClick = {
                                    // 追加処理
                                    val tmp =
                                        currentIngredient.value?.copy(description = userInputName.value)
                                    tmp?.let {
                                        viewModel.addOwnedIngredient(tmp)
                                    }
                                    isShowingDialog.value = false
                                    userInputName.value = ""

                                    Toast.makeText(context, "追加しました。", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text("追加")
                            }
                        }
                    }
                }
            )
        }

    }
}