package day02

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day01Test {
    companion object {
        val data = """
            A Y
            B X
            C Z
        """.trimIndent()
    }

    @Test
    fun `ROCK vs ROCK`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.ROCK, RPS.ROCK)), Score(4, 4))
    }

    @Test
    fun `ROCK vs PAPER`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.ROCK, RPS.PAPER)), Score(1, 8))
    }

    @Test
    fun `ROCK vs SCISSORS`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.ROCK, RPS.SCISSORS)), Score(7, 3))
    }

    @Test
    fun `PAPER vs ROCK`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.PAPER, RPS.ROCK)), Score(8, 1))
    }

    @Test
    fun `PAPER vs PAPER`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.PAPER, RPS.PAPER)), Score(5, 5))
    }

    @Test
    fun `PAPER vs SCISSORS`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.PAPER, RPS.SCISSORS)), Score(2, 9))
    }

    @Test
    fun `SCISSORS vs ROCK`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.ROCK)), Score(3, 7))
    }

    @Test
    fun `SCISSORS vs PAPER`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.PAPER)), Score(9, 2))
    }

    @Test
    fun `SCISSORS vs SCISSORS`() {
        assertEquals(calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.SCISSORS)), Score(6, 6))
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(data), 15)
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(data), 12)
    }
}