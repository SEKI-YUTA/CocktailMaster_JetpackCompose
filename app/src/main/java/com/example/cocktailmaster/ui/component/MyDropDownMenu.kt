@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
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
    categories: List<String>,
    selectedValue: String,
    onValueChanged: (String) -> Unit
) {
    val context = LocalContext.current
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = isDropDownExpanded,
            onExpandedChange = {
                isDropDownExpanded = it
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(60.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                BasicTextField(
                    value = selectedValue,
                    onValueChange = {},
                    readOnly = true,

                    textStyle = TextStyle(
                        color = if(isSystemInDarkTheme()) Color.White else Color.Black,
                        lineHeightStyle = LineHeightStyle.Default,
                        lineHeight = 60.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    decorationBox = {innerTextField ->
                        innerTextField()
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownExpanded)
                    }
                )
            }
            ExposedDropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = {
                    isDropDownExpanded = false
                }
            ) {
                categories.map {
                    DropdownMenuItem(
                        text = {
                            Text(text = it)
                        },
                        onClick = {
                            onValueChanged(it)
                            isDropDownExpanded = false
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyDropDownMenuPreview_Light() {
    CocktailMasterTheme {
        Surface {
            MyDropDownMenu(
                categories = listOf("すべて") + ConstantValues.ingredientCategories,
                selectedValue = "すべて",
                onValueChanged = {}
            )
        }
    }
}