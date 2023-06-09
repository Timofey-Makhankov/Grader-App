package ch.timofey.grader.ui.screen.school_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.getAverageGrade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(SchoolListState())
    val uiState: StateFlow<SchoolListState> = _uiState.asStateFlow()

    private var deletedSchool: School? = null

    init {
        viewModelScope.launch {
            repository.getAllSchools().collect { schoolList ->
                _uiState.value = _uiState.value.copy(schoolList = schoolList)
                if (schoolList.isNotEmpty()) {
                    val averageGrade = calculateAverageGrade(schoolList)
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

    fun onEvent(event: SchoolListEvent) {
        when (event) {
            is SchoolListEvent.OnCreateSchool -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateSchoolScreen.route))
            }

            is SchoolListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedSchool(id = event.id, value = event.value)
                }
            }

            is SchoolListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    deletedSchool = event.school
                    repository.deleteSchool(event.school)
                    sendUiEvent(UiEvent.ShowSnackBar("School Deleted"))
                }
            }

            is SchoolListEvent.OnUndoDeleteClick -> {
                println("Clicked the undo Button")
            }
        }
    }

    private fun calculateAverageGrade(list: List<School>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }
        return getAverageGrade(grades = gradeList).toBigDecimal()
            .setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}