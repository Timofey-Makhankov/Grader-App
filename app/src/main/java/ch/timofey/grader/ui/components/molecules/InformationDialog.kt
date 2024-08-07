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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ch.timofey.grader.ui.theme.spacing
import ch.timofey.grader.R

@Composable
fun InformationDialog(
    title: String = "",
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
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
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .padding(
                        top = MaterialTheme.spacing.medium,
                        bottom = MaterialTheme.spacing.small
                    )
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.small)
                )
                content()
                Row{
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.close))
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
        InformationDialog(title = "This is an Example", onDismiss = {}){
            Text(text = l.values.joinToString(""))
        }
    }
}