package ch.timofey.grader.db.domain.module

interface ModuleValidationFields {
    val name: String
    val description: String
    val teacherFirstName: String
    val teacherLastName: String
}