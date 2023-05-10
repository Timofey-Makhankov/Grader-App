package ch.timofey.grader.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.school.School
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.event.CreateSchoolEvent
import ch.timofey.grader.ui.event.UiEvent
import ch.timofey.grader.ui.state.CreateSchoolState
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
                val newSchool = School(
                    id = UUID.randomUUID(),
                    name = _uiState.value.name,
                    description = _uiState.value.description,
                    address = _uiState.value.address,
                    zipCode = _uiState.value.zip,
                    city = _uiState.value.city
                )
                println(newSchool)
                /*viewModelScope.launch {
                    repository.saveSchool(newSchool)
                }*/
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CreateSchoolEvent.OnNameChange -> {
                //_uiState.value.name = event.name
                _uiState.value =_uiState.value.copy(name = event.name)
                println("After Copy: ${_uiState.value.name}")
            }
            is CreateSchoolEvent.OnDescriptionChange -> {
                //_uiState.value.description = event.description
                _uiState.value = _uiState.value.copy(description = event.description)
            }
            is CreateSchoolEvent.OnAddressChange -> {
                //_uiState.value.address = event.address
                _uiState.value = _uiState.value.copy(address = event.address)
            }
            is CreateSchoolEvent.OnZipChange -> {
                //_uiState.value.zip = event.zip
                _uiState.value = _uiState.value.copy(zip = event.zip)
            }
            is CreateSchoolEvent.OnCityChange -> {
                //_uiState.value.city = event.city
                _uiState.value = _uiState.value.copy(city = event.city)
            }
            is CreateSchoolEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}