package ch.timofey.grader.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun Triangle(
    triangleSize: Dp, color: Color, outlineColor: Color = color, rotation: Float = 0f
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.extraSmall)
            .size(triangleSize), onDraw = {
            val q: Float = (size.width / sin(75f * PI / 180) * sin(15f * PI / 180)).toFloat()
            rotate(-45f + rotation) {
                translate(
                    left = size.width.toDp().value / 2, top = -size.height.toDp().value / 2
                ) {
                    drawPath(color = color, path = Path().apply {
                        moveTo(0f, size.height) // A
                        lineTo(size.width, size.height - q) // B
                        lineTo(q, 0f) // C
                        close()
                    })
                }
            }
        })
        Canvas(modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.extraSmall)
            .size(triangleSize.minus(triangleSize.div(5))), onDraw = {
            rotate(-45f + rotation) {
                val q: Float = (size.width / sin(75f * PI / 180) * sin(15f * PI / 180)).toFloat()
                translate(
                    left = size.width.toDp().value / 1.7f,
                    top = -size.height.toDp().value / 1.7f
                ) {
                    drawPath(color = outlineColor, path = Path().apply {
                        moveTo(0f, size.height) // Point A
                        lineTo(size.width, size.height - q) // Point B
                        lineTo(q, 0f) // Point C
                        close()
                    })
                }
            }
        })
    }
}

@Preview(showBackground = true, name = "Small Triangle")
@Composable
private fun PreviewTriangleSmall() {
    GraderTheme {
        Triangle(
            triangleSize = 25.dp,
            color = MaterialTheme.colorScheme.onPrimary,
            outlineColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTriangle() {
    GraderTheme {
        Triangle(
            triangleSize = 50.dp, color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTriangleLarge() {
    GraderTheme {
        Triangle(
            triangleSize = 100.dp,
            color = MaterialTheme.colorScheme.onPrimary,
            outlineColor = MaterialTheme.colorScheme.primary,
            rotation = 90f
        )
    }
}
