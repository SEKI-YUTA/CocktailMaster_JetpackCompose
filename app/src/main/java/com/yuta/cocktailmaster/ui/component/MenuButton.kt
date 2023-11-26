package com.yuta.cocktailmaster.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuta.cocktailmaster.ui.theme.CocktailMasterTheme

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onTapAction: () -> Unit
) {
    Box(
        modifier =
        modifier.clickable(enabled = enabled, onClick = onTapAction)
            .border(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(99.dp)
            )
            .padding(8.dp)
            .alpha(if (enabled) 1f else 0.4f)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
            Text(
                text = text,
                Modifier.weight(1f),
                style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center)
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 220
)
@Composable
fun MenuButtonLightThemePreview_Light() {
    CocktailMasterTheme {
        Surface {
            MenuButton(
                text = "Menu",
                icon = Icons.Default.Add,
                onTapAction = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 220
)
@Composable
fun MenuButtonLightThemePreview_Night() {
    CocktailMasterTheme {
        Surface {
            MenuButton(
                text = "Menu",
                icon = Icons.Default.Add,
                onTapAction = {}
            )
        }
    }
}
