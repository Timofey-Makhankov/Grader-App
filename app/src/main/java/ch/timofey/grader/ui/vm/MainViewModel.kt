package ch.timofey.grader.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.event.MainEvent
import ch.timofey.grader.ui.event.UiEvent
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.ui.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllSchools().collect{
                _uiState.value = MainState(it)
            }
        }
    }
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnCreateSchool -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateSchoolScreen.route))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}