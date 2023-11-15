package ch.timofey.grader.ui.screen.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.utils.getAverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState: StateFlow<CalculatorState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        _uiState.value = _uiState.value.copy(
            grades = listOf("", "", ""), weights = listOf("1.0", "1.0", "1.0")
        )
    }

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.OnAddFieldClick -> {
                _uiState.value = _uiState.value.copy(
                    rowCount = _uiState.value.rowCount + 1,
                    grades = _uiState.value.grades + "",
                    weights = _uiState.value.weights + "1.0"
                )

            }

            is CalculatorEvent.OnGradeChange -> {
                _uiState.value =
                    _uiState.value.copy(grades = _uiState.value.grades.mapIndexed { index, s -> if (index == event.index) event.grade else s })
                updateGrade()
            }

            is CalculatorEvent.OnRemoveFieldClick -> {
                _uiState.value = _uiState.value.copy(
                    rowCount = _uiState.value.rowCount - 1,
                    grades = _uiState.value.grades.dropLast(1),
                    weights = _uiState.value.weights.dropLast(1)
                )
                updateGrade()
            }

            is CalculatorEvent.OnWeightChange -> {
                _uiState.value =
                    _uiState.value.copy(weights = _uiState.value.weights.mapIndexed { index, s -> if (index == event.index) event.weight else s })
                updateGrade()
            }
        }
    }

    private fun updateGrade(){
        _uiState.value = _uiState.value.copy(
            calculatedGrade = calculateGrade(
                _uiState.value.grades, _uiState.value.weights
            )
        )
    }

    private fun calculateGrade(grades: List<String>, weights: List<String>): Double {
        val binaryMapGrades = grades.map { it.toDoubleOrNull() }.map { if (it == null) 0 else 1 }
        val binaryMapWeights = weights.map { it.toDoubleOrNull() }.map { if (it == null) 0 else 1 }
        val combinedBinaryList = ArrayList<Int>()
        for (index in grades.indices) {
            combinedBinaryList.add(binaryMapGrades[index] * binaryMapWeights[index])
        }
        Log.v("calculate Grade", "$grades | $binaryMapGrades | $combinedBinaryList")
        return getAverage(
            grades.filterIndexed { index, _ -> combinedBinaryList[index] != 0 }
            .map { it.toDouble() },
            weights.filterIndexed { index, _ -> combinedBinaryList[index] != 0 }
                .map { it.toDouble() })
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}