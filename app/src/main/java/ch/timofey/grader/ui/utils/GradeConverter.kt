package ch.timofey.grader.ui.utils

import ch.timofey.grader.ui.utils.exception.UnevenListDistributionException
import kotlin.math.round

fun getAverage(grades: List<Double>, weights: List<Double>): Double {
    if (grades.isEmpty() || weights.isEmpty()) {
        return 0.0
    }
    if (grades.size != weights.size) {
        throw UnevenListDistributionException("The given lists are uneven")
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
    return minimumGrade - grade;
}

fun calculateDoublePointsFromGrade(grade: Double, minimumGrade: Double): Double {
    val result = minimumGrade - grade;
    return if (result < 0) {
        result * 2
    } else {
        result
    }
}