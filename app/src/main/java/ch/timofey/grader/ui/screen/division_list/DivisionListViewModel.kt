package ch.timofey.grader.ui.screen.division_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.utils.getAverageGrade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DivisionListViewModel @Inject constructor(
    private val repository: DivisionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val schoolId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(DivisionListState())
    val uiState: StateFlow<DivisionListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllDivisionsFromSchoolId(UUID.fromString(schoolId)).collect { divisionList ->
                _uiState.value = _uiState.value.copy(divisionList = divisionList)
                if (divisionList.isNotEmpty()) {
                    val averageGrade = calculateAverageGrade(divisionList)
                    repository.updateSchoolGradeById(UUID.fromString(schoolId), averageGrade)
                    _uiState.value = _uiState.value.copy(averageGrade = averageGrade.toString())
                    if (_uiState.value.averageGrade.toDouble() == 0.0) {
                        _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                    } else {
                        _uiState.value = _uiState.value.copy(averageGradeIsZero = false)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                }
            }
        }
    }

    fun onEvent(event: DivisionListEvent) {
        when (event) {
            is DivisionListEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is DivisionListEvent.OnCreateDivision -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateDivisionScreen.withArgs(schoolId)))
            }
            is DivisionListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedDivision(event.id, event.value)
                }
            }
            is DivisionListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    repository.deleteDivision(event.division)
                }
            }
        }
    }

    private fun calculateAverageGrade(list: List<Division>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }
        return getAverageGrade(grades = gradeList).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}