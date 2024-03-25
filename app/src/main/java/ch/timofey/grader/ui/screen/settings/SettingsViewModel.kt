package ch.timofey.grader.ui.screen.settings

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.AppDatabase
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.backup.BackupData
import ch.timofey.grader.db.backup.BackupManager
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val database: AppDatabase,
    private val schoolRepository: SchoolRepository,
    private val divisionRepository: DivisionRepository,
    private val moduleRepository: ModuleRepository,
    private val examRepository: ExamRepository,
    application: Application
) : AndroidViewModel(application) {
    private val manager: BackupManager = BackupManager()

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    appTheme = it.theme,
                    calculatePointsState = it.calculatePoints,
                    doublePointsState = it.doublePoints,
                    enableSwipeToDelete = it.enableSwipeToDelete
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnThemeChange -> {
                _uiState.value = _uiState.value.copy(appTheme = event.theme)
                viewModelScope.launch(Dispatchers.IO) {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnDeleteDatabaseButtonClick -> {
                //Log.d("OnDeleteDatabaseButtonClick", "Clearing Database")
                viewModelScope.launch (Dispatchers.IO){
                    database.clearAllTables()
                }
            }

            is SettingsEvent.OnCalculatePointsChange -> {
                _uiState.value = _uiState.value.copy(calculatePointsState = event.value)
                if (!event.value) {
                    _uiState.value = _uiState.value.copy(doublePointsState = false)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnDoublePointsChange -> {
                _uiState.value = _uiState.value.copy(doublePointsState = event.value)
                viewModelScope.launch(Dispatchers.IO) {
                    updateDataStore()
                }
            }

            is SettingsEvent.OnLoadBackupFile -> {
                val data = event.file.bufferedReader().use { it.readText() }
                val result = manager.readBackup(data)
                Log.d("SettingsViewModel-OnLoadBackupFile", result.toString())
                viewModelScope.launch (Dispatchers.IO){
                    result.schools.forEach { schoolRepository.saveSchool(it) }
                    result.divisions.forEach { divisionRepository.saveDivision(it) }
                    result.modules.forEach { moduleRepository.saveModule(it) }
                    result.exams.forEach { examRepository.saveExam(it) }
                }
            }

            is SettingsEvent.OnCreateBackupFile -> {
                val schools = schoolRepository.getAllSchools()
                val divisions = divisionRepository.getAllDivisions()
                val modules = moduleRepository.getAllModules()
                val exams = examRepository.getAllExams()

                val backup = BackupData(schools, divisions, modules, exams)
                val result = manager.createBackup(backup)
                event.file.bufferedWriter().use { it.write(result) }
            }

            is SettingsEvent.OnEnableSwipeToDeleteChange -> {
                _uiState.value = _uiState.value.copy(enableSwipeToDelete = event.value)
                viewModelScope.launch (Dispatchers.IO){
                    updateDataStore()
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }

    private suspend fun updateDataStore() {
        dataStore.updateData {
            it.copy(
                theme = _uiState.value.appTheme,
                calculatePoints = _uiState.value.calculatePointsState,
                doublePoints = _uiState.value.doublePointsState,
                enableSwipeToDelete = _uiState.value.enableSwipeToDelete
            )
        }
    }
}