package com.example.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun CustomTextField(
    value: String,
    placeholder: String = "",
    clearIcon: ImageVector = Icons.Default.Clear,
    clearAction: (() -> Unit)?,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    fontSize: Int = 20,
) {
    val visibleClearButton = value.isNotEmpty() && clearAction != null
    BasicTextField(
        value = value,
        maxLines = maxLines,
        textStyle = TextStyle(
            fontSize = fontSize.sp
        ),
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(
                    start = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                  modifier = Modifier.weight(1f),
              ) {
                  if(value.isEmpty()) {
                      Text(
                          text = placeholder,
                          style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                          )
                      )
                  }
                  innerTextField()
              }
                AnimatedVisibility(visible = visibleClearButton) {
                    IconButton(
                        onClick = {
                            clearAction?.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = clearIcon,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }

                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview_Light() {
    CocktailMasterTheme {
        Surface() {
            CustomTextField(
                value = "Hello",
                placeholder = "Placeholder",
                clearAction = {},
                onValueChange = {}
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CustomTextFieldPreview_Night() {
    CocktailMasterTheme {
        Surface {
            CustomTextField(
                value = "",
                placeholder = "Placeholder",
                clearAction = {},
                onValueChange = {}
            )
        }
    }
}