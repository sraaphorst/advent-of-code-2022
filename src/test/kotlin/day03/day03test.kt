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
    fun `Problem 1 example`() {
        assertEquals(157, problem1(data))
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(70, problem2(data))
    }
}
