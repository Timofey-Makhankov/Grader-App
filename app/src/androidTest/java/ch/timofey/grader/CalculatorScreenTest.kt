package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.timofey.grader.ui.screen.calculator.CalculatorEvent
import ch.timofey.grader.ui.screen.calculator.CalculatorScreen
import ch.timofey.grader.ui.screen.calculator.CalculatorState
import ch.timofey.grader.ui.theme.GraderTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class CalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculatorScreen_displaysCorrectUI() {
        val state = CalculatorState(
            grades = listOf("", "", ""),
            weights = listOf("1.0", "1.0", "1.0"),
            rowCount = 3,
            calculatedGrade = 85.0,
            calculatePoints = true
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

        composeTestRule.onNodeWithTag("calculatedResult").assertIsDisplayed()
        composeTestRule.onNodeWithTag("addFieldButton").assertIsDisplayed()
    }

    @Test
    fun calculatorScreen_gradeInputUpdatesCalculation() {
        val events = mutableListOf<CalculatorEvent>()
        val fakeOnEvent: (CalculatorEvent) -> Unit = { event -> events.add(event) }

        val state = CalculatorState(
            grades = listOf("", "", ""),
            weights = listOf("1.0", "1.0", "1.0"),
            rowCount = 3,
            calculatedGrade = 0.0
        )

        composeTestRule.setContent {
            GraderTheme {
                CalculatorScreen(
                    state = state,
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                    onEvent = fakeOnEvent,
                    onNavigate = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("gradeInput_0").performTextInput("90")
        val eventTriggered = events.any { it is CalculatorEvent.OnGradeChange && it.index == 0 && it.grade == "90" }
        assert(eventTriggered)
    }
}
