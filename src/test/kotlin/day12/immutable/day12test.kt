package day12.immutable

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

typealias c = Coordinates

class Day12Test {
    companion object {
        private val board = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()

        val input = parseInput(board)
    }

    @Test
    fun `Board parsing`() {
        val expectedBoard = mapOf(
            c(0,0) to 0, c(0,1) to 0, c(0,2) to 1, c(0,3) to 16, c(0,4) to 15, c(0,5) to 14, c(0,6) to 13, c(0,7) to 12,
            c(1,0) to 0, c(1,1) to 1, c(1,2) to 2, c(1,3) to 17, c(1,4) to 24, c(1,5) to 23, c(1,6) to 23, c(1,7) to 11,
            c(2,0) to 0, c(2,1) to 2, c(2,2) to 2, c(2,3) to 18, c(2,4) to 25, c(2,5) to 25, c(2,6) to 23, c(2,7) to 10,
            c(3,0) to 0, c(3,1) to 2, c(3,2) to 2, c(3,3) to 19, c(3,4) to 20, c(3,5) to 21, c(3,6) to 22, c(3,7) to 9,
            c(4,0) to 0, c(4,1) to 1, c(4,2) to 3, c(4,3) to 4,  c(4,4) to 5,  c(4,5) to 6,  c(4,6) to 7,  c(4,7) to 8)
        val expectedStart = c(0, 0)
        val expectedEnd = c(2, 5)
        val (board, start, end) = parseInput(board)
        assertEquals(board, expectedBoard)
        assertEquals(start, expectedStart)
        assertEquals(end, expectedEnd)
    }

    @Test
    fun `Part 1 example`() {
        assertEquals(31, problem1(input))
    }

    @Test
    fun `Part 2 example`() {
        assertEquals(29, problem2(input))
    }
}