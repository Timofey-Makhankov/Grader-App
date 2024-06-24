package ch.timofey.grader.ui.components.molecules

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.timofey.grader.ui.components.atom.Circle
import ch.timofey.grader.ui.components.atom.Triangle
import ch.timofey.grader.ui.theme.GraderTheme
import ch.timofey.grader.R

@Composable
fun ShowNavigationIconsInformation() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenIndicator(pages = 4, index = 2)
        Text(text = stringResource(id = R.string.indicators_desc))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Circle(size = 16.dp, color = MaterialTheme.colorScheme.secondary, outline = true)
            Circle(size = 16.dp, color = MaterialTheme.colorScheme.secondary)
            Triangle(triangleSize = 16.dp, color = MaterialTheme.colorScheme.primary)
        }
        Text(text = "")
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShowNavigationIconsInformationPreview(){
    GraderTheme {
        InformationDialog(title = stringResource(id = R.string.show_navigation_icons), onDismiss = {}) {
            ShowNavigationIconsInformation()
        }
    }
}