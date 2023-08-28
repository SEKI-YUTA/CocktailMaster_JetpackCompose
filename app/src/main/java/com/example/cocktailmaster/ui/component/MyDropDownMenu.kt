@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cocktailmaster.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.cocktailmaster.data.ConstantValues

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
        IconButton(onClick = {
            isDropDownExpanded = !isDropDownExpanded
        }) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        ExposedDropdownMenuBox(
            expanded = isDropDownExpanded,
            onExpandedChange = {
                isDropDownExpanded = it
            }
        ) {
            TextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                   ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropDownExpanded)
                },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
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
                        }
                    )
                }
            }
        }
    }
}