package ch.timofey.grader.ui.screen.module_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModuleListViewModel @Inject constructor(
    private val repository: ModuleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val divisionId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(ModuleListState())
    val uiState: StateFlow<ModuleListState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllModules().collect {
                _uiState.value = _uiState.value.copy(moduleList = it)
            }
        }
    }

    fun onEvent(event: ModuleListEvent){
        when (event) {
            is ModuleListEvent.OnFABClick -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateModuleScreen.withArgs(divisionId)))
            }
            is ModuleListEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ModuleListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedModule(event.id, event.value)
                }
            }
            is ModuleListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    repository.deleteModule(event.module)
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