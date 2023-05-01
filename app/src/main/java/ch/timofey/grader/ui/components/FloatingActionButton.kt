package ch.timofey.grader.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar {}
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Create a new School"
                )
            }
        },
    ) {
        content(it)
    }
}