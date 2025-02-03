package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
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

        // Ensure UI is fully composed before assertions
        composeTestRule.waitForIdle()

        // Verify the calculated result is displayed
        composeTestRule.onNodeWithTag("calculatedResult", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("calculatedResult", useUnmergedTree = true).assertIsDisplayed()

        // Verify that the Add Field button is displayed
        composeTestRule.onNodeWithTag("addFieldButton", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithTag("addFieldButton", useUnmergedTree = true).assertIsDisplayed()
    }
}