package com.yuta.cocktailmaster.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpotLight(
    rect: Rect,
    text: String = "",
    textAreaPosition: TextAreaPosition,
    isLast: Boolean,
    onAreaTapped: () -> Unit,
    onMarkOnboardingFinished: (Boolean) -> Unit
) {
    println("spotLight isLast: $isLast")
    val actual = rect.inflate(8f)
    val textMeasure = rememberTextMeasurer()
    val textStyle = TextStyle(
        fontSize = 26.sp,
        color = Color.White,
        lineHeight = TextUnit(30f, TextUnitType.Sp)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!isLast) onAreaTapped()
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()

        ) {
            val spotlightPath = Path().apply {
                addRect(actual)
            }
            clipPath(
                path = spotlightPath,
                clipOp = ClipOp.Difference
            ) {
                drawRect(color = Color.Black.copy(alpha = 0.8f))
            }
            if (!isLast) {
                val yPos = when (textAreaPosition) {
                    TextAreaPosition.ABOVE -> actual.top - (textStyle.fontSize.value * 2) - 40
                    TextAreaPosition.BELOW -> actual.bottom + 30
                }
                drawText(
                    textMeasure,
                    text = text,
                    topLeft = Offset(
                        x = 40f,
                        y = yPos
                    ),
                    style = TextStyle(
                        fontSize = 26.sp,
                        color = Color.White,
                        lineHeight = TextUnit(30f, TextUnitType.Sp)
                    )
                )
            }
        }
        if (isLast) {
            Button(onClick = { onMarkOnboardingFinished(true) }) {
                Text(text = "さぁ使ってみましょう!")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Rounded.ArrowForward, contentDescription = null)
            }
        }
    }
}

enum class TextAreaPosition {
    ABOVE,
    BELOW,
}