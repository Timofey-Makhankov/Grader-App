package ch.timofey.grader.ui.widget
/*
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

object CounterWidget : GlanceAppWidget() {
    val countKey = intPreferencesKey("count")
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val count = currentState(key = countKey) ?: 0
            Column(
                modifier = GlanceModifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = count.toString(), style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(MaterialTheme.colorScheme.onBackground),
                        fontSize = 26.sp
                    )
                )
                Button(
                    text = "Increment",
                    onClick = actionRunCallback(IncrementActionCallback::class.java)
                )
            }
        }
    }
}

class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget
}

object IncrementActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context, glanceId: GlanceId, parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentCount = prefs[CounterWidget.countKey]
            if (currentCount != null) {
                prefs[CounterWidget.countKey] = currentCount + 1
            } else {
                prefs[CounterWidget.countKey] = 1
            }
        }
        CounterWidget.update(context, glanceId)
    }
}*/