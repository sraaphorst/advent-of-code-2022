package day09

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day09Test {
    companion object {
        private val input1 = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
        """.trimIndent()

        val data1 = parseInput(input1)

        private val input2 = """
            R 5
            U 8
            L 8
            D 3
            R 17
            D 10
            L 25
            U 20
        """.trimIndent()

        val data2 = parseInput(input2)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(13, problem1(data1))
    }

    @Test
    fun `Problem 2 example 1`() {
        assertEquals(1, problem2(data1))
    }

    @Test
    fun `Problem 2 example 2`() {
        assertEquals(36, problem2(data2))
    }
}