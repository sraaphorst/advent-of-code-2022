package day04

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04Test {
    companion object {
        val data = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent().split('\n').map(::parseIntervalPair)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(data), 2)
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(data), 4)
    }
}
