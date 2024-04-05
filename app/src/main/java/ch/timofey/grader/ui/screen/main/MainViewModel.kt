package ch.timofey.grader.ui.screen.main

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.ui.screen.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStore: DataStore<AppSettings>
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState
    init {
        viewModelScope.launch(Dispatchers.Main) {
            dataStore.data.collect{ appSettings ->
                _uiState.value = _uiState.value.copy(theme = appSettings.theme)
            }
        }
    }
}