package ch.timofey.grader

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.ui.screen.school.school_list.SchoolListScreen
import ch.timofey.grader.ui.screen.school.school_list.SchoolListState
import ch.timofey.grader.ui.theme.GraderTheme
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class SchoolListScreenLayoutTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun schoolListScreen_displaysSchoolItems() {
        val fakeState = SchoolListState(
            schoolList = listOf(
                School(
                    id = UUID.randomUUID(),
                    name = "Technische Berufsschule Zürich",
                    description = "A sample school",
                    address = "Address 1",
                    zipCode = "8005",
                    city = "Zürich",
                    grade = 4.5,
                    isSelected = false
                ),
                School(
                    id = UUID.randomUUID(),
                    name = "Schulhaus Riedenhalden",
                    description = "Another school",
                    address = "Address 2",
                    zipCode = "8046",
                    city = "Zürich",
                    grade = 0.0,
                    isSelected = false
                )
            ),
            averageGrade = "0.0",
            averageGradeIsZero = true,
            showNavigationIcons = true,
            colorGrades = true,
            swipingEnabled = true,
            minimumGrade = 0.0
        )
        composeTestRule.setContent {
            GraderTheme {
                SchoolListScreen(
                    drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
                    state = fakeState,
                    onEvent = {},
                    uiEvent = emptyFlow(),
                    onNavigate = {},
                    snackBarHostState = SnackbarHostState(),
                    savedStateHandle = SavedStateHandle()
                )
            }
        }
        composeTestRule.onNodeWithText("Technische Berufsschule Zürich").assertIsDisplayed()
    }
}