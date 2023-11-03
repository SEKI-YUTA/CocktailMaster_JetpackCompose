package com.example.cocktailmaster

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.core.app.ActivityScenario
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel6Pro)
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
        val launch = ActivityScenario.launch(MainActivity::class.java)
//        どうやらActivityScenario.launch()の時点でcomposeTestRuleにコンテンツがセットされているみたい
//        なのでsetContentを呼んでからテストするとNodeが2つ以上あるって怒られる
//        composeTestRule.setContent {
//            CocktailMasterTheme {
//                Surface {
////                    MenuButton(
////                        text = "テスト用のボタン",
////                        icon = Icons.Default.Add,
////                        onTapAction = {}
////                    )
//                    Column {
//                        Text("テスト用のテキスト")
//                    }
//                }
//            }
//        }
        composeTestRule.onAllNodes(isRoot()).printToLog("onRoot")
        composeTestRule.onRoot().captureRoboImage(
//        composeTestRule.onAllNodes(isRoot()).onFirst().captureRoboImage(
            roborazziOptions = roborazziOptions
        )
    }
}