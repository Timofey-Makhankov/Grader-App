package ch.timofey.grader.ui.screen.update_school

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
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
class UpdateSchoolViewModel @Inject constructor(
    private val repository: SchoolRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val schoolId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(UpdateSchoolState())
    val uiState: StateFlow<UpdateSchoolState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getSchoolById(UUID.fromString(schoolId)).apply {
                this?.let { school ->
                    _uiState.value = _uiState.value.copy(
                        name = school.name,
                        description = school.description ?: "",
                        address = school.address,
                        zip = school.zipCode,
                        city = school.city,
                        currentSchool = school
                    )
                } ?: run {
                    sendUiEvents(
                        UiEvent.PopBackStack,
                        UiEvent.ShowSnackBar(
                            message = "School was unable to be found",
                            withDismissAction = true
                        )
                    )
                }
            }
        }
    }

    fun onEvent(event: UpdateSchoolEvent) {
        when (event) {
            is UpdateSchoolEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is UpdateSchoolEvent.OnNameChange -> {
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

            is UpdateSchoolEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is UpdateSchoolEvent.OnAddressChange -> {
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

            is UpdateSchoolEvent.OnCityChange -> {
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

            is UpdateSchoolEvent.OnZipChange -> {
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

            is UpdateSchoolEvent.OnUpdateSchool -> {
                if (isValidateInput(_uiState.value)) {
                    val updatedSchool = _uiState.value.currentSchool!!.copy(
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        address = _uiState.value.address,
                        zipCode = _uiState.value.zip,
                        city = _uiState.value.city
                    )

                    viewModelScope.launch {
                        repository.updateSchool(updatedSchool)
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "School Updated", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(),
                        "School was unable to be Updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidateInput(state: UpdateSchoolState): Boolean {
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

    private fun sendUiEvents(vararg events: UiEvent) {
        viewModelScope.launch {
            for (event in events) {
                _uiEvent.send(event)
            }
        }
    }
}