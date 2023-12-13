package ch.timofey.grader.ui.screen.module.update_module

sealed class UpdateModuleEvent {
    data class OnNameChange(val name: String) : UpdateModuleEvent()
    data class OnDescriptionChange(val description: String) : UpdateModuleEvent()
    data class OnTeacherFirstNameChange(val teacherFirstName: String) : UpdateModuleEvent()
    data class OnTeacherLastNameChange(val teacherLastName: String) : UpdateModuleEvent()
    data object OnBackButtonClick : UpdateModuleEvent()
    data object OnUpdateModuleButtonClick : UpdateModuleEvent()
}