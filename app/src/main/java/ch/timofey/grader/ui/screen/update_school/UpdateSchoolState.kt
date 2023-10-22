package ch.timofey.grader.ui.screen.update_school

import ch.timofey.grader.db.domain.school.School

data class UpdateSchoolState(
    val currentSchool: School? = null,
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val validName: Boolean = true,
    val validAddress: Boolean = true,
    val validZip: Boolean = true,
    val validCity: Boolean = true,
    val nameErrorMessage: String = "",
    val addressErrorMessage: String = "",
    val zipErrorMessage: String = "",
    val cityErrorMessage: String = ""
)
