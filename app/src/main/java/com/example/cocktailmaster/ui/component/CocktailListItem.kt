package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.R
import com.example.cocktailmaster.data.DemoData
import com.example.cocktailmaster.ui.model.Cocktail_UI
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun CocktailListItem(cocktail_UI: Cocktail_UI) {
//    val ingredientsStr = cocktail_UI.ingredients.joinToString(", ")
    println(cocktail_UI.ingredients)
    val ingredientsStr = cocktail_UI.ingredients.joinToString(", ") { it.longName }
    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        ) {
            Text(
                cocktail_UI.name,
                style = TextStyle(fontSize = 20.sp),
                maxLines = 1
            )
            Text(
                "${stringResource(id = R.string.ingredientStr)}: $ingredientsStr",
                style = TextStyle(fontSize = 16.sp),
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CocktailListItemPreview_Light() {
    CocktailMasterTheme {
        Surface {
            CocktailListItem(
                DemoData.cocktailList[0].toUIModel()
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CocktailListItemPreview_Night() {
    CocktailMasterTheme {
        Surface {
            CocktailListItem(
                DemoData.cocktailList[0].toUIModel()
            )
        }
    }
}