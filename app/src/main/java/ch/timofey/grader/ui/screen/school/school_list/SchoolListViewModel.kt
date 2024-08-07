package ch.timofey.grader.ui.screen.school.school_list

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.R
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.navigation.Screen
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
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolRepository,
    private val dataStore: DataStore<AppSettings>,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(SchoolListState())
    val uiState: StateFlow<SchoolListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    showPoints = it.calculatePoints,
                    swipingEnabled = it.enableSwipeToDelete,
                    minimumGrade = it.minimumGrade,
                    showNavigationIcons = it.showNavigationIcons,
                    colorGrades = it.colorGrades,
                    swapNavigation = it.swapNavigation
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllSchoolFlows().collect { schoolList ->
                _uiState.value =
                    _uiState.value.copy(schoolList = schoolList.filter { school -> !school.onDelete })
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
                viewModelScope.launch(Dispatchers.IO) {
                    deleteSchoolItems()
                }
                sendUiEvent(UiEvent.Navigate(Screen.CreateSchoolScreen.route))
            }

            is SchoolListEvent.OnDeleteItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteSchoolItems()
                }
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is SchoolListEvent.OnSwipeDelete -> {
                viewModelScope.launch (Dispatchers.IO){
                    repository.updateOnDeleteSchool(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.school_has_been_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is SchoolListEvent.OnCheckChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateIsSelectedSchool(id = event.schoolId, value = event.value)
                }
            }

            is SchoolListEvent.OnItemClickDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteSchool(event.schoolId, true)
                    repository.getAllSchoolFlows().collect { schoolList ->
                        _uiState.value =
                            _uiState.value.copy(schoolList = schoolList.filter { school -> !school.onDelete })
                    }
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.school_has_been_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is SchoolListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteSchool(event.schoolId, false)
                }
            }
        }
    }

    private fun calculateAverageGrade(list: List<School>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }.filter { it > 0 }
        return getAverage(grades = gradeList).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
    }

    private suspend fun deleteSchoolItems() {
        val schoolList = repository.getAllSchools()
        schoolList.filter { school -> school.onDelete }.forEach { school ->
            repository.deleteSchool(school.id)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
}