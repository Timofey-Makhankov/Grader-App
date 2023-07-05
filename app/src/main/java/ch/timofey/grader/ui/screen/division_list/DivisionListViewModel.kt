package ch.timofey.grader.ui.screen.division_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DivisionListViewModel @Inject constructor(
    private val repository: DivisionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: String = savedStateHandle.get<String>("id").orEmpty()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(DivisionListState())
    val uiState: StateFlow<DivisionListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllDivisionsFromSchoolId(UUID.fromString(id)).collect {
                _uiState.value = _uiState.value.copy(divisionList = it)
            }
        }
    }

    fun onEvent(event: DivisionListEvent) {
        when (event) {
            is DivisionListEvent.OnReturnBack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is DivisionListEvent.OnCreateDivision -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateDivisionScreen.withArgs(id)))
            }
            is DivisionListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedDivision(event.id, event.value)
                }
            }
            is DivisionListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    repository.deleteDivision(event.division)
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