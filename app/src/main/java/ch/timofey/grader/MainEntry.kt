package ch.timofey.grader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import ch.timofey.grader.ui.theme.GraderTheme

@Composable
fun MainEntry(
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.uiState.collectAsState()
    GraderTheme( themeSetting = state.theme ) {
        content()
    }
}