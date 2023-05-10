package ch.timofey.grader.ui.utils

import ch.timofey.grader.db.domain.exam.Exam

fun getAverage(grades: List<Double>, weights: List<Double>): Double {
    var gradeSum = 0.0
    var weightSum = 0.0
    for (index in 0..grades.size) {
        gradeSum += grades[index] * weights[index]
        weightSum += weights[index]
    }
    return (gradeSum / weightSum)
}

fun getAverage(extractedExam: ExtractedExam): Double {
    var gradeSum = 0.0
    var weightSum = 0.0
    for (index in 0..extractedExam.grades.size) {
        gradeSum += extractedExam.grades[index] * extractedExam.weights[index]
        weightSum += extractedExam.weights[index]
    }
    return (gradeSum / weightSum)
}

fun getGradeAndWeightLists(exams: List<Exam>): ExtractedExam{
    val grades = arrayListOf<Double>()
    val weights = arrayListOf<Double>()
    exams.forEach { exam ->
        grades.add(exam.grade)
        weights.add(exam.weight)
    }
    return ExtractedExam(grades = grades.toList(), weights = weights.toList())
}

fun getAverageGrade(grades: List<Double>): Double{
    var total = 0.0
    grades.forEach { grade ->
        total += grade
    }
    return total / grades.size
}