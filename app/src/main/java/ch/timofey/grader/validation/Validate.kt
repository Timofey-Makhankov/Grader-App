package ch.timofey.grader.validation

import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle

object Validate {
    fun year(year: String): Boolean {
        return try {
            Year.parse(year)
            true
        } catch (_: DateTimeParseException) {
            false
        }
    }

    fun date(date: String): Boolean {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        return try {
            LocalDate.parse(date, formatter)
            true
        } catch (_ : DateTimeParseException){
            false
        }
    }

    fun fullNumber(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (_ : NumberFormatException){
            false
        }
    }

    fun isDouble(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (_ : NumberFormatException){
            false
        }
    }

    fun swissGrade(grade: Double): Boolean {
        return grade in 1.0..6.0
    }

    fun inRange(value: Double, min: Double, max: Double): Boolean {
        return value in min..max
    }

    fun inRange(value: Int, min: Int, max: Int): Boolean {
        return value in min..max
    }
}