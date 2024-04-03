package ch.timofey.grader.ui.screen.school.create_school

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.db.domain.school.SchoolValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
                val result = SchoolValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    nameErrorMessage = result.message
                )
            }

            is CreateSchoolEvent.OnDescriptionChange -> {
                val result = SchoolValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    descriptionErrorMessage = result.message
                )
            }

            is CreateSchoolEvent.OnAddressChange -> {
                val result = SchoolValidation.address(event.address)
                _uiState.value = _uiState.value.copy(
                    address = event.address,
                    validAddress = result.valid,
                    addressErrorMessage = result.message
                )
            }

            is CreateSchoolEvent.OnZipChange -> {
                val result = SchoolValidation.zip(event.zip)
                _uiState.value = _uiState.value.copy(
                    zip = event.zip,
                    validZip = result.valid,
                    zipErrorMessage = result.message
                )
            }

            is CreateSchoolEvent.OnCityChange -> {
                val result = SchoolValidation.city(event.city)
                _uiState.value = _uiState.value.copy(
                    city = event.city,
                    validCity = result.valid,
                    cityErrorMessage = result.message
                )
            }

            is CreateSchoolEvent.OnCreateSchool -> {
                if (SchoolValidation.validateAll(_uiState.value)) {
                    val newSchool = School(
                        id = UUID.randomUUID(),
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        address = _uiState.value.address,
                        zipCode = _uiState.value.zip,
                        city = _uiState.value.city
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveSchool(newSchool)
                        //sendUiEvent(UiEvent.ShowSnackBar("Shool Created", true))
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                //Toast.makeText(GraderApp.getContext(), "School Created", Toast.LENGTH_SHORT)
                    //    .show()
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar("School was unable to be created", true))
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