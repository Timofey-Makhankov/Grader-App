package ch.timofey.grader.ui.screen.school.create_school

data class CreateSchoolState(
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val validName: Boolean = true,
    val validAddress: Boolean = true,
    val validZip: Boolean = true,
    val validCity: Boolean = true,
    val validDescription: Boolean = true,
    val nameErrorMessage: String = "",
    val addressErrorMessage: String = "",
    val zipErrorMessage: String = "",
    val cityErrorMessage: String = "",
    val descriptionErrorMessage: String = ""
)