package ch.timofey.grader.ui.screen.division.update_division

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.db.domain.division.DivisionValidation
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
                val result = DivisionValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    errorMessageName = result.message
                )
            }

            is UpdateDivisionEvent.OnDescriptionChange -> {
                val result = DivisionValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is UpdateDivisionEvent.OnYearChange -> {
                val result = DivisionValidation.year(event.year)
                _uiState.value = _uiState.value.copy(
                    year = event.year,
                    validYear = result.valid,
                    errorMessageYear = result.message
                )
            }

            is UpdateDivisionEvent.OnUpdateDivision -> {
                if (DivisionValidation.validateAll(_uiState.value)) {
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
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}