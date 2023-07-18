package ch.timofey.grader.ui.utils

import kotlin.math.round

fun getAverage(grades: List<Double>, weights: List<Double>): Double {
    if (grades.isEmpty() || weights.isEmpty()){
        return 0.0
    }
    var gradeSum = 0.0
    var weightSum = 0.0
    for (index in grades.indices) {
        gradeSum += grades[index] * weights[index]
        weightSum += weights[index]
    }
    return (gradeSum / weightSum)
}
fun getAverage(grades: List<Double>): Double{
    var total = 0.0
    if (grades.isEmpty()){
        return total
    }
    grades.forEach { grade ->
        total += grade
    }
    return total / grades.size
}

fun roundToPointFive(num: Double): Double{
    return round(num / .5) * .5
}