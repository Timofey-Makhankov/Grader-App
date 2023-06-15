package ch.timofey.grader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.LocaleManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ch.timofey.grader.navigation.Navigation
import ch.timofey.grader.ui.theme.GraderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("App: ${AppCompatDelegate.getApplicationLocales()}")
        println(
            "System: ${
                GraderApp.getContext()?.let { LocaleManagerCompat.getSystemLocales(it) }
            }"
        )
        installSplashScreen()
        setContent {
            GraderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

