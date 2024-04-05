package ch.timofey.grader.ui.screen.exam.update_exam

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import ch.timofey.grader.ui.components.atom.icons.CalendarToday
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.type.SnackBarMessage
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateExamScreen(
    state: UpdateExamState,
    onEvent: (UpdateExamEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: (SnackbarVisuals?) -> Unit,
    isDialogOpen: Boolean = false
) {
    val openDialog = remember { mutableStateOf(isDialogOpen) }
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack(SnackBarMessage("Exam was updated", withDismissAction = true))
                }

                else -> Unit
            }
        }
    }
    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(UpdateExamEvent.OnBackButtonPress) },
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            actionContentDescription = "Go back to previous Screen"
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Update Exam", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.name,
                onValueChange = { value -> onEvent(UpdateExamEvent.OnNameChange(value)) },
                minLines = 2,
                isError = !state.validName,
                label = {
                    Text(text = buildAnnotatedString {
                        append("Exam name ")
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic, fontSize = 8.sp
                            )
                        ) { append("(Required)") }
                    })
                },
                supportingText = {
                    if (!state.validName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth()
                .clickable { openDialog.value = true },
                value = state.dateTaken,
                onValueChange = {},
                readOnly = true,
                isError = !state.validDate,
                label = {
                    Text(text = buildAnnotatedString {
                        append("Exam date ")
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)) {
                            append("(Required)")
                        }
                    })
                },
                supportingText = {
                    if (!state.validDate) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageDate,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { openDialog.value = true }) {
                        Icon(imageVector = Icons.CalendarToday, contentDescription = "")
                    }
                })
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = state.grade,
                onValueChange = { value -> onEvent(UpdateExamEvent.OnGradeChange(value)) },
                singleLine = true,
                isError = !state.validGrade,
                label = {
                    Text(text = buildAnnotatedString {
                        append("Grade ")
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)) {
                            append("(Required)")
                        }
                    })
                },
                supportingText = {
                    if (!state.validGrade) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageGrade,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = state.weight,
                onValueChange = { value -> onEvent(UpdateExamEvent.OnWeightChange(value)) },
                singleLine = true,
                isError = !state.validWeight,
                label = {
                    Text(buildAnnotatedString {
                        append("Weight ")
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)) {
                            append("(Required)")
                        }
                    })
                },
                supportingText = {
                    if (!state.validWeight) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageWeight,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.description,
                onValueChange = { value -> onEvent(UpdateExamEvent.OnDescriptionChange(value)) },
                minLines = 6,
                label = { Text(text = "Description") })
            Button(modifier = Modifier.padding(
                top = MaterialTheme.spacing.medium
            ),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(UpdateExamEvent.OnUpdateExamButtonPress) }) {
                Text(text = "Create")
            }
            if (openDialog.value) {
                DatePickerDialog(properties = DialogProperties(
                    dismissOnBackPress = true, dismissOnClickOutside = true
                ), onDismissRequest = { openDialog.value = false }, confirmButton = {
                    TextButton(onClick = {
                        openDialog.value = false
                        onEvent(
                            UpdateExamEvent.OnSetDate(
                                datePickerState.selectedDateMillis!!
                            )
                        )
                    }) {
                        Text(text = "OK")
                    }
                }, dismissButton = {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "Cancel")
                    }
                }) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateExamScreenPreview() {
    GraderTheme {
        UpdateExamScreen(state = UpdateExamState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {})
    }
}