package ch.timofey.grader.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.event.MainEvent
import ch.timofey.grader.event.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SchoolRepository
): ViewModel() {
    val schools = repository.getAllSchools()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MainEvent){
        when(event){
            is MainEvent.OnCreateSchool -> {
                /*TODO*/
            }
            else -> {/*TODO*/}
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}