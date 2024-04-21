package ch.timofey.grader.ui.screen.division.update_division

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ch.timofey.grader.ui.components.organisms.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.type.SnackBarMessage
import ch.timofey.grader.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun UpdateDivisionScreen(
    state: UpdateDivisionState,
    onEvent: (UpdateDivisionEvent) -> Unit,
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

                is UiEvent.PopBackStackAndShowSnackBar -> {
                    onPopBackStack(event.snackbarVisuals)
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = event.withDismissAction,
                        duration = SnackbarDuration.Long
                    )
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = { AppBar(
            onNavigationIconClick = { onEvent(UpdateDivisionEvent.OnBackButtonClick) },
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            actionContentDescription = "Go back to Division Screen"
        )}) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize()
        ) {
            Text(
                text = "Update Division", style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.name,
                label = {
                    Text(text = buildAnnotatedString {
                        append("Division Name ")
                        withStyle(
                            SpanStyle(
                            fontStyle = FontStyle.Italic, fontSize = 8.sp
                        )
                        ){
                            append("(Required)")
                        }
                    })
                },
                onValueChange = { name -> onEvent(UpdateDivisionEvent.OnNameChange(name)) },
                isError = !state.validName,
                supportingText = {
                    if (!state.validName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageName,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                minLines = 2
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.year,
                label = { Text(text = buildAnnotatedString {
                    append("Division Year ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)){
                        append("(Required)")
                    }
                }) },
                onValueChange = { year ->
                    onEvent(UpdateDivisionEvent.OnYearChange(year))
                },
                singleLine = true,
                isError = !state.validYear,
                supportingText = {
                    if (!state.validYear) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.errorMessageYear,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.description,
                label = { Text(text = "Division Description") },
                onValueChange = { description ->
                    onEvent(
                        UpdateDivisionEvent.OnDescriptionChange(
                            description
                        )
                    )
                },
                minLines = 6
            )
            Button(modifier = Modifier.padding(
                top = MaterialTheme.spacing.medium
            ),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(UpdateDivisionEvent.OnUpdateDivision) }

            ) {
                Text(text = "Update")
            }
        }
    }
}

@Preview
@Composable
fun UpdateDivisionScreenPreview() {
    GraderTheme {
        UpdateDivisionScreen(
            state = UpdateDivisionState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {  },
            snackBarHostState = SnackbarHostState()
        )
    }
}