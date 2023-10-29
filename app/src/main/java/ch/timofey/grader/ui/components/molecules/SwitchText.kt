package ch.timofey.grader.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing

@Composable
fun SwitchText(
    modifier: Modifier = Modifier,
    onValueChange: (value: Boolean) -> Unit,
    value: Boolean,
    enabled: Boolean = true,
    name: String,
    extraInfo: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
        ) {
            Text(
                text = name,
                color = when (enabled) {
                    false -> MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
                    else -> MaterialTheme.colorScheme.onSurface
                },
                style = MaterialTheme.typography.titleMedium
            )
            AnimatedVisibility(visible = extraInfo != "") {
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
                    text = extraInfo,
                    fontStyle = FontStyle.Italic,
                    color = when (enabled) {
                        false -> MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled)
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }

        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = value, onCheckedChange = { onValueChange(it) }, enabled = enabled)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSwitchText() {
    GraderTheme {
        Column {
            SwitchText(onValueChange = {}, value = true, name = "Theme")
            SwitchText(
                onValueChange = {},
                value = true,
                enabled = false,
                name = "Calculate Points",
                extraInfo = "This is disabled because ..."
            )
        }
    }
}