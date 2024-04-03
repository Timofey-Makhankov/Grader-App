package ch.timofey.grader.utils

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class SnackBarMessage(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false
): SnackbarVisuals, Parcelable
