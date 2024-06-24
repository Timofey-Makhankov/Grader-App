package ch.timofey.grader.ui.screen.school.create_school

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.R
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.db.domain.school.SchoolValidation
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
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveSchool(School(
                            id = UUID.randomUUID(),
                            name = _uiState.value.name,
                            description = _uiState.value.description,
                            address = _uiState.value.address,
                            zipCode = _uiState.value.zip,
                            city = _uiState.value.city
                        ))
                    }
                    sendUiEvent(UiEvent.PopBackStackAndShowSnackBar(SnackBarMessage(R.string.school_with_name.toString() + " \"${_uiState.value.name}\" "+R.string.has_been_created.toString(), withDismissAction = true)))
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar(R.string.school_was_unable_to_be_created.toString(), true))
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