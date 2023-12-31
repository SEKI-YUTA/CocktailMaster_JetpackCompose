package com.yuta.cocktailmaster.ui.model

import androidx.compose.ui.geometry.Rect
import com.yuta.cocktailmaster.ui.component.TextAreaPosition

data class OnboardingState(
    val items: List<OnboardingItem> = listOf(),
    val currentOnboardingStep: Int
) {
    companion object {
        val INITIAL = OnboardingState(
            items = List(4) { OnboardingItem.INITIAL },
            currentOnboardingStep = 0
        )
    }
}
data class OnboardingItem(
    val pos: Rect,
    val text: String,
    val textAreaPosition: TextAreaPosition = TextAreaPosition.BELOW
) {
    companion object {
        val INITIAL = OnboardingItem(
            pos = Rect.Zero,
            text = ""
        )
    }
}
