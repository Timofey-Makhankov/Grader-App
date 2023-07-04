package ch.timofey.grader.ui.screen.create_school

data class CreateSchoolState(
    var name: String = "",
    var description: String = "",
    var address: String = "",
    var zip: String = "",
    var city: String = "",
    var validName: Boolean = true,
    var nameErrorMessage: String = "",
    var showSnackBar: Boolean = false
)