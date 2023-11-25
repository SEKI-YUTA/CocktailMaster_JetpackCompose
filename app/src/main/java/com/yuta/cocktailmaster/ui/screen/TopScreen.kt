package com.yuta.cocktailmaster.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.yuta.cocktailmaster.FakeRepositoryProvider
import com.yuta.cocktailmaster.R
import com.yuta.cocktailmaster.data.DemoData
import com.yuta.cocktailmaster.data.model.CocktailIngredient_Data
import com.yuta.cocktailmaster.ui.component.AddEditIngredientDialog
import com.yuta.cocktailmaster.ui.component.CenterMessage
import com.yuta.cocktailmaster.ui.component.IngredientListItem
import com.yuta.cocktailmaster.ui.component.LoadingMessage
import com.yuta.cocktailmaster.ui.component.MenuButton
import com.yuta.cocktailmaster.ui.model.CocktailIngredient_UI
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme
import com.yuta.cocktailmaster.ui.viewmodels.TopScreenViewModel
import com.yuta.cocktailmaster.util.CocktailMasterPreviewAnnotation

/*
所有中の酒をリスト表示して
追加画面と現在所有中の酒で作ることができるカクテルを表示する画面へ遷移できる
*/
@Composable
fun TopScreen(
    topAppBarSize: IntSize,
    isOnboardingFinished: Boolean = false,
    isAppStatusRead: Boolean = false,
    viewModel: TopScreenViewModel,
    ownedIngredientList: List<CocktailIngredient_UI>,
    navigateToCraftableCocktail: () -> Unit = {},
    navigateToAddIngredient: () -> Unit = {},
    onDeleteOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onEditOwnedIngredient: (CocktailIngredient_Data) -> Unit = {},
    onUpdateOnboardingFinished: (Boolean) -> Unit,
    cocktailListButtonModifier: Modifier = Modifier,
    addIngredientButtonModifier: Modifier = Modifier,
    ownedIngredientListModifier: Modifier = Modifier
) {
    val viewState = viewModel.viewState.collectAsState().value
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
                    modifier = cocktailListButtonModifier
                        .weight(1f),
                    icon = Icons.Default.List
                ) {
                    navigateToCraftableCocktail()
                }
                Spacer(modifier = Modifier.width(16.dp))
                MenuButton(
                    text = stringResource(R.string.add_ingredient_str),
                    modifier = addIngredientButtonModifier
                        .weight(1f),
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
            LazyColumn(
                modifier = ownedIngredientListModifier
                    .fillMaxSize()
            ) {
                items(ownedIngredientList) { ingredient_UI ->
                    IngredientListItem(
                        ingredient_UI = ingredient_UI,
                        tailIcon = null,
                        onIconTapAction = {},
                        onEditAction = {
                            viewModel.onIngredientEditRequest(it)
                        },
                        onDeleteAction = { ingredient ->
                            viewModel.onIngredientDeleteRequest(ingredient)
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
        if (viewState.isShowingEditDialog && viewState.selectedIngredient != null) {
            AddEditIngredientDialog(
                isAddMode = false,
                currentIngredient = viewState.selectedIngredient,
                onDoneEvent = { ingredient_ui ->
                    onEditOwnedIngredient(ingredient_ui.toDataModel())
                    viewModel.onCloseEditDialog()
                },
                onCancelEvent = {
                    viewModel.onCloseEditDialog()
                },
                userInputState = viewState.userInputState,
                onUpdateUserInput = {
                    viewModel.onUpdateUserInput(it)
                }
            )
        }

        if (viewState.isShowingDeleteConfirmDialog) {
            DeleteConfirmDialog(
                title = stringResource(R.string.delete_confirm_dialog_title),
                onConfirm = {
                    viewState.selectedIngredient?.let {
                        onDeleteOwnedIngredient(viewState.selectedIngredient.toDataModel())
                    }
                    viewModel.onCloseDeleteConfirmDialog()
                },
                onDismiss = {
                    viewModel.onCloseDeleteConfirmDialog()
                }
            )
        }
    }
}

@Composable
fun DeleteConfirmDialog(
    title: String,
    text: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Dialog(
        content = {
            Box(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)

            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = TextStyle(fontSize = 20.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.cancel_str)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                onConfirm()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.delete_str)
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = {
            onDismiss()
        }
    )
}

@CocktailMasterPreviewAnnotation
@Composable
fun TopScreenPreview() {
    FakeRepositoryProvider {
        val viewModel = TopScreenViewModel()
        CocktailMasterTheme {
            Surface {
                TopScreen(
                    topAppBarSize = IntSize.Zero,
                    viewModel = viewModel,
                    ownedIngredientList = DemoData.ingredientList.map { it.toUIModel() },
                    navigateToCraftableCocktail = {},
                    navigateToAddIngredient = {},
                    onDeleteOwnedIngredient = {},
                    onEditOwnedIngredient = {},
                    onUpdateOnboardingFinished = {}
                )
            }
        }
    }
}
