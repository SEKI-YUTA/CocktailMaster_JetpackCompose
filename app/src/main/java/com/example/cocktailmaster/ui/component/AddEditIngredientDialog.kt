@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun AddEditIngredientDialog(
    isAddMode: Boolean = true,
    isShowingDialog: MutableState<Boolean>,
    currentIngredient: CocktailIngredient_UI,
    onDoneEvent: (CocktailIngredient_UI) -> Unit,
) {
    val userInputName = remember { mutableStateOf("") }
    val dialogTitle =
        if (isAddMode) stringResource(R.string.dialog_title_add_ingredient) else stringResource(
            R.string.dialog_title_edit_ingredient
        )
    Dialog(
        onDismissRequest = {
            isShowingDialog.value = false
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    dialogTitle,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "材料名: ${currentIngredient.longName}",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = TextStyle(fontSize = 20.sp)
                )
                Row(
                    modifier = Modifier.height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomTextField(
                        value = userInputName.value,
                        clearAction = {},
                        onValueChange = {
                            userInputName.value = it
                        },
                        placeholder = stringResource(R.string.supplement_information_str)
                    )
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
                        Text(stringResource(R.string.cancel_str))
                    }
                    TextButton(
                        onClick = {
                            val tmp = currentIngredient.copy(
                                description = userInputName.value
                            )
                            onDoneEvent(tmp)
                        }
                    ) {
                        Text(stringResource(R.string.add_str))
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddEditIngredientDialogPreview_Light() {
    val isShowingDialog = remember { mutableStateOf(true) }
    val currentIngredient = DemoData.liqueurList.first().toUIModel()
    CocktailMasterTheme {
        Surface {
            AddEditIngredientDialog(
                isShowingDialog = isShowingDialog,
                currentIngredient = currentIngredient,
                onDoneEvent = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddEditIngredientDialogPreview_Night() {
    val isShowingDialog = remember { mutableStateOf(true) }
    val currentIngredient = DemoData.liqueurList.first().toUIModel()
    CocktailMasterTheme {
        Surface {
            AddEditIngredientDialog(
                isShowingDialog = isShowingDialog,
                currentIngredient = currentIngredient,
                onDoneEvent = {}
            )
        }
    }
}