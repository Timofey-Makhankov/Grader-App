package ch.timofey.grader.ui.screen.create_division

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.time.Year

@Composable
fun CreateDivisionScreen(
    state: CreateDivisionState,
    onEvent: (CreateDivisionEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit,
) {
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
                onNavigationIconClick = { onEvent(CreateDivisionEvent.OnBackButtonClick) },
                icon = Icons.Default.ArrowBack,
                contentDescription = "Go back to previous screen"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Division",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                value = state.name,
                label = { Text(text = "Division Name") },
                onValueChange = { name -> onEvent(CreateDivisionEvent.OnNameChange(name)) },
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                value = state.description,
                label = { Text(text = "Division Description") },
                onValueChange = { description ->
                    onEvent(
                        CreateDivisionEvent.OnDescriptionChange(
                            description
                        )
                    )
                },
                minLines = 1,
                maxLines = 4
            )
            OutlinedTextField(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                value = state.year,
                label = { Text(text = "Division Year") },
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
            Button(
                modifier = Modifier.padding(
                    top = MaterialTheme.spacing.medium
                ),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(CreateDivisionEvent.OnCreateDivision) }

            ) {
                Text(text = "Create")
            }
        }
    }
}

@Preview
@Composable
private fun CreateDivisionScreenPreview() {
    GraderTheme {
        CreateDivisionScreen(
            state = CreateDivisionState(
                year = "333"
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {}
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CreateDivisionScreenDarkModePreview() {
    GraderTheme {
        CreateDivisionScreen(
            state = CreateDivisionState(
                name = "Lehrjahr 2",
                year = Year.of(2022).value.toString()
            ),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {}
        )
    }
}
