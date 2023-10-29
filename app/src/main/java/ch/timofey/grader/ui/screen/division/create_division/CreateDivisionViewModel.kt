package ch.timofey.grader.ui.screen.division.division_list.create_division

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Year
import java.time.format.DateTimeParseException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateDivisionViewModel @Inject constructor(
    private val repository: DivisionRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(CreateDivisionState())
    val uiState: StateFlow<CreateDivisionState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateDivisionEvent) {
        when (event) {
            is CreateDivisionEvent.OnNameChange -> {
                if (event.name.isNotBlank()) {
                    if (event.name.length > 60) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                "The Name has to be not over 60 characters long", true
                            )
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(name = event.name, validName = true)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        name = "", validName = false, errorMessageName = "Please Enter a Valid Name"
                    )
                }
            }

            is CreateDivisionEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateDivisionEvent.OnYearChange -> {
                if (event.year.isNotBlank()) {
                    if (validateYear(event.year)) {
                        if (event.year.length > 9) {
                            sendUiEvent(
                                UiEvent.ShowSnackBar(
                                    "The Year cannot exceed the length of 9 characters", true
                                )
                            )
                        } else {
                            _uiState.value =
                                _uiState.value.copy(year = event.year, validYear = true)
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(
                            year = event.year,
                            validYear = false,
                            errorMessageYear = "Enter a Valid Year"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        year = "", validYear = false, errorMessageYear = "Please Enter a Year"
                    )
                }

            }

            is CreateDivisionEvent.OnCreateDivision -> {
                if (isValidInput(_uiState.value)) {
                    val newDivision = Division(
                        id = UUID.randomUUID(),
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        schoolYear = _uiState.value.year.toInt(),
                        schoolId = UUID.fromString(id),

                        )
                    println(newDivision)
                    viewModelScope.launch {
                        repository.saveDivision(newDivision)
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "Division Created", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(),
                        "Division was unable to be created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is CreateDivisionEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun isValidInput(state: CreateDivisionState): Boolean {
        return if (state.name.isNotBlank() && validateYear(state.year)) {
            state.validName && state.validYear
        } else {
            false
        }
    }

    private fun validateYear(year: String): Boolean {
        return try {
            Year.parse(year)
            true
        } catch (_: DateTimeParseException) {
            false
        }
    }
}