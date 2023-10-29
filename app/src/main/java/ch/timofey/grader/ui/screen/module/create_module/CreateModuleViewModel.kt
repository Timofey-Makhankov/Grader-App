package ch.timofey.grader.ui.screen.module.module_list.create_module

import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.module.ModuleRepository
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
class CreateModuleViewModel @Inject constructor(
    private val repository: ModuleRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val divisionId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(CreateModuleState())
    val uiState: StateFlow<CreateModuleState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateModuleEvent) {
        when (event) {
            is CreateModuleEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is CreateModuleEvent.OnNameChange -> {
                if (event.name.isNotBlank()) {
                    if (event.name.length > 60) {
                        _uiState.value = _uiState.value.copy(
                            validName = false,
                            errorMessageName = "The name cannot exceed 60 characters long"
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            name = event.name, validName = true
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        name = "",
                        validName = false,
                        errorMessageName = "Please enter a valid Module name"
                    )
                }
            }

            is CreateModuleEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(
                    description = event.description
                )
            }

            is CreateModuleEvent.OnTeacherFirstnameChange -> {
                if (event.teacherFirstname.isNotBlank()) {
                    if (event.teacherFirstname.length > 60) {
                        _uiState.value = _uiState.value.copy(
                            validTeacherFirstname = false,
                            errorMessageTeacherFirstname = "The teacher firstname cannot exceed 60 characters long"
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            teacherFirstname = event.teacherFirstname, validTeacherFirstname = true
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        teacherFirstname = "",
                        validTeacherFirstname = false,
                        errorMessageTeacherFirstname = "Please enter a valid teacher firstname"
                    )
                }
            }

            is CreateModuleEvent.OnTeacherLastnameChange -> {
                if (event.teacherLastname.isNotBlank()) {
                    if (event.teacherLastname.length > 60) {
                        _uiState.value = _uiState.value.copy(
                            validTeacherLastname = false,
                            errorMessageTeacherLastname = "The teacher lastname cannot exceed 60 characters long"
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            teacherLastname = event.teacherLastname, validTeacherLastname = true
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        teacherLastname = "",
                        validTeacherLastname = false,
                        errorMessageTeacherLastname = "Please enter a valid teacher lastname"
                    )
                }
            }

            is CreateModuleEvent.OnCreateModuleButtonClick -> {
                if (isValidInput(_uiState.value)) {
                    viewModelScope.launch {
                        repository.saveModule(
                            Module(
                                id = UUID.randomUUID(),
                                name = _uiState.value.name,
                                description = _uiState.value.description,
                                teacherFirstname = _uiState.value.teacherFirstname,
                                teacherLastname = _uiState.value.teacherLastname,
                                divisionId = UUID.fromString(divisionId)
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStack)
                    Toast.makeText(GraderApp.getContext(), "Module Created", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        GraderApp.getContext(),
                        "Module was unable to be created",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun isValidInput(state: CreateModuleState): Boolean {
        return if (state.name.isNotBlank() && state.teacherFirstname.isNotBlank() && state.teacherLastname.isNotBlank()) {
            state.validName && state.validTeacherLastname && state.validTeacherFirstname
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