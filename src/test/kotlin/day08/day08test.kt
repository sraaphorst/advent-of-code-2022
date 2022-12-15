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
        assertEquals(listOf(3, 0, 3, 7, 3), extractRow(grid, 0))
        assertEquals(listOf(2, 5, 5, 1, 2), extractRow(grid, 1))
        assertEquals(listOf(6, 5, 3, 3, 2), extractRow(grid, 2))
        assertEquals(listOf(3, 3, 5, 4, 9), extractRow(grid, 3))
        assertEquals(listOf(3, 5, 3, 9, 0), extractRow(grid, 4))
    }

    @Test
    fun `Extract columns`() {
        assertEquals(listOf(3, 2, 6, 3, 3), extractCol(grid, 0))
        assertEquals(listOf(0, 5, 5, 3, 5), extractCol(grid, 1))
        assertEquals(listOf(3, 5, 3, 5, 3), extractCol(grid, 2))
        assertEquals(listOf(7, 1, 3, 4, 9), extractCol(grid, 3))
        assertEquals(listOf(3, 2, 2, 9, 0), extractCol(grid, 4))
    }

    @Test
    fun `Row visibility forwards`() {
        assertEquals(setOf(0 to 0, 0 to 3), rowVisibilityForwards(grid, 0))
        assertEquals(setOf(1 to 0, 1 to 1), rowVisibilityForwards(grid, 1))
        assertEquals(setOf(2 to 0), rowVisibilityForwards(grid, 2))
        assertEquals(setOf(3 to 0, 3 to 2, 3 to 4), rowVisibilityForwards(grid, 3))
        assertEquals(setOf(4 to 0, 4 to 1, 4 to 3), rowVisibilityForwards(grid, 4))
    }

    @Test
    fun `Row visibility backwards`() {
        assertEquals(setOf(0 to 4, 0 to 3), rowVisibilityBackwards(grid, 0))
        assertEquals(setOf(1 to 4, 1 to 2), rowVisibilityBackwards(grid, 1))
        assertEquals(setOf(2 to 4, 2 to 3, 2 to 1, 2 to 0), rowVisibilityBackwards(grid, 2))
        assertEquals(setOf(3 to 4), rowVisibilityBackwards(grid, 3))
        assertEquals(setOf(4 to 4, 4 to 3), rowVisibilityBackwards(grid, 4))
    }

    @Test
    fun `Col visibility forwards`() {
        assertEquals(setOf(0 to 0, 2 to 0), colVisibilityForwards(grid, 0))
        assertEquals(setOf(0 to 1, 1 to 1), colVisibilityForwards(grid, 1))
        assertEquals(setOf(0 to 2, 1 to 2), colVisibilityForwards(grid, 2))
        assertEquals(setOf(0 to 3, 4 to 3), colVisibilityForwards(grid, 3))
        assertEquals(setOf(0 to 4, 3 to 4), colVisibilityForwards(grid, 4))
    }

    @Test
    fun `Col visibility backwards`() {
        assertEquals(setOf(4 to 0, 2 to 0), colVisibilityBackwards(grid, 0))
        assertEquals(setOf(4 to 1), colVisibilityBackwards(grid, 1))
        assertEquals(setOf(4 to 2, 3 to 2), colVisibilityBackwards(grid, 2))
        assertEquals(setOf(4 to 3), colVisibilityBackwards(grid, 3))
        assertEquals(setOf(4 to 4, 3 to 4), colVisibilityBackwards(grid, 4))
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(21, problem1(grid))
    }

    @Test
    fun `Scenic score (1,2)`() {
        assertEquals(4, scenicScore(grid, Coordinates(1, 2)))
    }

    @Test
    fun `Scenic score (3,2)`() {
        assertEquals(8, scenicScore(grid, Coordinates(3, 2)))
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(8, problem2(grid))
    }
}