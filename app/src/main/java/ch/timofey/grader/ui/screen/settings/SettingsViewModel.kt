package ch.timofey.grader.ui.screen.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.AppDatabase
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    init {
        viewModelScope.launch {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    appTheme = it.theme,
                    calculatePointsState = it.calculatePoints,
                    doublePointsState = it.doublePoints
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnThemeChange -> {
                _uiState.value = _uiState.value.copy(appTheme = event.theme)
                viewModelScope.launch {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnDeleteDatabaseButtonClick -> {
                viewModelScope.launch {
                    database.clearAllTables()
                }
            }

            is SettingsEvent.OnCalculatePointsChange -> {
                _uiState.value = _uiState.value.copy(calculatePointsState = event.value)
                if (!event.value) {
                    _uiState.value = _uiState.value.copy(doublePointsState = false)
                }
                viewModelScope.launch {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnDoublePointsChange -> {
                _uiState.value = _uiState.value.copy(doublePointsState = event.value)
                viewModelScope.launch {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnCreateExportClick -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private suspend fun updateDataStore() {
        dataStore.updateData {
            it.copy(
                theme = _uiState.value.appTheme,
                calculatePoints = _uiState.value.calculatePointsState,
                doublePoints = _uiState.value.doublePointsState
            )
        }
    }
}