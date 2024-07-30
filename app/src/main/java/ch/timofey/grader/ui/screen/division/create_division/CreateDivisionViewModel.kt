package ch.timofey.grader.ui.screen.division.create_division

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.db.domain.division.DivisionValidation
import ch.timofey.grader.type.SnackBarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import ch.timofey.grader.R

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
            is CreateDivisionEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is CreateDivisionEvent.OnNameChange -> {
                val result = DivisionValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    errorMessageName = result.message
                )
            }

            is CreateDivisionEvent.OnDescriptionChange -> {
                val result = DivisionValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is CreateDivisionEvent.OnYearChange -> {
                val result = DivisionValidation.year(event.year)
                _uiState.value = _uiState.value.copy(
                    year = event.year,
                    validYear = result.valid,
                    errorMessageYear = result.message
                )
            }

            is CreateDivisionEvent.OnCreateDivision -> {
                if (DivisionValidation.validateAll(_uiState.value)) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveDivision(Division(
                            id = UUID.randomUUID(),
                            name = _uiState.value.name,
                            description = _uiState.value.description,
                            schoolYear = _uiState.value.year.toInt(),
                            schoolId = UUID.fromString(id),
                            isSelected = true
                            ))
                    }
                    sendUiEvent(UiEvent.PopBackStackAndShowSnackBar(SnackBarMessage(R.string.division_with_name.toString()+ " \"${_uiState.value.name}\" " + R.string.has_been_created.toString(), withDismissAction = true)))
                } else {
                    sendUiEvent((UiEvent.ShowSnackBar(R.string.division_was_unable_to_be_created.toString(), true)))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
}