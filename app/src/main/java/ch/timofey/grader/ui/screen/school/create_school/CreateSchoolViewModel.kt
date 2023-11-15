package ch.timofey.grader.ui.screen.school.create_school

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.validation.ValidateSchool
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
            is CreateSchoolEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is CreateSchoolEvent.OnNameChange -> {
                val result = ValidateSchool.Name(event.name).validate()
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.isValid,
                    nameErrorMessage = result.errorMessage
                )
            }

            is CreateSchoolEvent.OnDescriptionChange -> {
                val result = ValidateSchool.Description(event.description).validate()
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.isValid,
                    descriptionErrorMessage = result.errorMessage
                )
            }

            is CreateSchoolEvent.OnAddressChange -> {
                val result = ValidateSchool.Address(event.address).validate()
                _uiState.value = _uiState.value.copy(
                    address = event.address,
                    validAddress = result.isValid,
                    addressErrorMessage = result.errorMessage
                )
            }

            is CreateSchoolEvent.OnZipChange -> {
                val result = ValidateSchool.Zip(event.zip).validate()
                _uiState.value = _uiState.value.copy(
                    zip = event.zip,
                    validZip = result.isValid,
                    zipErrorMessage = result.errorMessage
                )
            }

            is CreateSchoolEvent.OnCityChange -> {
                val result = ValidateSchool.City(event.city).validate()
                _uiState.value = _uiState.value.copy(
                    city = event.city,
                    validCity = result.isValid,
                    cityErrorMessage = result.errorMessage
                )
            }

            is CreateSchoolEvent.OnCreateSchool -> {
                if (ValidateSchool.validateAll(_uiState.value)) {
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
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}