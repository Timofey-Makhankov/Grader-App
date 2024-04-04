package ch.timofey.grader.ui.screen.module.module_list

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.division.DivisionRepository
import ch.timofey.grader.db.domain.module.Module
import ch.timofey.grader.db.domain.module.ModuleRepository
import ch.timofey.grader.db.domain.school.SchoolRepository
import ch.timofey.grader.navigation.Screen
import ch.timofey.grader.utils.UiEvent
import ch.timofey.grader.utils.getAverage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ModuleListViewModel @Inject constructor(
    private val repository: ModuleRepository,
    private val divisionRepository: DivisionRepository,
    private val schoolRepository: SchoolRepository,
    private val dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val divisionId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(ModuleListState())
    val uiState: StateFlow<ModuleListState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    showPoints = it.calculatePoints,
                    swipingEnabled = it.enableSwipeToDelete,
                    showNavigationIcons = it.showNavigationIcons
                )
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllModulesFromDivisionId(UUID.fromString(divisionId))
                .collect { moduleList ->
                    _uiState.value =
                        _uiState.value.copy(moduleList = moduleList.filter { module -> !module.onDelete })
                    if (moduleList.isNotEmpty()) {
                        val averageGrade = calculateAverageGrade(moduleList)
                        repository.updateDivisionGradeById(
                            UUID.fromString(divisionId), averageGrade
                        )
                        _uiState.value = _uiState.value.copy(averageGrade = averageGrade.toString())
                        if (_uiState.value.averageGrade.toDouble() == 0.0) {
                            _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                        } else {
                            _uiState.value = _uiState.value.copy(averageGradeIsZero = false)
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(averageGradeIsZero = true)
                    }
                }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val division = divisionRepository.getDivision(UUID.fromString(divisionId))
            division?.let {
                val school = schoolRepository.getSchoolById(it.schoolId)
                school?.let {
                    _uiState.value = _uiState.value.copy(
                        locationsTitles = listOf(school.name, division.name, "Modules")
                    )
                }
            }
        }
    }

    fun onEvent(event: ModuleListEvent) {
        when (event) {
            is ModuleListEvent.OnFABClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteModuleItems()
                }
                sendUiEvent(UiEvent.Navigate(Screen.CreateModuleScreen.withArgs(divisionId)))
            }

            is ModuleListEvent.OnReturnBack -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteModuleItems()
                }
                sendUiEvent(UiEvent.PopBackStack)
            }

            is ModuleListEvent.OnDeleteItems -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteModuleItems()
                }
                sendUiEvent(UiEvent.Navigate(route = event.route))
            }

            is ModuleListEvent.OnCheckChange -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateIsSelectedModule(event.id, event.value)
                }
            }
            is ModuleListEvent.OnDeleteButtonClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteModule(event.moduleId, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Module has been deleted", true, "Undo"
                        )
                    )
                }
            }

            is ModuleListEvent.OnSwipeDelete -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteModule(event.id, true)
                    sendUiEvent(
                        UiEvent.ShowSnackBar(
                            "Module was deleted", true, "Undo"
                        )
                    )
                }
            }

            is ModuleListEvent.OnUndoDeleteClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.updateOnDeleteModule(event.id, false)
                }
            }
        }
    }

    private fun calculateAverageGrade(list: List<Module>): Double {
        val validExams = list.map { it }.filter { it.isSelected }
        val gradeList = validExams.map { it.grade }
        return getAverage(grades = gradeList).toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)
            .toDouble()
    }

    private suspend fun deleteModuleItems() {
        val moduleList = repository.getAllModules()
        moduleList.filter { module -> module.onDelete }.forEach { module ->
            repository.deleteModuleById(module.id)
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(event)
        }
    }
}