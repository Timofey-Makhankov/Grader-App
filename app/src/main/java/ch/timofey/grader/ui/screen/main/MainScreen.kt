package ch.timofey.grader.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ch.timofey.grader.navigation.Navigation
import ch.timofey.grader.ui.theme.GraderTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val state by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    if (state.theme != null){
        GraderTheme(themeSetting = state.theme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) {
                    Navigation(snackBarHostState)
                }
            }
        }
    }
}