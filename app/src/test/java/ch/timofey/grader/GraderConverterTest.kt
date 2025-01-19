package ch.timofey.grader

import ch.timofey.grader.utils.calculatePointsFromGrade
import ch.timofey.grader.utils.exception.UnevenListDistributionException
import ch.timofey.grader.utils.getAverage
import ch.timofey.grader.utils.roundToPointFive
import org.assertj.core.api.AbstractDoubleAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GraderConverterTest {
    fun AbstractDoubleAssert<*>.isCloseTo(expected: Double): AbstractDoubleAssert<*> =
        this.isCloseTo(expected, Offset.offset(0.001))

    @Nested
    inner class WeightedCalculations {
        @Test
        fun `return average grade with one grade and one weight`() {
            assertThat(getAverage(listOf(5.6), listOf(1.0))).isCloseTo(5.6)
        }

        @Test
        fun `return 0 when empty list`() {
            assertEquals(0.0, getAverage(listOf(), listOf()), 0.001)
        }

        @Test
        fun `return 0 when grades list and empty weight list`() {
            assertEquals(0.0, getAverage(listOf(5.5), listOf()), 0.001)
        }

        @Test
        fun `return 0 when empty grade list and weight list`() {
            assertEquals(0.0, getAverage(listOf(), listOf(1.0)), 0.001)
        }

        @Test
        fun `return average grade with weights and grades`() {
            assertEquals(5.15, getAverage(listOf(3.5, 6.5), listOf(0.9, 1.1)), 0.001)
        }

        @Test
        fun `return average grade with extended weights and grades`() {
            assertEquals(
                2.891,
                getAverage(
                    listOf(3.92, 4.34, 1.13, 2.92, 1.91),
                    listOf(0.25, 0.91, 0.15, 0.18, 1.34)
                ),
                0.001
            )
        }

        @Test
        fun `throw UnevenListDistributionException when lists are uneven`() {
            assertThrows<UnevenListDistributionException> {
                getAverage(listOf(5.6, 3.4), listOf(1.2))
            }
            assertThrows<UnevenListDistributionException> {
                getAverage(listOf(4.5), listOf(1.2, 0.9))
            }
        }
    }

    @Nested
    inner class NonWeightedCalculations {
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
        fun returnAverageGrade_WithFiveItems() {
            assertEquals(3.9, getAverage(listOf(3.71, 3.41, 5.35, 3.41, 3.62)), 0.001)
        }
    }

    @Nested
    inner class PointFiveRounding {
        @Test
        fun returnFormattedDouble() {
            assertEquals(5.5, roundToPointFive(5.6679), 0.001)
        }
    }

    // Points calculation
    @Nested
    inner class PointsCalculation {
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
}