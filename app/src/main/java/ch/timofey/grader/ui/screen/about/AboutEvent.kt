package ch.timofey.grader.ui.screen.about

sealed class AboutEvent {
    data object OnButtonSourceCLick : AboutEvent()
    data object OnButtonShareClick : AboutEvent()
    data object OnButtonCreateClick : AboutEvent()
    data object OnButtonDonateClick : AboutEvent()
}
