package ch.timofey.grader.ui.screen.about

sealed class AboutEvent {
    data object OnButtonSourceCLick : AboutEvent()
    data object OnButtonShareClick : AboutEvent()
}
