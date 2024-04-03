package ch.timofey.grader.utils

sealed class UiEvent {
    data object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(
        val message: String, val withDismissAction: Boolean = false, val action: String? = null
    ) : UiEvent()
}
