package day05

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day05Test {
    companion object {
        private val input = """
                [D]    
            [N] [C]    
            [Z] [M] [P]
             1   2   3 

            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
        """.trimIndent()

        val data = parseInput(input)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(data), "CMZ")
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(data), "MCD")
    }
}
