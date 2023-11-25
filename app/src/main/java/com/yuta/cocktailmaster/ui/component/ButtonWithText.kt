package com.yuta.cocktailmaster.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithText(
    text: String,
    space: Dp = 8.dp,
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = text)
        Spacer(modifier = Modifier.width(space))
        Icon(icon, contentDescription = contentDescription)
    }
}
