package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
    ownedCount: Int = 0,
    showOwnedCountBadge: Boolean = false,
    onIconTapAction: (CocktailIngredient_UI) -> Unit = {},
    onDeleteAction: (CocktailIngredient_UI) -> Unit = {},
    onEditAction: (CocktailIngredient_UI) -> Unit = {}
) {
    val menuShowing = remember { mutableStateOf(false) }
    ConstraintLayout {
        val (cardRef, badgeRef) = createRefs()
        Card(
            modifier = modifier
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (enableContextMenu) {
                                // 削除と編集ボタンを表示する処理を書く
                                println("Long Pressed")
                                menuShowing.value = true
                            }
                        }
                    )
                }
                .constrainAs(cardRef) {
                    linkTo(start = parent.start, top = parent.top, end = parent.end, bottom = parent.bottom)
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
                Text("vol: ${ingredient_UI.vol}${stringResource(id = R.string.alcohol_volume_unit)}")
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
                            onEditAction(ingredient_UI)
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
//
        }
        if(showOwnedCountBadge && ownedCount > 0) {
            CountBadge(
                modifier = Modifier.constrainAs(badgeRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = "$ownedCount"
            )
        }
    }
}

@Composable
fun CountBadge(modifier: Modifier = Modifier,text: String) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.inversePrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = TextStyle(fontWeight = FontWeight.Bold))
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientListItemPreview_Light() {
    val data = DemoData.ingredientList[0].toUIModel()
    CocktailMasterTheme {
        Surface {
            IngredientListItem(
                ownedCount = 1,
                ingredient_UI = data,
                showOwnedCountBadge = true,
                tailIcon = Icons.Default.Add
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun IngredientListItemPreview_Night() {
    val data = DemoData.ingredientList[0].toUIModel()
    CocktailMasterTheme {
        Surface {
            IngredientListItem(
                ownedCount = 1,
                showOwnedCountBadge = true,
                ingredient_UI = data,
            )
        }
    }
}
