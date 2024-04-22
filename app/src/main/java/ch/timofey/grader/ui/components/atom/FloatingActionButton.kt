package ch.timofey.grader.ui.components.atom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onFABClick: () -> Unit,
    contentDescription: String
) {
    FloatingActionButton(
        modifier = Modifier.then(modifier),
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = MaterialTheme.colorScheme.onTertiary,
        onClick = { onFABClick() }) {
        Icon(
            imageVector = Icons.Filled.Add, contentDescription = contentDescription
        )
    }
}