package ch.timofey.grader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ch.timofey.grader.navigation.Navigation
import ch.timofey.grader.ui.theme.GraderTheme

@Composable
fun MainEntry(snackBarHostState: SnackbarHostState) {
    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.uiState.collectAsState()
    if (state.theme != null){
        GraderTheme(themeSetting = state.theme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Navigation(snackBarHostState)
            }
        }
    }
}