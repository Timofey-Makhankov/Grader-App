package ch.timofey.grader.ui.components.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.theme.GraderTheme

@Composable
fun Line(
    length: Dp,
    color: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(length.plus(4.dp), length.plus(4.dp))
        /*.background(MaterialTheme.colorScheme.tertiary)*/
    ) {
        Box(
            modifier = Modifier
                .height(length.div(6))
                .fillMaxSize()
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color = color)
        )
    }
}

@Preview
@Composable
private fun PreviewLine() {
    GraderTheme {
        Line(length = 24.dp, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview
@Composable
private fun PreviewLineLarge() {
    GraderTheme {
        Line(length = 48.dp, color = MaterialTheme.colorScheme.onTertiary)
    }
}