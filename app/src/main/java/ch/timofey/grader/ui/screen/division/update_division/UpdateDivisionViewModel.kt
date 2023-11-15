package ch.timofey.grader.ui.screen.division.update_division

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.ui.utils.RegexPatterns
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UpdateDivisionViewModel @Inject constructor(
    private val repository: DivisionRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val divisionId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(UpdateDivisionState())
    val uiState: StateFlow<UpdateDivisionState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getDivision(UUID.fromString(divisionId)).apply {
                this?.let { division ->
                    _uiState.value = _uiState.value.copy(
                        currentDivision = division,
                        name = division.name,
                        description = division.description ?: "",
                        year = division.schoolYear.toString()
                    )
                } ?: run {
                    sendUiEvent(
                        UiEvent.PopBackStack
                    )
                }
            }
        }
    }

    fun onEvent(event: UpdateDivisionEvent) {
        when (event) {
            is UpdateDivisionEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is UpdateDivisionEvent.OnNameChange -> {
                if (event.name.isNotBlank()) {
                    if (event.name.length > 60) {
                        sendUiEvent(
                            UiEvent.ShowSnackBar(
                                "The Name has to be not over 60 characters long",
                                withDismissAction = true
                            )
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(name = event.name, validName = true)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        name = "",
                        validName = false,
                        errorMessageName = "Please Enter a valid School Name"
                    )
                }
            }

            is UpdateDivisionEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is UpdateDivisionEvent.OnYearChange -> {
                _uiState.value = _uiState.value.copy(description = event.year)
            }

            is UpdateDivisionEvent.OnUpdateDivision -> {
                if (isValidateInput(_uiState.value)) {
                    val updatedDivision = _uiState.value.currentDivision!!.copy(
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        schoolYear = _uiState.value.year.toInt()
                    )

                    viewModelScope.launch {
                        repository.updateDivision(updatedDivision)
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "Division Updated", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(),
                        "Division was unable to be Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidateInput(state: UpdateDivisionState): Boolean {
        return if (state.name.isNotBlank() && state.year.isNotBlank() && state.year.matches(
                RegexPatterns.OnlyNumberRegex
            )
        ) {
            state.validName && state.validYear
        } else {
            false
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}