package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun IngredientListItem(
    modifier: Modifier = Modifier,
    ingredient_UI: CocktailIngredient_UI,
    tailIcon: ImageVector? = null,
    enableContextMenu: Boolean = true,
    onIconTapAction: (CocktailIngredient_UI) -> Unit = {},
    onDeleteAction: (CocktailIngredient_UI) -> Unit = {},
    onEditAction: (CocktailIngredient_UI) -> Unit = {}
) {
    val menuShowing = remember { mutableStateOf(false) }
    val isShowingDialog = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if(enableContextMenu) {
                            // 削除と編集ボタンを表示する処理を書く
                            println("Long Pressed")
                            menuShowing.value = true
                        }
                    }
                )
            },
        ) {
        Row(
            modifier = modifier.padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ingredient_UI.longName,
                    style = TextStyle(fontSize = 20.sp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (ingredient_UI.description != "") {
                    Text(
                        text = ingredient_UI.description,
                        style = TextStyle(fontSize = 16.sp),
                        maxLines = 2
                    )
                }
            }
            Text("${ingredient_UI.vol}${stringResource(id = R.string.alcohol_volume_unit)}")
            if (tailIcon != null) {
                IconButton(onClick = { onIconTapAction(ingredient_UI) }) {
                    Icon(imageVector = tailIcon, contentDescription = null)
                }
            }
        }
        if(menuShowing.value) {
            DropdownMenu(
                expanded = menuShowing.value,
                onDismissRequest = {
                    menuShowing.value = false
                }) {
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.edit_str))
                    },
                    onClick = {
                        isShowingDialog.value = true
                        menuShowing.value = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(stringResource(R.string.remove_str))
                    },
                    onClick = {
                        onDeleteAction(ingredient_UI)
                        menuShowing.value = false
                    }
                )
            }
        }
        if(isShowingDialog.value) {
            AddEditIngredientDialog(
                isShowingDialog = isShowingDialog,
                currentIngredient = ingredient_UI,
                onDoneEvent = { ingredient_ui ->
                    onEditAction(ingredient_ui)
                    isShowingDialog.value = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientListItemPreview_Light() {
    val data = DemoData.liqueurList[0].toUIModel()
    CocktailMasterTheme {
        Surface {
            IngredientListItem(
                ingredient_UI = data,
                tailIcon = Icons.Default.Add
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IngredientListItemPreview_Night() {
    val data = DemoData.liqueurList[0].toUIModel()
    CocktailMasterTheme {
        Surface {
            IngredientListItem(
                ingredient_UI = data,
            )
        }
    }
}
