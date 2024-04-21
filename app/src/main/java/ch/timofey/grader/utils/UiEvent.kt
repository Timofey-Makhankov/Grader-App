package ch.timofey.grader.utils

import androidx.compose.material3.SnackbarVisuals

sealed class UiEvent {
    data object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(
        val message: String, val withDismissAction: Boolean = false, val action: String? = null
    ) : UiEvent()
    data class PopBackStackAndShowSnackBar(val snackbarVisuals: SnackbarVisuals) : UiEvent()
}
