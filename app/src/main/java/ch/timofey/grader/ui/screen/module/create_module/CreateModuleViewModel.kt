package ch.timofey.grader.ui.screen.module.create_module

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.module.ModuleValidation
import ch.timofey.grader.type.SnackBarMessage
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
import ch.timofey.grader.R

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
                val result = ModuleValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name,
                    validName = result.valid,
                    errorMessageName = result.message
                )
            }

            is CreateModuleEvent.OnDescriptionChange -> {
                val result = ModuleValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is CreateModuleEvent.OnTeacherFirstnameChange -> {
                val result = ModuleValidation.teacherFirstName(event.teacherFirstname)
                _uiState.value = _uiState.value.copy(
                    teacherFirstName = event.teacherFirstname,
                    validTeacherFirstname = result.valid,
                    errorMessageTeacherFirstname = result.message
                )
            }

            is CreateModuleEvent.OnTeacherLastnameChange -> {
                val result = ModuleValidation.teacherLastName(event.teacherLastname)
                _uiState.value = _uiState.value.copy(
                    teacherLastName = event.teacherLastname,
                    validTeacherFirstname = result.valid,
                    errorMessageTeacherFirstname = result.message
                )
            }

            is CreateModuleEvent.OnCreateModuleButtonClick -> {
                if (ModuleValidation.validateAll(_uiState.value)) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveModule(
                            Module(
                                id = UUID.randomUUID(),
                                name = _uiState.value.name,
                                description = _uiState.value.description,
                                teacherFirstname = _uiState.value.teacherFirstName,
                                teacherLastname = _uiState.value.teacherLastName,
                                divisionId = UUID.fromString(divisionId),
                                isSelected = false
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStackAndShowSnackBar(SnackBarMessage(R.string.module_with_name.toString() + " \"${_uiState.value.name}\" "+R.string.has_been_created.toString(), withDismissAction = true)))
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar(R.string.module_was_unable_to_be_created.toString(), true))
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