package ch.timofey.grader.ui.screen.exam_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.utils.getAverage
import ch.timofey.grader.ui.utils.getAverageGrade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
    private val repository: ExamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moduleId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(ExamListState())
    val uiState: StateFlow<ExamListState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        println("inside the init Block")
        viewModelScope.launch {
            repository.getAllExams().collect{ examList ->
                _uiState.value = _uiState.value.copy(exams = examList)
                if (examList.isNotEmpty()){
                    println("inside the if check block in init $examList")
                    val validExams = examList.map { it }.filter { it.isSelected }
                    val weightList = validExams.map { it.weight }
                    val gradeList = validExams.map { it.grade }
                    val averageGrade =  getAverage(grades = gradeList, weights = weightList).toString()
                    _uiState.value = _uiState.value.copy(average_grade = averageGrade)
                }
            }
        }
    }

    fun onEvent(event: ExamListEvent){
        when (event) {
            is ExamListEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ExamListEvent.OnFABClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateExamScreen.withArgs(moduleId)))
            }
            is ExamListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedExam(event.id, event.value)
                }
            }
            is ExamListEvent.OnSwipeDelete -> {
                viewModelScope.launch{
                    repository.deleteExam(event.exam)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}