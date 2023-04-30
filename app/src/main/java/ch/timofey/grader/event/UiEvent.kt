package ch.timofey.grader.event

sealed class UiEvent{
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()
}
