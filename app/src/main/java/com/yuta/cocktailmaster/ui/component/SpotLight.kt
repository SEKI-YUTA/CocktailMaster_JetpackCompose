package com.yuta.cocktailmaster.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@Composable
fun SpotLight(
    rect: Rect,
    topAppBarSize: IntSize,
    text: String = "",
    textAreaPosition: TextAreaPosition,
    isLast: Boolean,
    onAreaTapped: () -> Unit,
) {
    println("spotLight isLast: $isLast")
    val actual = rect.translate(
        translateX = 0f,
        translateY = -topAppBarSize.height.toFloat()
    ).inflate(8f)
    val textMeasure = rememberTextMeasurer()
    val textStyle = TextStyle(
        fontSize = 26.sp,
        color = Color.White,
        lineHeight = TextUnit(30f, TextUnitType.Sp)
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    onAreaTapped()
                }
            }
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
        if(isLast) {
            val width = size.width
            val height = size.height
            val textLayoutResult = textMeasure.measure(
                text = AnnotatedString("さぁ使ってみましょう!"),
                style = textStyle
            )
            val textSize = textLayoutResult.size
            drawText(
                textMeasure,
                text = "さぁ使ってみましょう!",
                topLeft = Offset(
                    x = (width - textSize.width) / 2,
                    y = (height - textSize.height) / 2
                ),
                style = textStyle
            )
        } else {

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
}

enum class TextAreaPosition {
    ABOVE,
    BELOW,
}