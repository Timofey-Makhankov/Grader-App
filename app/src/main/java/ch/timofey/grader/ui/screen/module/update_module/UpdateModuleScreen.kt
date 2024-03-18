package ch.timofey.grader.ui.screen.module.update_module

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Snackbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.atom.InputField
import ch.timofey.grader.ui.components.atom.InputFieldLabel
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.SnackBarMessage
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@Composable
fun UpdateModuleScreen(
    state: UpdateModuleState,
    onEvent: (UpdateModuleEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: (SnackbarVisuals?) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack(SnackBarMessage("Module was updated", withDismissAction = true))
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = event.message,
                            withDismissAction = event.withDismissAction,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(UpdateModuleEvent.OnBackButtonClick) },
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            actionContentDescription = "Go back to Module Screen"
        )
    }, //snackbarHost = { SnackbarHost(snackBarHostState) {
       // Snackbar(snackbarData = it) } }
        ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Update Module", style = MaterialTheme.typography.titleLarge
            )
            InputField(modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.name,
                onValueChange = { name ->
                    onEvent(UpdateModuleEvent.OnNameChange(name))
                },
                label = { InputFieldLabel(message = "Module Name", required = true) },
                isError = !state.validName,
                supportingText = {
                    if (!state.validName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })

            InputField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.teacherFirstName,
                onValueChange = { teacherFirstname ->
                    onEvent(UpdateModuleEvent.OnTeacherFirstNameChange(teacherFirstname))
                },
                label = { InputFieldLabel(message = "Teacher Surname", required = true) },
                isError = !state.validTeacherFirstName,
                supportingText = {
                    if (!state.validTeacherFirstName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageTeacherFirstName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })

            InputField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.teacherLastName,
                onValueChange = { teacherLastName ->
                    onEvent(UpdateModuleEvent.OnTeacherLastNameChange(teacherLastName))
                },
                label = { InputFieldLabel(message = "Teacher Lastname", required = true) },
                isError = !state.validTeacherLastName,
                supportingText = {
                    if (!state.validTeacherLastName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageTeacherLastName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })

            InputField(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.description,
                onValueChange = { description ->
                    onEvent(UpdateModuleEvent.OnNameChange(description))
                },
                label = { InputFieldLabel(message = "Description") },
                isError = !state.validDescription,
                supportingText = {
                    if (!state.validDescription) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageDescription,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                maxLines = 6
            )
            Button(modifier = Modifier.padding(
                top = MaterialTheme.spacing.medium
            ),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(UpdateModuleEvent.OnUpdateModuleButtonClick) }

            ) {
                Text(text = "Update")
            }
        }
    }
}

@Preview
@Composable
private fun UpdateModuleScreenPreview() {
    GraderTheme {
        UpdateModuleScreen(
            state = UpdateModuleState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UpdateModuleScreenDarkModePreview() {
    GraderTheme {
        UpdateModuleScreen(
            state = UpdateModuleState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}