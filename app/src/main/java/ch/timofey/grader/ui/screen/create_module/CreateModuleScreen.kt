package ch.timofey.grader.ui.screen.create_module

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.AppBar
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.ui.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun CreateModuleScreen(
    state: CreateModuleState,
    onEvent: (CreateModuleEvent) -> Unit,
    uiEvent: Flow<UiEvent>,
    onPopBackStack: () -> Unit
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
            onNavigationIconClick = { onEvent(CreateModuleEvent.OnBackButtonClick) },
            icon = Icons.Default.ArrowBack,
            contentDescription = "Go back to previous screen"
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Module", style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.name,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnNameChange(value)) },
                singleLine = true,
                label = { Text(text = "Module name") })
            OutlinedTextField(modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.teacherFirstname,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnTeacherFirstnameChange(value)) },
                singleLine = true,
                label = { Text(text = "Teacher Firstname") })
            OutlinedTextField(modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.teacherLastname,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnTeacherLastnameChange(value)) },
                singleLine = true,
                label = { Text(text = "Teacher Lastname") })
            OutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                value = state.description,
                onValueChange = { value -> onEvent(CreateModuleEvent.OnDescriptionChange(value)) },
                label = { Text(text = "Module description") },
                minLines = 4
            )
            Button(modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                shape = MaterialTheme.shapes.large,
                onClick = { onEvent(CreateModuleEvent.OnCreateModuleButtonClick) }) {
                Text(text = "Create")
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
            onPopBackStack = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateModuleScreenDarkModePreview() {
    GraderTheme {
        CreateModuleScreen(state = CreateModuleState(),
            onEvent = {},
            uiEvent = emptyFlow(),
            onPopBackStack = {})
    }
}