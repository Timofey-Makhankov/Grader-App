package ch.timofey.grader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.screen.school.school_list.SchoolListEvent
import ch.timofey.grader.ui.screen.school.school_list.SchoolListViewModel
import ch.timofey.grader.utils.UiEvent
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolListViewModelTest {

    private lateinit var viewModel: SchoolListViewModel
    private lateinit var repository: SchoolRepository
    private lateinit var dataStore: DataStore<AppSettings>

    @get:Rule
    val dispatcherRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = mock()
        dataStore = mock()

        whenever(repository.getAllSchoolFlows()).thenReturn(flowOf(emptyList()))
        whenever(dataStore.data).thenReturn(flowOf(AppSettings()))

        viewModel = SchoolListViewModel(repository, dataStore)
    }

    @Test
    fun `calculateAverageGrade returns correct average`() = runTest {
        val testSchoolList = listOf(
            School(UUID.randomUUID(), "School 1", "", "", "", "", true, 4.0),
            School(UUID.randomUUID(), "School 2", "", "", "", "", true, 6.0)
        )

        val avgGrade = viewModel.uiState.first().averageGrade.toDoubleOrNull()
        if (avgGrade != null) {
            assertEquals(5.0, avgGrade, 0.01)
        }
    }

    @Test
    fun `deleteSchoolItems removes marked schools`() = runTest {
        val testSchool = School(UUID.randomUUID(), "School to delete", "", "", "", "", true, 3.0)

        whenever(repository.getAllSchools()).thenReturn(listOf(testSchool))

        viewModel.onEvent(SchoolListEvent.OnDeleteItems("test_route"))
        viewModel.onEvent(SchoolListEvent.OnUndoDeleteClick(testSchool.id))

        val updatedList = viewModel.uiState.first().schoolList
        assertTrue(updatedList.none { it.id == testSchool.id })
    }

    @Test
    fun `sendUiEvent emits correct event`() = runTest {
        val testEvent = UiEvent.ShowSnackBar("Test message", true, "Undo")
        val job = launch { viewModel.uiEvent.collect { event -> assertEquals(testEvent, event) } }
        viewModel.onEvent(SchoolListEvent.OnSwipeDelete(UUID.randomUUID()))
        job.cancel()
    }

    @Test
    fun `onEvent updates state on school creation`() = runTest {
        viewModel.onEvent(SchoolListEvent.OnCreateSchool)

        val uiEvent = viewModel.uiEvent.first()
        assertTrue(uiEvent is UiEvent.Navigate)
    }
}
