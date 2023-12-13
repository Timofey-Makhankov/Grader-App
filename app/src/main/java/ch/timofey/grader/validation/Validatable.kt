package ch.timofey.grader.validation

interface Validatable<Fields> {
    fun validateAll(fields: Fields): Boolean
}