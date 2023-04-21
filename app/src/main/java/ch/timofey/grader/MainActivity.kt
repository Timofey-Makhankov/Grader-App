package ch.timofey.grader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import ch.timofey.grader.db.AppDatabase
import ch.timofey.grader.db.Converter
import ch.timofey.grader.ui.navigation.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "grader-database"
        ).build()
        setContent {
            Navigation()
        }
    }
}

