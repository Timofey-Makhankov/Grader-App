package ch.timofey.grader.ui.screen.module.update_module

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.module.ModuleValidation
import ch.timofey.grader.utils.UiEvent
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
class UpdateModuleViewModel @Inject constructor(
    private val repository: ModuleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moduleId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(UpdateModuleState())
    val uiState: StateFlow<UpdateModuleState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getModuleById(UUID.fromString(moduleId))?.also { module ->
                _uiState.value = _uiState.value.copy(
                    currentModule = module,
                    name = module.name,
                    description = module.description ?: "",
                    teacherLastName = module.teacherLastname,
                    teacherFirstName = module.teacherFirstname
                )
            } ?: run {
                sendUiEvents(
                    UiEvent.PopBackStack, UiEvent.ShowSnackBar(
                        message = "Module was unable to be found", withDismissAction = true
                    )
                )
            }
        }
    }

    fun onEvent(event: UpdateModuleEvent) {
        when (event) {
            is UpdateModuleEvent.OnBackButtonClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is UpdateModuleEvent.OnNameChange -> {
                val result = ModuleValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    errorMessageName = result.message
                )
            }

            is UpdateModuleEvent.OnDescriptionChange -> {
                val result = ModuleValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is UpdateModuleEvent.OnTeacherFirstNameChange -> {
                val result = ModuleValidation.teacherFirstName(event.teacherFirstName)
                _uiState.value = _uiState.value.copy(
                    teacherFirstName = event.teacherFirstName,
                    validTeacherFirstName = result.valid,
                    errorMessageTeacherFirstName = result.message
                )
            }

            is UpdateModuleEvent.OnTeacherLastNameChange -> {
                val result = ModuleValidation.teacherLastName(event.teacherLastName)
                _uiState.value = _uiState.value.copy(
                    teacherLastName = event.teacherLastName,
                    validTeacherLastName = result.valid,
                    errorMessageTeacherLastName = result.message
                )
            }

            is UpdateModuleEvent.OnUpdateModuleButtonClick -> {
                if (ModuleValidation.validateAll(_uiState.value)) {
                    val updatedModule = _uiState.value.currentModule!!.copy(
                        name = _uiState.value.name,
                        description = _uiState.value.description,
                        teacherFirstname = _uiState.value.teacherFirstName,
                        teacherLastname = _uiState.value.teacherLastName
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.updateModule(updatedModule)
                        sendUiEvent(UiEvent.PopBackStack)
                        //sendUiEvent(UiEvent.ShowSnackBar("Module was successfully updated"))
                    }
                } else {
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            message = "Module was unable to be created", withDismissAction = true
                        )
                    )
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
        for (event in events) {
            viewModelScope.launch(Dispatchers.Main) {
                _uiEvent.send(event)
            }
        }
    }
}