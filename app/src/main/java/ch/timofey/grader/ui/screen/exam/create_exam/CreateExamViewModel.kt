package ch.timofey.grader.ui.screen.exam.create_exam

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.exam.ExamValidation
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
import java.time.format.FormatStyle
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExamViewModel @Inject constructor(
    private val repository: ExamRepository, savedStateHandle: SavedStateHandle
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
                val result = ExamValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name, validName = result.valid, errorMessageName = result.message
                )
            }

            is CreateExamEvent.OnDescriptionChange -> {
                val result = ExamValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is CreateExamEvent.OnGradeChange -> {
                val result = ExamValidation.grade(event.grade, true)
                _uiState.value = _uiState.value.copy(
                    grade = event.grade,
                    validGrade = result.valid,
                    errorMessageGrade = result.message
                )
            }

            is CreateExamEvent.OnWeightChange -> {
                val result = ExamValidation.weight(event.weight)
                _uiState.value = _uiState.value.copy(
                    weight = event.weight,
                    validWeight = result.valid,
                    errorMessageWeight = result.message
                )
            }

            is CreateExamEvent.OnSetDate -> {
                _uiState.value = _uiState.value.copy(
                    dateTaken = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                )
            }

            is CreateExamEvent.OnCreateExamButtonPress -> {
                if (ExamValidation.validateAll(_uiState.value)) {
                    viewModelScope.launch {
                        repository.saveExam(
                            Exam(
                                id = UUID.randomUUID(),
                                name = _uiState.value.name,
                                description = _uiState.value.description,
                                grade = _uiState.value.grade.toDouble(),
                                weight = _uiState.value.weight.toDouble(),
                                date = LocalDate.parse(_uiState.value.dateTaken, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
                                moduleId = UUID.fromString(moduleId)
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "Exam Created", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(), "Exam was unable to be created", Toast.LENGTH_SHORT
                    ).show()
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