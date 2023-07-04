package ch.timofey.grader.ui.screen.create_school

import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
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
                val newSchool = School(
                    id = UUID.randomUUID(),
                    name = _uiState.value.name,
                    description = _uiState.value.description,
                    address = _uiState.value.address,
                    zipCode = _uiState.value.zip,
                    city = _uiState.value.city
                )
                println(newSchool)
                viewModelScope.launch {
                    repository.saveSchool(newSchool)
                }
                sendUiEvent(UiEvent.PopBackStack)
                Toast.makeText(GraderApp.getContext(), "School Created", Toast.LENGTH_SHORT).show()
            }

            is CreateSchoolEvent.OnNameChange -> {
                if (event.name.isNotBlank()) {
                    if (event.name.length > 30) {
                        sendUiEvent(UiEvent.ShowSnackBar("The Name has to be not over 30 characters long", withDismissAction = true))
                    } else {
                        _uiState.value = _uiState.value.copy(name = event.name, validName = true)
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        name = "",
                        validName = false,
                        nameErrorMessage = "Please Enter a Name"
                    )
                }
            }

            is CreateSchoolEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(description = event.description)
            }

            is CreateSchoolEvent.OnAddressChange -> {
                _uiState.value = _uiState.value.copy(address = event.address)
            }

            is CreateSchoolEvent.OnZipChange -> {
                _uiState.value = _uiState.value.copy(zip = event.zip)
            }

            is CreateSchoolEvent.OnCityChange -> {
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