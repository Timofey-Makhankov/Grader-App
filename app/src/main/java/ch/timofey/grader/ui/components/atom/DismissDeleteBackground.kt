package ch.timofey.grader.ui.components.atom

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ch.timofey.grader.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissDeleteBackground(
    modifier: Modifier = Modifier,
    dismissState: SwipeToDismissBoxState
){
    dismissState.dismissDirection
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
            else -> MaterialTheme.colorScheme.background
        }, label = "Color"
    )
    Card(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        shape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                modifier = Modifier.padding(end = MaterialTheme.spacing.large),
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}