package ch.timofey.grader.ui.screen.exam.update_exam

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.exam.ExamValidation
import ch.timofey.grader.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
class UpdateExamViewModel @Inject constructor(
    private val repository: ExamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val examId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(UpdateExamState())
    val uiState: StateFlow<UpdateExamState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getExamById(UUID.fromString(examId))?.also { exam ->
                _uiState.value = _uiState.value.copy(
                    currentExam = exam,
                    name = exam.name,
                    description = exam.description ?: "",
                    grade = exam.grade.toString(),
                    weight = exam.weight.toString(),
                    dateTaken = exam.date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                )
            } ?: run {
                /*sendUiEvents(
                    UiEvent.PopBackStack, UiEvent.ShowSnackBar(
                        message = "Module was unable to be found", withDismissAction = true
                    )
                )*/
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    fun onEvent(event: UpdateExamEvent) {
        when (event) {
            is UpdateExamEvent.OnBackButtonPress -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is UpdateExamEvent.OnNameChange -> {
                val result = ExamValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    errorMessageName = result.message
                )
            }
            is UpdateExamEvent.OnDescriptionChange -> {
                val result = ExamValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }
            is UpdateExamEvent.OnGradeChange -> {
                val result = ExamValidation.grade(event.grade, true)
                _uiState.value = _uiState.value.copy(
                    grade = event.grade,
                    validGrade = result.valid,
                    errorMessageGrade = result.message
                )
            }
            is UpdateExamEvent.OnWeightChange -> {
                val result = ExamValidation.weight(event.weight)
                _uiState.value = _uiState.value.copy(
                    weight = event.weight,
                    validWeight = result.valid,
                    errorMessageWeight = result.message
                )
            }
            is UpdateExamEvent.OnSetDate -> {
                _uiState.value = _uiState.value.copy(
                    dateTaken = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                )
            }
            is UpdateExamEvent.OnUpdateExamButtonPress -> {
                if (ExamValidation.validateAll(_uiState.value)) {
                    val updatedExam = _uiState.value.currentExam!!.copy(
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        grade = _uiState.value.grade.toDouble(),
                        weight = _uiState.value.weight.toDouble(),
                        date = LocalDate.parse(_uiState.value.dateTaken, DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.updateExam(updatedExam)
                        sendUiEvent(UiEvent.PopBackStack)
                        sendUiEvent(UiEvent.ShowSnackBar("Exam was successfully updated"))
                    }
                } else {
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "Exam was unable to be created", true
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }

    private fun sendUiEvents(vararg events: UiEvent) {
        for (event in events) {
            viewModelScope.launch (Dispatchers.Main){
                _uiEvent.send(event)
            }
        }
    }
}