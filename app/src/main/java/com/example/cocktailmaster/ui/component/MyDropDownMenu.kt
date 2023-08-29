@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.data.ConstantValues
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun MyDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedVal: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(selectedVal, modifier = Modifier.weight(1f))
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    imageVector =
                    if(expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown,
                    contentDescription = null
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
                DropdownMenuItem(text = {
                    Text(it)
                }, onClick = {
                    onItemSelected(it)
                    expanded = false
                })
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