package ch.timofey.grader.type

import android.os.Parcelable
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import kotlinx.parcelize.Parcelize

@Parcelize
data class SnackBarMessage(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false
): SnackbarVisuals, Parcelable
