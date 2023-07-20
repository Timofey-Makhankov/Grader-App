package ch.timofey.grader.ui.screen.create_module

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
                _uiState.value = _uiState.value.copy(
                    name = event.name
                )
            }

            is CreateModuleEvent.OnDescriptionChange -> {
                _uiState.value = _uiState.value.copy(
                    description = event.description
                )
            }

            is CreateModuleEvent.OnTeacherFirstnameChange -> {
                _uiState.value = _uiState.value.copy(
                    teacherFirstname = event.teacherFirstname
                )
            }

            is CreateModuleEvent.OnTeacherLastnameChange -> {
                _uiState.value = _uiState.value.copy(
                    teacherLastname = event.teacherLastname
                )
            }

            is CreateModuleEvent.OnCreateModuleButtonClick -> {
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
                Toast.makeText(GraderApp.getContext(), "Module Created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}