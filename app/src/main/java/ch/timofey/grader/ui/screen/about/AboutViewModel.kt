package ch.timofey.grader.ui.screen.about

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ch.timofey.grader.GraderApp
import ch.timofey.grader.ui.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AboutViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AboutEvent) {
        when (event) {
            is AboutEvent.OnButtonSourceCLick -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Timofey-Makhankov/Grader-App"))
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity( getApplication() ,browserIntent, null);
            }

            is AboutEvent.OnButtonShareClick -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Grader Github Repo")
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/Timofey-Makhankov/Grader-App")
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(getApplication(), shareIntent, null)
            }

            is AboutEvent.OnButtonCreateClick -> {
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TITLE, "file_name_to_save_as")
                intent.putExtra(Intent.EXTRA_TEXT, "jhdvuhavuhsehvbuhbvuhsbvuhbvudhvbudhvbudhb")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity( getApplication(), intent, null)
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}