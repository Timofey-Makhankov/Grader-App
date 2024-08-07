package ch.timofey.grader.ui.screen.school.update_school

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.R
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
class UpdateSchoolViewModel @Inject constructor(
    private val repository: SchoolRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val schoolId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(UpdateSchoolState())
    val uiState: StateFlow<UpdateSchoolState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
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
                            message = R.string.school_was_unable_to_be_found.toString(),
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
                val result = SchoolValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    nameErrorMessage = result.message
                )
            }

            is UpdateSchoolEvent.OnDescriptionChange -> {
                val result = SchoolValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    descriptionErrorMessage = result.message
                )
            }

            is UpdateSchoolEvent.OnAddressChange -> {
                val result = SchoolValidation.address(event.address)
                _uiState.value = _uiState.value.copy(
                    address = event.address,
                    validAddress = result.valid,
                    addressErrorMessage = result.message
                )
            }

            is UpdateSchoolEvent.OnCityChange -> {
                val result = SchoolValidation.city(event.city)
                _uiState.value = _uiState.value.copy(
                    city = event.city,
                    validCity = result.valid,
                    cityErrorMessage = result.message
                )
            }

            is UpdateSchoolEvent.OnZipChange -> {
                val result = SchoolValidation.zip(event.zip)
                _uiState.value = _uiState.value.copy(
                    zip = event.zip,
                    validZip = result.valid,
                    zipErrorMessage = result.message
                )
            }

            is UpdateSchoolEvent.OnUpdateSchool -> {
                if (SchoolValidation.validateAll(_uiState.value)) {
                    val updatedSchool = _uiState.value.currentSchool!!.copy(
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        address = _uiState.value.address,
                        zipCode = _uiState.value.zip,
                        city = _uiState.value.city
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.updateSchool(updatedSchool)
                    }
                    sendUiEvent(UiEvent.PopBackStackAndShowSnackBar(SnackBarMessage(R.string.school_with_name.toString()+" \"${_uiState.value.name}\" "+R.string.has_been_updated.toString())))
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar(R.string.school_was_unable_to_be_updated.toString(),true))
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
    private fun sendUiEvents(vararg events: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            for (event in events) {
                _uiEvent.send(event)
            }
        }
    }
}