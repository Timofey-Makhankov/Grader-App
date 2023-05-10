package ch.timofey.grader.ui.state

data class CreateSchoolState (
    var name: String = "",
    var description: String = "",
    var address: String = "",
    var zip: String = "",
    var city: String = ""
)