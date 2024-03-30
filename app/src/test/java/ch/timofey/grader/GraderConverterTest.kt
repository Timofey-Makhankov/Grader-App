package ch.timofey.grader

import ch.timofey.grader.ui.utils.calculatePointsFromGrade
import ch.timofey.grader.ui.utils.exception.UnevenListDistributionException
import ch.timofey.grader.ui.utils.getAverage
import ch.timofey.grader.ui.utils.roundToPointFive
import org.junit.Test
import org.junit.Assert.*

class GraderConverterTest {
    // Testing getAverage() with grades and weights
    @Test
    fun returnAverageGrade_WithOneGradeAndOneWeight() {
        assertEquals(5.6, getAverage(listOf(5.6), listOf(1.0)), 0.001)
    }

    @Test
    fun returnZero_WithEmptyLists() {
        assertEquals(0.0, getAverage(listOf(), listOf()), 0.001)
    }

    @Test
    fun returnZero_WithGradesAndEmptyWeightList(){
        assertEquals(0.0, getAverage(listOf(5.5), listOf()), 0.001)
    }

    @Test
    fun returnZero_WithEmptyGradesAndWeighList(){
        assertEquals(0.0, getAverage(listOf(), listOf(1.0)), 0.001)
    }

    @Test(expected = UnevenListDistributionException::class)
    fun throwUnevenListDistributionException_WithTwoGradesAndOneWeight(){
        getAverage(listOf(5.6, 3.4), listOf(1.2))
    }

    @Test(expected = UnevenListDistributionException::class)
    fun throwUnevenListDistributionException_WithOneGradesAndTwoWeight(){
        getAverage(listOf(4.5), listOf(1.2, 0.9))
    }

    @Test()
    fun returnAverageGrade_WithTwoGradesAndWeights(){
        assertEquals(5.15, getAverage(listOf(3.5, 6.5), listOf(0.9, 1.1)), 0.001)
    }

    @Test()
    fun returnAverageGrade_WithFiveGradesAndWeights(){
        assertEquals(2.891, getAverage(listOf(3.92, 4.34, 1.13, 2.92, 1.91), listOf(0.25, 0.91, 0.15, 0.18 ,1.34)), 0.001)
    }

    // Testing getAverage() with grades
    @Test
    fun returnAverageGrade_WithOneItem() {
        assertEquals(5.6, getAverage(listOf(5.6)), 0.001)
    }

    @Test
    fun returnAverageGrade_WithNoItems() {
        assertEquals(0.0, getAverage(listOf()), 0.001)
    }

    @Test
    fun returnAverageGrade_WithTwoItems() {
        assertEquals(3.95, getAverage(listOf(3.4, 4.5)), 0.001)
    }

    @Test
    fun returnAverageGrade_WithFiveItems(){
        assertEquals(3.9, getAverage(listOf(3.71, 3.41, 5.35, 3.41, 3.62)), 0.001)
    }

    // Testing roundToPointFive()
    @Test
    fun returnFormattedDouble() {
        assertEquals(5.5, roundToPointFive(5.6679), 0.001)
    }

    // Points calculation

    @Test
    fun returnPositivePointsValue() {
        assertEquals(1.0, calculatePointsFromGrade(5.0, 4.0), 0.001)
    }

    @Test
    fun returnNegativePointsValue() {
        assertEquals(-1.0, calculatePointsFromGrade(3.0, 4.0), 0.001)
    }

    @Test
    fun returnZeroPointsValue() {
        assertEquals(0.0, calculatePointsFromGrade(4.0, 4.0), 0.001)
    }

    @Test
    fun returnHalfPositivePointsValue() {
        assertEquals(0.5, calculatePointsFromGrade(4.5, 4.0), 0.001)
    }
}
