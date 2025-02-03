package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.timofey.grader.ui.screen.calculator.CalculatorScreen
import ch.timofey.grader.ui.screen.calculator.CalculatorState
import ch.timofey.grader.ui.theme.GraderTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorScreenLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculatorScreen_displaysKeyElements() {
        val state = CalculatorState(
            grades = listOf("80", "90", "70"),
            weights = listOf("1.0", "1.0", "1.0"),
            rowCount = 3,
            calculatedGrade = 80.0,
            calculatePoints = true,
            showColoredGrade = false,
            minimumGrade = 0.0
        )
        composeTestRule.setContent {
            GraderTheme {
                CalculatorScreen(
                    state = state,
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                    onEvent = {},
                    onNavigate = {}
                )
            }
        }
        // Verify that expected text labels are displayed.
        composeTestRule.onNodeWithText("Calculated Result").assertIsDisplayed()
        composeTestRule.onNodeWithText("80.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Field").assertIsDisplayed()
    }
}