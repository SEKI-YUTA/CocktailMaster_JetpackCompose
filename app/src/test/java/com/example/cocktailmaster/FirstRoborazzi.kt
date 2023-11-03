package com.example.cocktailmaster

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.core.app.ActivityScenario
import com.example.cocktailmaster.ui.component.MenuButton
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class FirstRoborazzi {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val roborazziOptions = RoborazziOptions(
        compareOptions = RoborazziOptions.CompareOptions(
            changeThreshold = 0f
        )
    )

    @Test
    fun captureTest() {
        ActivityScenario.launch(MainActivity::class.java)
        composeTestRule.setContent {
            MenuButton(
                text = "テスト用のボタン",
                icon = Icons.Default.Add,
                onTapAction = {}
            )
        }
        composeTestRule.onRoot().captureRoboImage(
            filePath = "C:\\Users\\yuta\\Pictures\\RoboImage\\robo.png",
            roborazziOptions = roborazziOptions
        )
    }
}