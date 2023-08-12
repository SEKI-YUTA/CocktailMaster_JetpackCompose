package com.example.cocktailmaster.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.ui.model.CocktailIngredient_UI

@Composable
fun IngredientListItem(
    modifier: Modifier = Modifier,
    ingredient_UI: CocktailIngredient_UI,
    tailIcon: ImageVector? = null,
    onIconTapAction: (CocktailIngredient_UI) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = ingredient_UI.name, style = TextStyle(fontSize = 20.sp))
            Text(
                text = ingredient_UI.description ?: "",
                style = TextStyle(fontSize = 16.sp)
            )
        }
        if(tailIcon != null) {
            IconButton(onClick = { onIconTapAction(ingredient_UI)}) {
                Icon(imageVector = tailIcon, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientListItemPreview() {
    val data = DemoData.liqueurList[0].toUIModel()
    IngredientListItem(
        ingredient_UI = data,
    ){}
}