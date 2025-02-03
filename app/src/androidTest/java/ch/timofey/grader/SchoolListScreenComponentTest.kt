package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
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
        // Look for a test tag "schoolCard_Test School"
        composeTestRule.onNodeWithText("Test School").assertIsDisplayed().performTouchInput {
            longClick()
        }
        composeTestRule.onNodeWithTag("deleteIcon_Test School").assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()
        assertTrue("Expected OnItemClickDelete event for 'Test School'", eventCalled)    }
}