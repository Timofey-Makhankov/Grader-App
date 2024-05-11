package ch.timofey.grader.utils

import ch.timofey.grader.R
import ch.timofey.grader.utils.exception.UnevenListDistributionException
import kotlin.math.round

fun getAverage(grades: List<Double>, weights: List<Double>): Double {
    if (grades.isEmpty() || weights.isEmpty()) {
        return 0.0
    }
    if (grades.size != weights.size) {
        throw UnevenListDistributionException(R.string.the_given_lists_are_uneven.toString())
    }
    var gradeSum = 0.0
    var weightSum = 0.0
    for (index in grades.indices) {
        gradeSum += grades[index] * weights[index]
        weightSum += weights[index]
    }
    return (gradeSum / weightSum)
}

fun getAverage(grades: List<Double>): Double {
    var total = 0.0
    if (grades.isEmpty()) {
        return total
    }
    grades.forEach { grade ->
        total += grade
    }
    return total / grades.size
}

fun roundToPointFive(num: Double): Double {
    return round(num / .5) * .5
}

fun calculatePointsFromGrade(grade: Double, minimumGrade: Double): Double {
    return grade - minimumGrade;
}

fun calculateDoublePointsFromGrade(grade: Double, minimumGrade: Double): Double {
    val result = minimumGrade - grade;
    return if (result < 0) {
        result * 2
    } else {
        result
    }
}