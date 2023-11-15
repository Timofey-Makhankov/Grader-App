package ch.timofey.grader.ui.screen.division.update_division

import ch.timofey.grader.db.domain.division.Division
import java.time.Year

data class UpdateDivisionState(
    val currentDivision: Division? = null,
    val name: String = "",
    val description: String = "",
    val year: String = Year.now().value.toString(),
    val validYear: Boolean = true,
    val validName: Boolean = true,
    val errorMessageYear: String = "",
    val errorMessageName: String = ""
)
