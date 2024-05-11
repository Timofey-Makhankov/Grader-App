package ch.timofey.grader.ui.components.molecules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.R

@Composable
fun SwitchText(
    modifier: Modifier = Modifier,
    onValueChange: (value: Boolean) -> Unit,
    value: Boolean,
    enabled: Boolean = true,
    name: String,
    extraInfo: String = "",
    showExtraInformation: Boolean = false,
    dialog: @Composable (onDismissRequest: () -> Unit) -> Unit?
) {
    var openDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.8f)
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
                    overflow = TextOverflow.Visible,
                    minLines = 2,
                    style = MaterialTheme.typography.labelLarge
                )
            }

        }
        //Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.weight(0.3f)){
            if (showExtraInformation) {
                IconButton(
                    onClick = { openDialog = true }
                ) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Show Extra Information", tint = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
            Switch(checked = value, onCheckedChange = { onValueChange(it) }, enabled = enabled)
            if (openDialog) {
                dialog {
                    openDialog = false
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSwitchText() {
    GraderTheme {
        Column {
            SwitchText(onValueChange = {}, value = true, name = "Theme", dialog = {})
            SwitchText(
                onValueChange = {},
                value = true,
                enabled = false,
                name = R.string.calculate_points.toString(),
                extraInfo = R.string.disable_desc.toString(),
                showExtraInformation = true,
                dialog = {}
            )
        }
    }
}