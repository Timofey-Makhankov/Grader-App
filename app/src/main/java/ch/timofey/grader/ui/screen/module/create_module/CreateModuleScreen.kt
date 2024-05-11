package ch.timofey.grader.ui.screen.module.create_module

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ch.timofey.grader.R
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CreateModuleScreen(
    state: CreateModuleState,
    onEvent: (CreateModuleEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: (SnackbarVisuals?) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack(null)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = event.withDismissAction,
                        duration = SnackbarDuration.Long
                    )
                }

                is UiEvent.PopBackStackAndShowSnackBar -> {
                    onPopBackStack(event.snackbarVisuals)
                }

                else -> Unit
            }
        }
    }
    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(CreateModuleEvent.OnBackButtonClick) },
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            actionContentDescription = R.string.go_back_to_previous_screen.toString()
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = R.string.create_module.toString(), style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.name,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnNameChange(value)) },
                minLines = 2,
                isError = !state.validName,
                label = { Text(text = buildAnnotatedString {
                    append(R.string.module_name.toString())
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)){
                        append(R.string.required.toString())
                    }
                }) },
                supportingText = {
                    if (!state.validName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.teacherFirstName,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnTeacherFirstnameChange(value)) },
                minLines = 2,
                isError = !state.validTeacherFirstname,
                label = { Text(text = buildAnnotatedString {
                    append(R.string.teacher_firstname.toString())
                }) },
                supportingText = {
                    if (!state.validTeacherFirstname) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageTeacherFirstname,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            OutlinedTextField(modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.large)
                .fillMaxWidth(),
                value = state.teacherLastName,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnTeacherLastnameChange(value)) },
                minLines = 2,
                isError = !state.validTeacherLastname,
                label = { Text(text = buildAnnotatedString {
                    append(R.string.teacher_lastname.toString())
                }) },
                supportingText = {
                    if (!state.validTeacherLastname) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageTeacherLastname,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                })
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.description,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnDescriptionChange(value)) },
                label = { Text(text = R.string.module_description.toString()) },
                minLines = 6
            )
            Button(modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(CreateModuleEvent.OnCreateModuleButtonClick) }) {
                Text(text = R.string.create.toString())
            }
        }
    }
}

@Preview
@Composable
private fun CreateModuleScreenPreview() {
    GraderTheme {
        CreateModuleScreen(state = CreateModuleState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateModuleScreenDarkModePreview() {
    GraderTheme {
        CreateModuleScreen(state = CreateModuleState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}