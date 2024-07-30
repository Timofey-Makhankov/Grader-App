package ch.timofey.grader.ui.screen.exam.exam_list

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.R
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.utils.getAverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
    private val repository: ExamRepository,
    private val moduleRepository: ModuleRepository,
    private val divisionRepository: DivisionRepository,
    private val schoolRepository: SchoolRepository,
    private val dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moduleId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(ExamListState())
    val uiState: StateFlow<ExamListState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    showPoints = it.calculatePoints,
                    swipingEnabled = it.enableSwipeToDelete,
                    minimumGrade = it.minimumGrade,
                    showNavigationIcons = it.showNavigationIcons,
                    colorGrades = it.colorGrades,
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllExamsFromModuleId(UUID.fromString(moduleId)).collect { examList ->
                println(examList)
                println(moduleId)
                _uiState.value =
                    _uiState.value.copy(exams = examList.filter { exam -> !exam.onDelete })
                if (examList.isNotEmpty()) {
                    val averageGrade = calculateAverageGrade(examList)
                    repository.updateModuleGradeById(UUID.fromString(moduleId), averageGrade)
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
            moduleRepository.getModuleById(UUID.fromString(moduleId))?.let { module ->
                divisionRepository.getDivision(module.divisionId)?.let { division ->
                    schoolRepository.getSchoolById(division.schoolId)?.let { school ->
                        _uiState.value = _uiState.value.copy(
                            locationsTitles = listOf(
                                school.name, division.name, module.name, "Exams"
                            )
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ExamListEvent) {
        when (event) {
            is ExamListEvent.OnBackButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteExamItems()
                }
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ExamListEvent.OnFABClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteExamItems()
                }
                sendUiEvent(UiEvent.Navigate(Screen.CreateExamScreen.withArgs(moduleId)))
            }

            is ExamListEvent.OnDeleteItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteExamItems()
                }
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is ExamListEvent.OnCheckChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateIsSelectedExam(event.id, event.value)
                }
            }

            is ExamListEvent.OnDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDelete(event.examId, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.exam_has_been_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is ExamListEvent.OnSwipeDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDelete(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            R.string.exam_was_deleted.toString(), true, R.string.undo.toString()
                        )
                    )
                }
            }

            is ExamListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDelete(event.id, false)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }

    private suspend fun deleteExamItems() {
        val examList = repository.getAllExams()
        examList.filter { exam -> exam.onDelete }.forEach { exam ->
            repository.deleteExamById(exam.id)
        }
    }

    private fun calculateAverageGrade(list: List<Exam>): Double {
        val validExams = list.map { it }.filter { it.isSelected && it.grade > 0 }
        val weightList = validExams.map { it.weight }
        val gradeList = validExams.map { it.grade }
        return getAverage(grades = gradeList, weights = weightList).toBigDecimal()
            .setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}