package ch.timofey.grader.ui.screen.exam.create_exam

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.db.AppSettings
import ch.timofey.grader.db.domain.exam.Exam
import ch.timofey.grader.db.domain.exam.ExamRepository
import ch.timofey.grader.db.domain.exam.ExamValidation
import ch.timofey.grader.type.SnackBarMessage
import ch.timofey.grader.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExamViewModel @Inject constructor(
    private val repository: ExamRepository,
    private val dataStore: DataStore<AppSettings>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val moduleId = savedStateHandle.get<String>("id").orEmpty()

    private val _uiState = MutableStateFlow(CreateExamState())
    val uiState: StateFlow<CreateExamState> = _uiState

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.collectLatest {
                _uiState.value = _uiState.value.copy(
                    dateFormatting = it.dateFormatter
                )
            }
        }
    }

    fun onEvent(event: CreateExamEvent) {
        when (event) {
            is CreateExamEvent.OnBackButtonPress -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is CreateExamEvent.OnNameChange -> {
                val result = ExamValidation.name(event.name)
                _uiState.value = _uiState.value.copy(
                    name = event.name, validName = result.valid, errorMessageName = result.message
                )
            }

            is CreateExamEvent.OnDescriptionChange -> {
                val result = ExamValidation.description(event.description)
                _uiState.value = _uiState.value.copy(
                    description = event.description,
                    validDescription = result.valid,
                    errorMessageDescription = result.message
                )
            }

            is CreateExamEvent.OnGradeChange -> {
                val result = ExamValidation.grade(event.grade, true)
                _uiState.value = _uiState.value.copy(
                    grade = event.grade,
                    validGrade = result.valid,
                    errorMessageGrade = result.message
                )
            }

            is CreateExamEvent.OnWeightChange -> {
                val result = ExamValidation.weight(event.weight)
                _uiState.value = _uiState.value.copy(
                    weight = event.weight,
                    validWeight = result.valid,
                    errorMessageWeight = result.message
                )
            }

            is CreateExamEvent.OnSetDate -> {
                _uiState.value = _uiState.value.copy(
                    dateTaken = Instant.ofEpochMilli(event.date).atZone(ZoneId.systemDefault())
                        .format(
                            DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                .withLocale(AppCompatDelegate.getApplicationLocales().get(0)!!)
                        )
                )
            }

            is CreateExamEvent.OnCreateExamButtonPress -> {
                if (ExamValidation.validateAll(_uiState.value)) {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveExam(
                            Exam(
                                id = UUID.randomUUID(),
                                name = _uiState.value.name,
                                description = _uiState.value.description,
                                grade = _uiState.value.grade.toDouble(),
                                weight = _uiState.value.weight.toDouble(),
                                date = LocalDate.parse(
                                    _uiState.value.dateTaken,
                                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
                                        AppCompatDelegate.getApplicationLocales().get(0)!!
                                    )
                                ),
                                isSelected = true,
                                moduleId = UUID.fromString(moduleId)
                            )
                        )
                    }
                    sendUiEvent(UiEvent.PopBackStackAndShowSnackBar(SnackBarMessage("Exam with name: \"${_uiState.value.name}\" has been created", withDismissAction = true)))
                } else {
                    sendUiEvent(UiEvent.ShowSnackBar("Exam was unable to be created", true))
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