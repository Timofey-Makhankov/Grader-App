package ch.timofey.grader.ui.screen.create_exam

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExamViewModel @Inject constructor(
    private val repository: ExamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moduleId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(CreateExamState())
    val uiState: StateFlow<CreateExamState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateExamEvent) {
        when (event) {
            is CreateExamEvent.OnBackButtonPress -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is CreateExamEvent.OnNameChange -> {
                _uiState.value = _uiState.value.copy(name = event.name)
            }

            is CreateExamEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateExamEvent.OnGradeChange -> {
                _uiState.value = _uiState.value.copy(grade = event.grade)
            }

            is CreateExamEvent.OnWeightChange -> {
                _uiState.value = _uiState.value.copy(weight = event.weight)
            }

            is CreateExamEvent.OnDateChange -> {
                _uiState.value = _uiState.value.copy(date = event.date)
            }

            is CreateExamEvent.OnSetDate -> {
                _uiState.value = _uiState.value.copy(
                    date = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault())
                        .toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                )
            }

            is CreateExamEvent.OnCreateExamButtonPress -> {
                viewModelScope.launch {
                    repository.saveExam(
                        Exam(
                            id = UUID.randomUUID(),
                            name = _uiState.value.name,
                            description = _uiState.value.description,
                            grade = _uiState.value.grade.toDouble(),
                            weight = _uiState.value.weight.toDouble(),
                            date = LocalDate.parse(_uiState.value.date, DateTimeFormatter.ISO_LOCAL_DATE),
                            module = UUID.fromString(moduleId)
                        )
                    )
                }
                sendUiEvent(UiEvent.PopBackStack)
                Toast.makeText(GraderApp.getContext(), "Exam Created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}