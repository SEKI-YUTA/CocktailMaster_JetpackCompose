package com.yuta.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuta.cocktailmaster.R
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun CenterMessage(
    message: String,
    icon: ImageVector? = null,
    iconTapAction: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = TextStyle(fontSize = 24.sp)
            )
            if (icon != null) {
                IconButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        iconTapAction()
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CenterMessagePreview_Light() {
    CocktailMasterTheme {
        Surface {
            CenterMessage(
                message = stringResource(id = R.string.not_found_ingredient)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CenterMessagePreview_Night() {
    CocktailMasterTheme {
        Surface {
            CenterMessage(
                message = stringResource(id = R.string.not_found_ingredient)
            )
        }
    }
}
