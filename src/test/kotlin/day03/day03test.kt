package day03

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day03Test {
    companion object {
        val data = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().split('\n')
    }

    @Test
    fun `Problem 1`() {
        assertEquals(problem1(data), 157)
    }

    @Test
    fun `Problem 2`() {
        assertEquals(problem2(data), 70)
    }
}
