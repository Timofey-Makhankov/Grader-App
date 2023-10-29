package ch.timofey.grader.ui.components.molecules

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.components.atom.Triangle
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Composable
fun BreadCrumb(
    modifier: Modifier = Modifier,
    locationTitles: List<String>,
) {
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = null) {
        listState.scrollToItem(locationTitles.lastIndex)
    }
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(locationTitles, key = { _, item -> item.hashCode() }) { index, title ->
            val lastIndex = index >= locationTitles.lastIndex
            Surface(
                color = if (lastIndex) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.background,
                modifier = if (lastIndex) Modifier.clip(MaterialTheme.shapes.large) else Modifier
            ) {
                Text(
                    modifier = if (lastIndex) Modifier
                        .padding(horizontal = MaterialTheme.spacing.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer) else Modifier,
                    text = title,
                )
            }
            if (index < locationTitles.lastIndex) Triangle(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
                triangleSize = 12.dp,
                color = MaterialTheme.colorScheme.secondary,
                rotation = -90f
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBreadCrumb() {
    GraderTheme {
        BreadCrumb(locationTitles = listOf("school item", "division item", "module item"))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDarkModeBreadCrumb() {
    GraderTheme {
        BreadCrumb(
            locationTitles = listOf(
                "school item", "division item", "module item", "Exam Item"
            )
        )
    }
}