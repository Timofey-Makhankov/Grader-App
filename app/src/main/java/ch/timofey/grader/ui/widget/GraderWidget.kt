package ch.timofey.grader.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import ch.timofey.grader.MainActivity

@Composable
fun GraderWidget(){
    Column(
        modifier = GlanceModifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Where to?", modifier = GlanceModifier.padding(12.dp), style = TextStyle(color = GlanceTheme.colors.primary))
        Row(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                text = "Home",
                onClick = actionStartActivity<MainActivity>()
            )
            Button(
                text = "Work",
                onClick = actionStartActivity<MainActivity>()
            )
        }
    }
}