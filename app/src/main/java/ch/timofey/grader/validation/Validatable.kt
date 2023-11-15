package ch.timofey.grader.validation

interface Validatable {
    fun validate() : ValidationResponse
}