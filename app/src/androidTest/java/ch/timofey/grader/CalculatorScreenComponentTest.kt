package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
class CalculatorScreenComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculatorScreen_gradeInputTriggersEvent() {
        val events = mutableListOf<CalculatorEvent>()
        val fakeOnEvent: (CalculatorEvent) -> Unit = { event ->
            events.add(event)
        }
        val state = CalculatorState(
            grades = listOf("", "", ""),
            weights = listOf("1.0", "1.0", "1.0"),
            rowCount = 3,
            calculatedGrade = 0.0,
            calculatePoints = false,
            showColoredGrade = false,
            minimumGrade = 0.0
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
        // Assume that the first GradeInputField uses the test tag "gradeInput_0"
        composeTestRule.onNodeWithTag("gradeInput_0").performTextInput("80")
        val eventFired = events.any { it is CalculatorEvent.OnGradeChange && it.index == 0 && it.grade == "80" }
        assertTrue("Expected an OnGradeChange event for index 0 with grade \"80\"", eventFired)
    }
}