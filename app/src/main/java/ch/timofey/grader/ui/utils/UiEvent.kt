package ch.timofey.grader.ui.utils

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val withDismissAction: Boolean = false) : UiEvent()
}
