package ch.timofey.grader.ui.screen.division.division_list.create_division

import java.time.Year

data class CreateDivisionState(
    val name: String = "",
    val description: String = "",
    val year: String = Year.now().value.toString(),
    val validYear: Boolean = true,
    val validName: Boolean = true,
    val errorMessageYear: String = "",
    val errorMessageName: String = ""
)
