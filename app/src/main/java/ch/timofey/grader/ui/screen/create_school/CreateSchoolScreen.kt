package ch.timofey.grader.ui.screen.create_school

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.utils.UiEvent
import ch.timofey.grader.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CreateSchoolScreen(
    state: CreateSchoolState,
    onEvent: (CreateSchoolEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.message, withDismissAction = event.withDismissAction)
                }

                else -> Unit
            }
        }
    }

    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(CreateSchoolEvent.OnReturnBack) },
            icon = Icons.Default.ArrowBack,
            contentDescription = "Go back to School Screen"
        )
    }, snackbarHost = { SnackbarHost(snackBarHostState) }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create School", style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                value = state.name,
                label = { Text(text = "School Name") },
                onValueChange = {
                        name -> onEvent(CreateSchoolEvent.OnNameChange(name))
                                },
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                singleLine = true,
                isError = !state.validName,
                supportingText = {
                    if (!state.validName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.nameErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                value = state.address,
                label = { Text(text = "Address") },
                onValueChange = { address -> onEvent(CreateSchoolEvent.OnAddressChange(address)) },
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.zip,
                label = { Text(text = "Zip") },
                onValueChange = { zip -> onEvent(CreateSchoolEvent.OnZipChange(zip)) },
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .padding(top = MaterialTheme.spacing.medium)
                    .fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.city,
                label = { Text(text = "City") },
                onValueChange = { city -> onEvent(CreateSchoolEvent.OnCityChange(city)) },
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .padding(top = MaterialTheme.spacing.medium)
                    .fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = state.description,
                label = {
                    Text(

                        text = "School Description"
                    )
                },
                onValueChange = { description ->
                    onEvent(
                        CreateSchoolEvent.OnDescriptionChange(
                            description
                        )
                    )
                },
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.large)
                    .padding(top = MaterialTheme.spacing.medium)
                    .fillMaxWidth(),
                minLines = 4,
                maxLines = 4
            )
            Button(
                onClick = { onEvent(CreateSchoolEvent.OnCreateSchool) },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Create")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCreateSchoolScreen() {
    GraderTheme {
        CreateSchoolScreen(state = CreateSchoolState(
            name = "Technische Berufsschule Zürich",
            description = "Das ist eine Berufsschule\n asfaf"
        ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() })
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewDarkModeCreateSchoolScreen() {
    GraderTheme {
        CreateSchoolScreen(state = CreateSchoolState(
            showSnackBar = true,
            validName = false,
            nameErrorMessage = "This is an Error Message"
        ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {},
            snackBarHostState = remember { SnackbarHostState() })
    }
}