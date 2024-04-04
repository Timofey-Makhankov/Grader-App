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
import ch.timofey.grader.utils.AppLanguage
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.utils.getApplicationLanguage
import ch.timofey.grader.utils.setApplicationLanguage
import ch.timofey.grader.validation.Validate
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
            val languageSetting = getApplicationLanguage()
            val result = AppLanguage.getFromTag(languageSetting)
            Log.d("SettingsViewModel", languageSetting)
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    appTheme = it.theme,
                    calculatePointsState = it.calculatePoints,
                    doublePointsState = it.doublePoints,
                    enableSwipeToDelete = it.enableSwipeToDelete,
                    minimumGrade = it.minimumGrade.toString(),
                    dateFormat = it.dateFormatter,
                    showNavigationIcons = it.showNavigationIcons,
                    language = result!!
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnThemeChange -> {
                _uiState.value = _uiState.value.copy(appTheme = event.theme)
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            theme = _uiState.value.appTheme
                        )
                    }
                }
            }

            is SettingsEvent.OnDeleteDatabaseButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    database.clearAllTables()
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Data has been cleared from the Application Database",
                            true
                        )
                    )
                }
            }

            is SettingsEvent.OnCalculatePointsChange -> {
                _uiState.value = _uiState.value.copy(calculatePointsState = event.value)
                if (!event.value) {
                    _uiState.value = _uiState.value.copy(doublePointsState = false)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            calculatePoints = _uiState.value.calculatePointsState,
                            doublePoints = false
                        )
                    }
                }
            }

            is SettingsEvent.OnDoublePointsChange -> {
                _uiState.value = _uiState.value.copy(doublePointsState = event.value)
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            doublePoints = _uiState.value.doublePointsState
                        )
                    }
                }
            }

            is SettingsEvent.OnLoadBackupFile -> {
                val data = event.file.bufferedReader().use { it.readText() }
                val result = manager.readBackup(data)
                viewModelScope.launch(Dispatchers.IO) {
                    result.schools.forEach { schoolRepository.saveSchool(it) }
                    result.divisions.forEach { divisionRepository.saveDivision(it) }
                    result.modules.forEach { moduleRepository.saveModule(it) }
                    result.exams.forEach { examRepository.saveExam(it) }
                    sendUiEvent(UiEvent.ShowSnackBar("Backup File was successfully Loaded", true))
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

                sendUiEvent(
                    UiEvent.ShowSnackBar(
                        "Backup File was created in:\n${event.fileLocation}",
                        true
                    )
                )
            }

            is SettingsEvent.OnEnableSwipeToDeleteChange -> {
                _uiState.value = _uiState.value.copy(enableSwipeToDelete = event.value)
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            enableSwipeToDelete = _uiState.value.enableSwipeToDelete
                        )
                    }
                }
            }

            is SettingsEvent.OnMinimumGradeChange -> {
                if (event.grade != "") {
                    if (Validate.isDouble(event.grade)) {
                        _uiState.value = _uiState.value.copy(minimumGrade = event.grade)
                        viewModelScope.launch(Dispatchers.IO) {
                            dataStore.updateData {
                                it.copy(
                                    minimumGrade = _uiState.value.minimumGrade.toDouble(),
                                )
                            }
                        }
                        _uiState.value = _uiState.value.copy(
                            minimumGrade = event.grade,
                            validMinimumGrade = true,
                            errorMessageMinimumGrade = ""
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            minimumGrade = event.grade,
                            validMinimumGrade = false,
                            errorMessageMinimumGrade = "Grade is not a Number"
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        minimumGrade = event.grade,
                        validMinimumGrade = false,
                        errorMessageMinimumGrade = "Enter a Valid Grade"
                    )
                }
            }

            is SettingsEvent.OnLanguageChange -> {
                _uiState.value = _uiState.value.copy(language = event.language)
                setApplicationLanguage(event.language.tag)
            }

            is SettingsEvent.OnShowNavigationIconsChange -> {
                _uiState.value = _uiState.value.copy(showNavigationIcons = event.value)
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            showNavigationIcons = _uiState.value.showNavigationIcons
                        )
                    }
                }
            }

            is SettingsEvent.OnDateFormatChange -> {
                _uiState.value = _uiState.value.copy(dateFormat = event.format)
                viewModelScope.launch(Dispatchers.IO) {
                    dataStore.updateData {
                        it.copy(
                            dateFormatter = _uiState.value.dateFormat
                        )
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
}