package ch.timofey.grader.ui.screen.school_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(SchoolListState())
    val uiState: StateFlow<SchoolListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllSchools().collect {
                _uiState.value = _uiState.value.copy(schoolList = it)
            }
        }
    }

    fun onEvent(event: SchoolListEvent) {
        when (event) {
            is SchoolListEvent.OnCreateSchool -> {
                sendUiEvent(UiEvent.Navigate(Screen.CreateSchoolScreen.route))
            }

            is SchoolListEvent.OnCheckChange -> {
                viewModelScope.launch {
                    repository.updateIsSelectedSchool(id = event.id, value = event.value)
                }
            }

            is SchoolListEvent.OnSwipeDelete -> {
                viewModelScope.launch {
                    repository.deleteSchool(event.school)
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