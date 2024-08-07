package ch.timofey.grader.ui.screen.division.create_division

import android.content.res.Configuration
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import java.time.Year
import ch.timofey.grader.R

@Composable
fun CreateDivisionScreen(
    state: CreateDivisionState,
    onEvent: (CreateDivisionEvent) -> Unit,
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
    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(CreateDivisionEvent.OnBackButtonClick) },
            actionIcon = Icons.AutoMirrored.Filled.ArrowBack,
            actionContentDescription = stringResource(id = R.string.go_to_settings_screen)
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.create_division), style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = MaterialTheme.spacing.small)
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                value = state.name,
                label = {
                    Text(text = buildAnnotatedString {
                        append(stringResource(id = R.string.division_name))
                        withStyle(
                            SpanStyle(
                                fontStyle = FontStyle.Italic, fontSize = 8.sp
                            )
                        ) {
                            append("(Required)")
                        }
                    })
                },
                onValueChange = { name -> onEvent(CreateDivisionEvent.OnNameChange(name)) },
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
                label = {
                    Text(text = buildAnnotatedString {
                        append(stringResource(id = R.string.division_year))
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)) {
                            append("(Required)")
                        }
                    })
                },
                onValueChange = { year ->
                    onEvent(CreateDivisionEvent.OnYearChange(year))
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
                label = { Text(text = stringResource(id = R.string.division_description)) },
                onValueChange = { description ->
                    onEvent(
                        CreateDivisionEvent.OnDescriptionChange(
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
                onClick = { onEvent(CreateDivisionEvent.OnCreateDivision) }

            ) {
                Text(text = stringResource(id = R.string.create))
            }
        }
    }
}

@Preview
@Composable
private fun CreateDivisionScreenPreview() {
    GraderTheme {
        CreateDivisionScreen(state = CreateDivisionState(
            year = "333"
        ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() })
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CreateDivisionScreenDarkModePreview() {
    GraderTheme {
        CreateDivisionScreen(state = CreateDivisionState(
            name = "Lehrjahr 2", year = Year.of(2022).value.toString()
        ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() })
    }
}
