package ch.timofey.grader.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onFABClick: () -> Unit,
    appBar: @Composable () -> Unit,
    contentDescription: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { appBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = onFABClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = contentDescription
                )
            }
        },
    ) {
        content(it)
    }
}