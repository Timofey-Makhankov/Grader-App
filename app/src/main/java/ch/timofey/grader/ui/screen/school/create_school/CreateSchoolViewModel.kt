package ch.timofey.grader.ui.screen.school.create_school

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
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
class CreateSchoolViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateSchoolState())
    val uiState: StateFlow<CreateSchoolState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateSchoolEvent) {
        when (event) {
            is CreateSchoolEvent.OnCreateSchool -> {
                if (isValidateInput(_uiState.value)) {
                    val newSchool = School(
                        id = UUID.randomUUID(),
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        address = _uiState.value.address,
                        zipCode = _uiState.value.zip,
                        city = _uiState.value.city
                    )
                    viewModelScope.launch {
                        repository.saveSchool(newSchool)
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "School Created", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(),
                        "School was unable to be created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is CreateSchoolEvent.OnNameChange -> {
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
                        nameErrorMessage = "Please Enter a valid School Name"
                    )
                }
            }

            is CreateSchoolEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateSchoolEvent.OnAddressChange -> {
                if (event.address.isNotBlank()) {
                    _uiState.value =
                        _uiState.value.copy(address = event.address, validAddress = true)
                } else {
                    _uiState.value = _uiState.value.copy(
                        address = "",
                        validAddress = false,
                        addressErrorMessage = "Please Enter a valid Address"
                    )
                }
            }

            is CreateSchoolEvent.OnZipChange -> {
                if (event.zip.isNotBlank()) {
                    _uiState.value = _uiState.value.copy(zip = event.zip, validZip = true)
                } else {
                    _uiState.value = _uiState.value.copy(
                        zip = "",
                        validZip = false,
                        zipErrorMessage = "Please Enter a Valid Zip Code"
                    )
                }
            }

            is CreateSchoolEvent.OnCityChange -> {
                if (event.city.isNotBlank()) {
                    _uiState.value = _uiState.value.copy(city = event.city, validCity = true)
                } else {
                    _uiState.value = _uiState.value.copy(
                        city = "",
                        validCity = false,
                        cityErrorMessage = "Please Enter a Valid City Name"
                    )
                }
            }

            is CreateSchoolEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun isValidateInput(state: CreateSchoolState): Boolean {
        return if (state.name.isNotBlank() && state.address.isNotBlank() && state.zip.isNotBlank() && state.city.isNotBlank()) {
            state.validName && state.validAddress && state.validCity && state.validZip
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