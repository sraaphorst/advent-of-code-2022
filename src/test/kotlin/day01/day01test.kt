package day01

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    companion object {
        private val input = """
            1000
            2000
            3000
            
            4000
            
            5000
            6000
            
            7000
            8000
            9000
            
            10000
        """.trimIndent()

        val data = parseInput(input)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(data), 24000)
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(data), 45000)
    }
}
