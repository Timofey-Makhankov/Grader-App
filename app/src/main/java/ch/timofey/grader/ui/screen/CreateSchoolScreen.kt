package ch.timofey.grader.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import ch.timofey.grader.ui.event.CreateSchoolEvent
import ch.timofey.grader.ui.event.UiEvent
import ch.timofey.grader.ui.state.CreateSchoolState
import ch.timofey.grader.ui.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSchoolScreen(
    state: CreateSchoolState,
    onEvent: (CreateSchoolEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(CreateSchoolEvent.OnReturnBack) },
            icon = Icons.Default.ArrowBack,
            contentDescription = "Go back to School Screen"
        )
    },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create School",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                value = state.name,
                label = { Text(text = "Name") },
                onValueChange = { name -> onEvent(CreateSchoolEvent.OnNameChange(name)) },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                )
            )
            OutlinedTextField(
                value = state.description,
                label = { Text(text = "Description") },
                onValueChange = { description ->
                    onEvent(
                        CreateSchoolEvent.OnDescriptionChange(
                            description
                        )
                    )
                },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                )
            )
            OutlinedTextField(
                value = state.address,
                label = { Text(text = "Address") },
                onValueChange = { address -> onEvent(CreateSchoolEvent.OnAddressChange(address)) },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                )
            )
            OutlinedTextField(
                value = state.zip,
                label = { Text(text = "Zip") },
                onValueChange = { zip -> onEvent(CreateSchoolEvent.OnZipChange(zip)) },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                )
            )
            OutlinedTextField(
                value = state.city,
                label = { Text(text = "City") },
                onValueChange = { city -> onEvent(CreateSchoolEvent.OnCityChange(city)) },
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                )
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
        CreateSchoolScreen(
            state = CreateSchoolState(
                name = "Technische Berufsschule Zürich"
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PreviewDarkModeCreateSchoolScreen() {
    GraderTheme {
        CreateSchoolScreen(
            state = CreateSchoolState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {}
        )
    }
}