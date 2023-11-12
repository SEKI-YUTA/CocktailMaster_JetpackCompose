@file:OptIn(ExperimentalMaterial3Api::class)

package com.yuta.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yuta.cocktailmaster.data.ConstantValues
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun MyDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedVal: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotateDegree by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, label = ""
    )
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
        ) {
            Text(selectedVal, modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .rotate(rotateDegree)
                    .wrapContentSize()
            ) {
                Icon(
                    Icons.Rounded.ArrowDropDown,
                    contentDescription = ""
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            items.map {
                DropdownMenuItem(
                    text = {
                        Text(it)
                    },
                    onClick = {
                        onItemSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyDropDownPreview_Light() {
    CocktailMasterTheme {
        Surface {
            MyDropDownMenu(
                items = ConstantValues.ingredientCategories,
                selectedVal = ConstantValues.ingredientCategories[0],
                onItemSelected = {}
            )
        }
    }
}