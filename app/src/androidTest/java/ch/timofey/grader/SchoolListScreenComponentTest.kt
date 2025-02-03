package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.screen.school.school_list.SchoolListEvent
import ch.timofey.grader.ui.screen.school.school_list.SchoolListScreen
import ch.timofey.grader.ui.screen.school.school_list.SchoolListState
import ch.timofey.grader.ui.theme.GraderTheme
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID
import org.junit.Assert.assertTrue

@RunWith(AndroidJUnit4::class)
class SchoolListScreenComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun schoolListScreen_clickOnSchoolCard_triggersOnEvent() {
        val fakeSchool = School(
            id = UUID.randomUUID(),
            name = "Test School",
            description = "A test school",
            address = "Test Address",
            zipCode = "12345",
            city = "Test City",
            grade = 0.0,
            isSelected = false
        )

        val fakeState = SchoolListState(
            schoolList = listOf(fakeSchool),
            averageGrade = "0.0",
            averageGradeIsZero = true,
            showNavigationIcons = true,
            colorGrades = true,
            swipingEnabled = true,
            minimumGrade = 0.0
        )

        var eventCalled = false
        val fakeOnEvent: (SchoolListEvent) -> Unit = { event ->
            if (event is SchoolListEvent.OnItemClickDelete && event.schoolId == fakeSchool.id)
                eventCalled = true
        }

        composeTestRule.setContent {
            GraderTheme {
                SchoolListScreen(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                    state = fakeState,
                    onEvent = fakeOnEvent,
                    uiEvent = emptyFlow(),
                    onNavigate = {},
                    snackBarHostState = SnackbarHostState(),
                    savedStateHandle = SavedStateHandle()
                )
            }
        }

        composeTestRule.waitForIdle()

        // Ensure the school card exists
        composeTestRule.onNodeWithTag("schoolCard_Test School")
            .assertExists("ERROR: School Card Not Found! Check if it's rendered properly.")
            .performClick()

        composeTestRule.waitForIdle() // Wait for the animation

        // Print all UI elements to check if delete button exists
        composeTestRule.onAllNodes(isRoot()).printToLog("UI_TREE_DUMP")

        // Wait extra time to allow animations to finish
        composeTestRule.mainClock.advanceTimeBy(500) // Wait 500ms more

        // Now check if delete button exists
        composeTestRule.onNodeWithTag("deleteIcon_Test School")
            .assertExists("ERROR: Delete Button Not Found! Ensure the card expands when clicked.")
            .performClick()

        assertTrue("Expected OnItemClickDelete event for 'Test School'", eventCalled)
    }


}
