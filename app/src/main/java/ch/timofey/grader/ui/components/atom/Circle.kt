package ch.timofey.grader.ui.components.atom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.theme.GraderTheme

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color,
    outline: Boolean = false
) {
    Box(modifier = modifier){
        Canvas(modifier = Modifier.size(size, size)) {
            drawCircle(
                radius = if (outline) size.toPx() / 2 - 1.dp.toPx() else size.toPx() / 2,
                color = color,
                style = if (outline) Stroke(2.dp.toPx()) else Fill
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCircleFilled() {
    GraderTheme {
        Box {
            Circle(size = 25.dp, color = MaterialTheme.colorScheme.tertiary)
        }
    }
}

@Preview
@Composable
private fun PreviewCircleOutlined() {
    GraderTheme {
        Box {
            Circle(
                size = 25.dp,
                color = MaterialTheme.colorScheme.primary,
                outline = true
            )
        }
    }
}