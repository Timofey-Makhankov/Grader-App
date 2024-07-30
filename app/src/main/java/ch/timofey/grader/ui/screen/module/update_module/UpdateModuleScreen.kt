package ch.timofey.grader.ui.screen.module.update_module

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.R
import ch.timofey.grader.ui.components.atom.InputField
import ch.timofey.grader.ui.components.atom.InputFieldLabel
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.type.SnackBarMessage
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.Dispatchers
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
                    onPopBackStack(null)
                }

                is UiEvent.PopBackStackAndShowSnackBar -> {
                    onPopBackStack(event.snackbarVisuals)
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch (Dispatchers.Main){
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
            actionContentDescription = stringResource(id = R.string.go_back_to_module_screen),
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.update_module), style = MaterialTheme.typography.titleLarge
            )
            InputField(modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.name,
                onValueChange = { name ->
                    onEvent(UpdateModuleEvent.OnNameChange(name))
                },
                label = { InputFieldLabel(message = stringResource(id = R.string.module_name), required = true) },
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
                label = { InputFieldLabel(message = stringResource(id = R.string.teacher_firstname), required = false) },
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
                label = { InputFieldLabel(message = stringResource(id = R.string.teacher_lastname), required = false) },
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
                label = { InputFieldLabel(message = stringResource(id = R.string.description)) },
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
                Text(text = stringResource(id = R.string.update))
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