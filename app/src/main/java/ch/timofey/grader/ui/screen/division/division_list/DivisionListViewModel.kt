package ch.timofey.grader.ui.screen.division.division_list

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.utils.getAverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject
import ch.timofey.grader.R

@HiltViewModel
class DivisionListViewModel @Inject constructor(
    private val repository: DivisionRepository,
    private val schoolRepository: SchoolRepository,
    private val dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val schoolId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(DivisionListState())
    val uiState: StateFlow<DivisionListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    showPoints = it.calculatePoints,
                    swipingEnabled = it.enableSwipeToDelete,
                    showNavigationIcons = it.showNavigationIcons,
                    colorGrades = it.colorGrades,
                    minimumGrade = it.minimumGrade,
                    swapNavigation = it.swapNavigation
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllDivisionsFromSchoolId(UUID.fromString(schoolId))
                .collect { divisionList ->
                    println(R.string.division_list.toString() + " $divisionList")
                    _uiState.value =
                        _uiState.value.copy(divisionList = divisionList.filter { division -> !division.onDelete })
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
        viewModelScope.launch(Dispatchers.IO) {
            val school = schoolRepository.getSchoolById(UUID.fromString(schoolId))
            school?.let {
                _uiState.value = _uiState.value.copy(
                    locationTitles = listOf(it.name, "Divisions")
                )
            }
        }
    }

    fun onEvent(event: DivisionListEvent) {
        when (event) {
            is DivisionListEvent.OnReturnBack -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteDivisionItems()
                }
                sendUiEvent(UiEvent.PopBackStack)
            }

            is DivisionListEvent.OnCreateDivision -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteDivisionItems()
                }
                sendUiEvent(UiEvent.Navigate(Screen.CreateDivisionScreen.withArgs(schoolId)))
            }

            is DivisionListEvent.OnDeleteItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteDivisionItems()
                }
                sendUiEvent(UiEvent.Navigate(route = event.route))
            }

            is DivisionListEvent.OnCheckChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateIsSelectedDivision(event.id, event.value)
                }
            }

            is DivisionListEvent.OnSwipeDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteDivision(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.division_was_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is DivisionListEvent.OnDeleteIconClick -> {
                Log.d("DivisionListViewModel", R.string.delete_button_clicked.toString())
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteDivision(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.division_was_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is DivisionListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteDivision(event.id, false)
                }
            }
        }
    }

    private fun calculateAverageGrade(list: List<Division>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }
        return getAverage(grades = gradeList).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
    }

    private suspend fun deleteDivisionItems() {
        val divisionList = repository.getAllDivisions()
        divisionList.filter { division -> division.onDelete }.forEach { division ->
            repository.deleteDivision(division.id)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
}