package ch.timofey.grader.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier, floatingActionButton: @Composable () -> Unit = {}, text: String
) {
    BottomAppBar(modifier = Modifier.then(modifier), actions = {
        Text(
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
            text = text,
            style = MaterialTheme.typography.titleLarge
        )
    }, floatingActionButton = { floatingActionButton() })
}

@Preview
@Composable
private fun BottomAppBarPreview() {
    GraderTheme {
        BottomAppBar(text = "Average Grade: 5.9", floatingActionButton = {
            FloatingActionButton(
                onFABClick = {}, contentDescription = ""
            )
        })
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BottomAppBarDarkModePreview() {
    GraderTheme {
        BottomAppBar(text = "Average Grade: 5.9")
    }
}