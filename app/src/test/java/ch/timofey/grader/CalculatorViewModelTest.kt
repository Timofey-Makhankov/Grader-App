package ch.timofey.grader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ch.timofey.grader.ui.screen.calculator.CalculatorEvent
import ch.timofey.grader.ui.screen.calculator.CalculatorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CalculatorViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CalculatorViewModel(FakeDataStore())
        runBlocking { viewModel.uiState.first() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun calculateAverage_validInput_returnsCorrectResult() = runBlocking {
        viewModel.onEvent(CalculatorEvent.OnGradeChange(index = 0, grade = "80"))
        viewModel.onEvent(CalculatorEvent.OnGradeChange(index = 1, grade = "90"))
        viewModel.onEvent(CalculatorEvent.OnGradeChange(index = 2, grade = "70"))
        // Advance pending coroutines
        testDispatcher.scheduler.advanceUntilIdle()
        val calculatedGrade = viewModel.uiState.value.calculatedGrade
        // Expected average: (80 + 90 + 70) / 3 = 80.0
        assertEquals(80.0, calculatedGrade, 0.001)
    }

    @Test
    fun predictRequiredGrade_returnsCorrectValue() = runBlocking {
        val goal = 85.0
        val currentTotal = 240.0
        val count = 3
        val additionalCount = 1
        val result = viewModel.predictRequiredGrade(goal, currentTotal, count, additionalCount)
        val expected = (goal * (count + additionalCount)) - currentTotal
        assertEquals(expected, result, 0.001)
    }
}