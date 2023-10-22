package ch.timofey.grader.ui.screen.create_division

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
    Scaffold(topBar = {
        AppBar(
            onNavigationIconClick = { onEvent(CreateDivisionEvent.OnBackButtonClick) },
            actionIcon = Icons.Default.ArrowBack,
            actionContentDescription = "Go back to previous screen"
        )
    }) {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Division", style = MaterialTheme.typography.titleLarge
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
                        withStyle(SpanStyle(
                            fontStyle = FontStyle.Italic, fontSize = 8.sp
                        )){
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
                label = { Text(text = buildAnnotatedString {
                    append("Division Year ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontSize = 8.sp)){
                        append("(Required)")
                    }
                }) },
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
                label = { Text(text = "Division Description") },
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
                Text(text = "Create")
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
        ), onEvent = {}, uiEvent = emptyFlow(), onPopBackStack = {})
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
        ), onEvent = {}, uiEvent = emptyFlow(), onPopBackStack = {})
    }
}
