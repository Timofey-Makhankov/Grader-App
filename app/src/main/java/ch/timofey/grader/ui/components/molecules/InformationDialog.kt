package ch.timofey.grader.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ch.timofey.grader.ui.theme.spacing

@Composable
fun InformationDialog(
    onDismiss: () -> Unit,
    text: String
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true,
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.small).padding(top = MaterialTheme.spacing.extraSmall)
            ) {
                Text(text = text)
                Row{
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "Close")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun InformationDialogPreview(){
    val l = LoremIpsum(30)
    MaterialTheme {
        InformationDialog(onDismiss = {}, text = l.values.joinToString(""))
    }
}