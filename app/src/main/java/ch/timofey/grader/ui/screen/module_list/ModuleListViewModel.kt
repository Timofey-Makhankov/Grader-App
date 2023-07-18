package ch.timofey.grader.ui.screen.module_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.utils.getAverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ModuleListViewModel @Inject constructor(
    private val repository: ModuleRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val divisionId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(ModuleListState())
    val uiState: StateFlow<ModuleListState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllModules().collect { moduleList ->
                _uiState.value = _uiState.value.copy(moduleList = moduleList.filter { module -> !module.onDelete })
                if (moduleList.isNotEmpty()) {
                    val averageGrade = calculateAverageGrade(moduleList)
                    println("calculate Grade: $averageGrade")
                    repository.updateDivisionGradeById(UUID.fromString(divisionId), averageGrade)
                    _uiState.value = _uiState.value.copy(averageGrade = averageGrade.toString())
                    if (_uiState.value.averageGrade.toDouble() == 0.0) {
                        _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                    } else {
                        _uiState.value = _uiState.value.copy(averageGradeIsZero = false)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                }
                println("calculate Grade after toString: ${_uiState.value.averageGrade}")
            }
        }
    }

    fun onEvent(event: ModuleListEvent) {
        when (event) {
            is ModuleListEvent.OnFABClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateModuleScreen.withArgs(divisionId)))
            }

            is ModuleListEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ModuleListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedModule(event.id, event.value)
                }
            }

            is ModuleListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    repository.updateOnDeleteModule(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Module was deleted", true, "Undo"
                        )
                    )
                }
            }

            is ModuleListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch {
                    repository.updateOnDeleteModule(event.id, false)
                }
            }
        }
    }

    private fun calculateAverageGrade(list: List<Module>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }
        return getAverage(grades = gradeList).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}