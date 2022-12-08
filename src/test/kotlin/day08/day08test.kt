package day08

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08Test {
    companion object {
        private val data = """
            30373
            25512
            65332
            33549
            35390
        """.trimIndent()

        val grid = parseInput(data)
    }

    @Test
    fun `Extract rows`() {
        assertEquals(extractRow(grid, 0), listOf(3, 0, 3, 7, 3))
        assertEquals(extractRow(grid, 1), listOf(2, 5, 5, 1, 2))
        assertEquals(extractRow(grid, 2), listOf(6, 5, 3, 3, 2))
        assertEquals(extractRow(grid, 3), listOf(3, 3, 5, 4, 9))
        assertEquals(extractRow(grid, 4), listOf(3, 5, 3, 9, 0))
    }

    @Test
    fun `Extract columns`() {
        assertEquals(extractCol(grid, 0), listOf(3, 2, 6, 3, 3))
        assertEquals(extractCol(grid, 1), listOf(0, 5, 5, 3, 5))
        assertEquals(extractCol(grid, 2), listOf(3, 5, 3, 5, 3))
        assertEquals(extractCol(grid, 3), listOf(7, 1, 3, 4, 9))
        assertEquals(extractCol(grid, 4), listOf(3, 2, 2, 9, 0))
    }

    @Test
    fun `Row visibility forwards`() {
        assertEquals(rowVisibilityForwards(grid, 0), setOf(0 to 0, 0 to 3))
        assertEquals(rowVisibilityForwards(grid, 1), setOf(1 to 0, 1 to 1))
        assertEquals(rowVisibilityForwards(grid, 2), setOf(2 to 0))
        assertEquals(rowVisibilityForwards(grid, 3), setOf(3 to 0, 3 to 2, 3 to 4))
        assertEquals(rowVisibilityForwards(grid, 4), setOf(4 to 0, 4 to 1, 4 to 3))
    }

    @Test
    fun `Row visibility backwards`() {
        assertEquals(rowVisibilityBackwards(grid, 0), setOf(0 to 4, 0 to 3))
        assertEquals(rowVisibilityBackwards(grid, 1), setOf(1 to 4, 1 to 2))
        assertEquals(rowVisibilityBackwards(grid, 2), setOf(2 to 4, 2 to 3, 2 to 1, 2 to 0))
        assertEquals(rowVisibilityBackwards(grid, 3), setOf(3 to 4))
        assertEquals(rowVisibilityBackwards(grid, 4), setOf(4 to 4, 4 to 3))
    }

    @Test
    fun `Col visibility forwards`() {
        assertEquals(colVisibilityForwards(grid, 0), setOf(0 to 0, 2 to 0))
        assertEquals(colVisibilityForwards(grid, 1), setOf(0 to 1, 1 to 1))
        assertEquals(colVisibilityForwards(grid, 2), setOf(0 to 2, 1 to 2))
        assertEquals(colVisibilityForwards(grid, 3), setOf(0 to 3, 4 to 3))
        assertEquals(colVisibilityForwards(grid, 4), setOf(0 to 4, 3 to 4))
    }

    @Test
    fun `Col visibility backwards`() {
        assertEquals(colVisibilityBackwards(grid, 0), setOf(4 to 0, 2 to 0))
        assertEquals(colVisibilityBackwards(grid, 1), setOf(4 to 1))
        assertEquals(colVisibilityBackwards(grid, 2), setOf(4 to 2, 3 to 2))
        assertEquals(colVisibilityBackwards(grid, 3), setOf(4 to 3))
        assertEquals(colVisibilityBackwards(grid, 4), setOf(4 to 4, 3 to 4))
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(grid), 21)
    }

    @Test
    fun `Scenic score (1,2)`() {
        assertEquals(scenicScore(grid, Coordinates(1, 2)), 4)
    }

    @Test
    fun `Scenic score (3,2)`() {
        assertEquals(scenicScore(grid, Coordinates(3, 2)), 8)
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(grid), 8)
    }
}