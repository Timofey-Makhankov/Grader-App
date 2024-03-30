package ch.timofey.grader.ui.components.organisms

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.components.atom.FloatingActionButton
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    text: String,
    subText: String? = null
) {
    BottomAppBar(modifier = Modifier.then(modifier), actions = {
        Column {
            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
            if (subText != null) {
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.spacing.large),
                    text = subText,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
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
        BottomAppBar(text = "Average Grade: 5.9", subText = "Points: 1.5")
    }
}