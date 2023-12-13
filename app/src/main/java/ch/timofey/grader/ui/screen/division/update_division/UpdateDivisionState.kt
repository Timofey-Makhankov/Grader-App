package ch.timofey.grader.ui.screen.division.update_division

import ch.timofey.grader.db.domain.division.Division
import ch.timofey.grader.db.domain.division.DivisionValidationFields
import java.time.Year

data class UpdateDivisionState(
    val currentDivision: Division? = null,
    override val name: String = "",
    override val description: String = "",
    override val year: String = Year.now().value.toString(),
    val validYear: Boolean = true,
    val validName: Boolean = true,
    val validDescription: Boolean = true,
    val errorMessageYear: String = "",
    val errorMessageName: String = "",
    val errorMessageDescription: String = ""
) : DivisionValidationFields
