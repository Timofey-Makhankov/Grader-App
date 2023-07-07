package ch.timofey.grader.ui.screen.create_exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.components.icons.CalendarToday
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExamScreen(
    state: CreateExamState,
    onEvent: (CreateExamEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
    isDialogOpen: Boolean = false
) {
    val openDialog = remember { mutableStateOf(isDialogOpen) }
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        topBar = {
            AppBar(
                onNavigationIconClick = { onEvent(CreateExamEvent.OnBackButtonPress) },
                icon = Icons.Default.ArrowBack,
                contentDescription = "Go back to previous Screen"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Create Exam", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.name,
                onValueChange = { value -> onEvent(CreateExamEvent.OnNameChange(value)) },
                maxLines = 2,
                label = { Text(text = "Exam Name") }
            )
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.date,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Exam taken") },
                placeholder = { Text(text = "YYYY-MM-DD")},
                trailingIcon = {
                    IconButton(
                        onClick = { openDialog.value = true }
                    ) {
                        Icon(imageVector = Icons.CalendarToday, contentDescription = "")
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.grade,
                onValueChange = { value -> onEvent(CreateExamEvent.OnGradeChange(value)) },
                singleLine = true,
                label = { Text(text = "Exam Grade") }
            )
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.weight,
                onValueChange = { value -> onEvent(CreateExamEvent.OnWeightChange(value)) },
                singleLine = true,
                label = { Text(text = "Exam Weight") }
            )
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.description,
                onValueChange = { value -> onEvent(CreateExamEvent.OnDescriptionChange(value)) },
                singleLine = true,
                maxLines = 4,
                label = { Text(text = "Exam Description") }
            )
            Button(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(CreateExamEvent.OnCreateExamButtonPress) }
            ) {
                Text(text = "Create")
            }
            if (openDialog.value) {
                DatePickerDialog(
                    onDismissRequest = { openDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = { openDialog.value = false; onEvent(CreateExamEvent.OnSetDate(datePickerState.selectedDateMillis!!))}) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDialog.value = false }) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateExamScreenPreview() {
    GraderTheme {
        CreateExamScreen(
            state = CreateExamState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {}
        )
    }
}