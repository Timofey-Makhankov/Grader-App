package ch.timofey.grader.ui.screen.settings

sealed class SettingsEvent{
    data class OnSettingChange(val state: Boolean) : SettingsEvent()
}