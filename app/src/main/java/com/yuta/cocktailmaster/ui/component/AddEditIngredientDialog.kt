@file:OptIn(ExperimentalMaterial3Api::class)

package com.yuta.cocktailmaster.ui.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yuta.cocktailmaster.R
import com.yuta.cocktailmaster.data.DemoData
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.AddCocktailIngredientScreenViewModel

@Composable
fun AddEditIngredientDialog(
    isAddMode: Boolean = true,
    currentIngredient: CocktailIngredient_UI,
    userInputState: AddCocktailIngredientScreenViewModel.UserInputState,
    onUpdateUserInput: (AddCocktailIngredientScreenViewModel.UserInputState) -> Unit,
    onDoneEvent: (CocktailIngredient_UI) -> Unit = {},
    onCancelEvent: () -> Unit = {},
) {
    val dialogTitle =
        if (isAddMode) stringResource(R.string.dialog_title_add_ingredient)
        else stringResource(R.string.dialog_title_edit_ingredient)
    Dialog(
        onDismissRequest = {
            onCancelEvent()
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
                        value = userInputState.description,
                        clearAction = {},
                        onValueChange = {
                            onUpdateUserInput(
                                userInputState.copy(
                                    description = it
                                )
                            )
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
                            onUpdateUserInput(
                                userInputState.copy(
                                    description = ""
                                )
                            )
                            onCancelEvent()
                        }
                    ) {
                        Text(stringResource(R.string.cancel_str))
                    }
                    TextButton(
                        onClick = {
                            val tmp = currentIngredient.copy(
                                description = userInputState.description
                            )
                            onDoneEvent(tmp)
                        }
                    ) {
                        Text(
                            if(isAddMode) stringResource(R.string.add_str)
                            else stringResource(R.string.edit_str)
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AddEditIngredientDialogPreview_Light() {
    val currentIngredient = DemoData.ingredientList.first().toUIModel()
    CocktailMasterTheme {
        Surface {
            AddEditIngredientDialog(
                currentIngredient = currentIngredient,
                userInputState = AddCocktailIngredientScreenViewModel.UserInputState(),
                onUpdateUserInput = {},
                onDoneEvent = {},
                onCancelEvent = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddEditIngredientDialogPreview_Night() {
    val currentIngredient = DemoData.ingredientList.first().toUIModel()
    CocktailMasterTheme {
        Surface {
            AddEditIngredientDialog(
                currentIngredient = currentIngredient,
                userInputState = AddCocktailIngredientScreenViewModel.UserInputState(),
                onUpdateUserInput = {},
                onDoneEvent = {},
                onCancelEvent = {}
            )
        }
    }
}