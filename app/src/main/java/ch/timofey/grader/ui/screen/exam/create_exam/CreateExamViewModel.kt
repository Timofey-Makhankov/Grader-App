package ch.timofey.grader.ui.screen.exam.exam_list.create_exam

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
import java.time.format.DateTimeParseException
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
                if (event.name.isNotBlank()) {
                    if (event.name.length > 60) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                "The Name cannot Exceed over 60 Characters", true
                            )
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(name = event.name)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        name = "", validName = false, errorMessageName = "Please Enter a valid Name"
                    )
                }
            }

            is CreateExamEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateExamEvent.OnGradeChange -> {
                if (event.grade.isNotBlank()) {
                    if (numberIsValidFloatingPointNumber(event.grade)) {
                        _uiState.value = _uiState.value.copy(grade = event.grade, validGrade = true)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            grade = event.grade,
                            validGrade = false,
                            errorMessageGrade = "Please enter a Grade that is a Floating Point Number"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        grade = "",
                        validGrade = false,
                        errorMessageGrade = "Please Enter a valid Grade"
                    )
                }
            }

            is CreateExamEvent.OnWeightChange -> {
                when {
                    event.weight.isNotBlank() -> _uiState.value = _uiState.value.copy(
                        weight = "",
                        validWeight = false,
                        errorMessageWeight = "Please Enter a valid Weight"
                    )

                    !numberIsValidFloatingPointNumber(event.weight) -> _uiState.value =
                        _uiState.value.copy(
                            weight = event.weight,
                            validWeight = false,
                            errorMessageWeight = "Please enter a Weight that is a Floating Point Number"
                        )

                    !weightOverZeroAndUnderOrEqualOne(event.weight.toDouble()) -> _uiState.value =
                        _uiState.value.copy(
                            weight = event.weight,
                            validWeight = false,
                            errorMessageWeight = "Please Enter a Weight that is over 0 and under or equal 1"
                        )

                    else -> _uiState.value =
                        _uiState.value.copy(weight = event.weight, validWeight = true)
                }
            }

            is CreateExamEvent.OnDateChange -> {
                if (event.date.isNotBlank()) {
                    if (dateIsValid(event.date)) {
                        _uiState.value = _uiState.value.copy(date = event.date)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            date = event.date,
                            validDate = false,
                            errorMessageDate = "Please Enter a Valid Date in YYYY-mm-dd format"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        date = "", validDate = false, errorMessageDate = "Please Enter a valid Date"
                    )
                }
            }

            is CreateExamEvent.OnSetDate -> {
                _uiState.value = _uiState.value.copy(
                    date = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault())
                        .toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                )
            }

            is CreateExamEvent.OnCreateExamButtonPress -> {
                if (isValidInput(_uiState.value)) {
                    viewModelScope.launch {
                        repository.saveExam(
                            Exam(
                                id = UUID.randomUUID(),
                                name = _uiState.value.name,
                                description = _uiState.value.description,
                                grade = _uiState.value.grade.toDouble(),
                                weight = _uiState.value.weight.toDouble(),
                                date = LocalDate.parse(
                                    _uiState.value.date, DateTimeFormatter.ISO_LOCAL_DATE
                                ),
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

    private fun isValidInput(state: CreateExamState): Boolean {
        return if (state.name.isNotBlank() && state.date.isNotBlank() && state.grade.isNotBlank() && state.weight.isNotBlank()) {
            state.validName && state.validDate && state.validGrade && state.validWeight
        } else {
            false
        }
    }

    private fun dateIsValid(date: String): Boolean {
        return try {
            LocalDate.parse(date)
            true
        } catch (_: DateTimeParseException) {
            false
        }
    }

    private fun numberIsValidFloatingPointNumber(number: String): Boolean {
        return try {
            number.toDouble()
            true
        } catch (_: NumberFormatException) {
            false
        }
    }

    private fun weightOverZeroAndUnderOrEqualOne(weight: Double): Boolean {
        return weight > 0 && weight <= 1.0
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}