package ch.timofey.grader.db.domain.school

interface SchoolValidationFields {
    val name: String
    val description: String
    val city: String
    val address: String
    val zip: String
}
