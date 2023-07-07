package ch.timofey.grader

import ch.timofey.grader.ui.utils.getAverage
import org.junit.Test
import org.junit.Assert.*

class GraderConverterTest {
    @Test
    fun returnGradeWithOneGradeAndOneWeight(){
        assertEquals(5.6, getAverage(listOf(5.6), listOf(1.0)), 0.001)
    }
    @Test
    fun returnGradeWithEmptyLists(){
        assertEquals(0.0, getAverage(listOf(), listOf()), 0.001)
    }
}