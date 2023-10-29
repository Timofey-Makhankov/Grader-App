package ch.timofey.grader.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.components.atom.Circle
import ch.timofey.grader.ui.components.atom.Triangle
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

private enum class Shape {
    TRIANGLE_ID,
    CIRCLE_OUTLINE_ID,
    CIRCLE_FILL_ID,
    DASH_ID
}

@Composable
fun ScreenIndicator(
    modifier: Modifier = Modifier,
    pages: Int,
    index: Int,
) {
    val componentIndexes: ArrayList<Shape> = ArrayList()
    for (i in 0 until pages + pages - 1) {
        if (i % 2 == 0) {
            componentIndexes.add(
                when {
                    index * 2 == i -> Shape.TRIANGLE_ID
                    index * 2 < i -> Shape.CIRCLE_OUTLINE_ID
                    else -> Shape.CIRCLE_FILL_ID
                }
            )
        } else {
            componentIndexes.add(Shape.DASH_ID)
        }
    }

    LazyRow(
        Modifier
            .padding(8.dp)
            .height(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .then(modifier)
    ) {
        items(componentIndexes.toList()) {shape ->
            when (shape) {
                Shape.TRIANGLE_ID -> Triangle(triangleSize = 16.dp, color = MaterialTheme.colorScheme.primary)
                Shape.CIRCLE_FILL_ID -> Circle(
                    size = 16.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
                )
                Shape.CIRCLE_OUTLINE_ID -> Circle(
                    size = 16.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    outline = true,
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
                )
                Shape.DASH_ID -> Line(length = 16.dp, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreenIndicator() {
    GraderTheme {
        Column {
            ScreenIndicator(pages = 4, index = 0)
            ScreenIndicator(pages = 4, index = 1)
            ScreenIndicator(pages = 4, index = 2)
            ScreenIndicator(pages = 4, index = 3)
        }
    }
}