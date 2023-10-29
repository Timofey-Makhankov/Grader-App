package ch.timofey.grader.ui.screen.module.create_module

sealed class CreateModuleEvent {
    data class OnNameChange(val name: String) : CreateModuleEvent()
    data class OnDescriptionChange(val description: String) : CreateModuleEvent()
    data class OnTeacherFirstnameChange(val teacherFirstname: String) : CreateModuleEvent()
    data class OnTeacherLastnameChange(val teacherLastname: String) : CreateModuleEvent()
    object OnBackButtonClick : CreateModuleEvent()
    object OnCreateModuleButtonClick : CreateModuleEvent()
}
