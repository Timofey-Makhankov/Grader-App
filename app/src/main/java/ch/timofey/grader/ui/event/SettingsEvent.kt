package ch.timofey.grader.ui.event

sealed class SettingsEvent{
    data class OnSettingChange(val state: Boolean) : SettingsEvent()
}