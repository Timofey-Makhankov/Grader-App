package ch.timofey.grader.ui.screen.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.timofey.grader.db.AppDatabase
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.Language
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<AppSettings>,
    private val database: AppDatabase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnSettingChange -> {
                //TODO
                println()
            }

            is SettingsEvent.OnDeleteDatabaseButtonClick -> {
                viewModelScope.launch {
                    database.clearAllTables()
                }
            }
        }
    }

    private suspend fun updateLanguage(language: Language) {
        dataStore.updateData {
            it.copy(
                language = language
            )
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}