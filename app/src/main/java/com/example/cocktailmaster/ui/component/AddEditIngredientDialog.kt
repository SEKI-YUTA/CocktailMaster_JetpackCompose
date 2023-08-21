@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI

@Composable
fun AddEditIngredientDialog(
    isAddMode: Boolean = true,
    isShowingDialog: MutableState<Boolean>,
    currentIngredient: CocktailIngredient_UI,
    onDoneEvent: (CocktailIngredient_UI) -> Unit,
) {
    val userInputName = remember { mutableStateOf("") }
    val dialogTitle = if (isAddMode) "材料を追加" else "材料を編集"
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
                    dialogTitle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "材料名: ${currentIngredient?.name}",
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
                            val tmp = currentIngredient.copy(
                                description = userInputName.value
                            )
                           onDoneEvent(tmp)
                        }
                    ) {
                        Text("追加")
                    }
                }
            }
        }
    )
}