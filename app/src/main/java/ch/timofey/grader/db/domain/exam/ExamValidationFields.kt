package ch.timofey.grader.db.domain.exam

interface ExamValidationFields {
    val name: String
    val description: String
    val grade: String
    val weight: String
    val dateTaken: String
}