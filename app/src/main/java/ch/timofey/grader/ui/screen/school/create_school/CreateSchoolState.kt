package ch.timofey.grader.ui.screen.school.create_school

import ch.timofey.grader.db.domain.school.SchoolValidationFields

data class CreateSchoolState(
    override val name: String = "",
    override val description: String = "",
    override val address: String = "",
    override val zip: String = "",
    override val city: String = "",
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
) : SchoolValidationFields