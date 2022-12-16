package day14

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    companion object {
        private val data = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
        """.trimIndent()

        val cave = parseInput(data)
    }

    @Test
    fun `Parse cave`() {
        val expected: Set<Coordinates> = setOf(
            (498 to 4),
            (498 to 5),
            (498 to 6),
            (497 to 6),
            (496 to 6),
            (503 to 4),
            (502 to 4),
            (502 to 5),
            (502 to 6),
            (502 to 7),
            (502 to 8),
            (502 to 9),
            (501 to 9),
            (500 to 9),
            (499 to 9),
            (498 to 9),
            (497 to 9),
            (496 to 9),
            (495 to 9),
            (494 to 9)
        )
        assertEquals(expected, cave)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(24, problem1(cave))
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(93, problem2(cave))
    }
}