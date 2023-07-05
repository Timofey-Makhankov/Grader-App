package ch.timofey.grader.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ch.timofey.grader.ui.theme.spacing

@Composable
fun SwipeToDeleteBackground(
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = MaterialTheme.spacing.small)
            .padding(end = MaterialTheme.spacing.small)
        ,
        shape = MaterialTheme.shapes.large
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color),
            contentAlignment = Alignment.CenterEnd
        ){
            Icon(
                modifier = Modifier.padding(end = MaterialTheme.spacing.large),
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}