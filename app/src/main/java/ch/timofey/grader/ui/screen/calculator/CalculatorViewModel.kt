package ch.timofey.grader.ui.screen.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.ui.utils.UiEvent
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

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.OnWeightChange -> {
                val currentList: ArrayList<Double> = ArrayList(_uiState.value.weight)
                currentList[event.id] = event.weight
                _uiState.value = _uiState.value.copy(weight = currentList.toList())
            }

            is CalculatorEvent.OnGradeChange -> {
                val currentList: ArrayList<Double> = ArrayList(_uiState.value.grade)
                currentList[event.id] = event.grade
                _uiState.value = _uiState.value.copy(
                    grade = currentList.toList(), rowCount = uiState.value.rowCount + 1
                )
            }

            is CalculatorEvent.OnFieldEmpty -> {
                _uiState.value = _uiState.value.copy(rowCount = _uiState.value.rowCount - 1)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}