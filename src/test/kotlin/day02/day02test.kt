package day02

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day02Test {
    companion object {
        val data = """
            A Y
            B X
            C Z
        """.trimIndent()
    }

    @Test
    fun `ROCK vs ROCK`() {
        assertEquals(Score(4, 4), calculateRoundScore(RoundInput(RPS.ROCK, RPS.ROCK)))
    }

    @Test
    fun `ROCK vs PAPER`() {
        assertEquals(Score(1, 8), calculateRoundScore(RoundInput(RPS.ROCK, RPS.PAPER)))
    }

    @Test
    fun `ROCK vs SCISSORS`() {
        assertEquals( Score(7, 3), calculateRoundScore(RoundInput(RPS.ROCK, RPS.SCISSORS)))
    }

    @Test
    fun `PAPER vs ROCK`() {
        assertEquals(Score(8, 1), calculateRoundScore(RoundInput(RPS.PAPER, RPS.ROCK)))
    }

    @Test
    fun `PAPER vs PAPER`() {
        assertEquals(Score(5, 5), calculateRoundScore(RoundInput(RPS.PAPER, RPS.PAPER)))
    }

    @Test
    fun `PAPER vs SCISSORS`() {
        assertEquals(Score(2, 9), calculateRoundScore(RoundInput(RPS.PAPER, RPS.SCISSORS)))
    }

    @Test
    fun `SCISSORS vs ROCK`() {
        assertEquals(Score(3, 7), calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.ROCK)))
    }

    @Test
    fun `SCISSORS vs PAPER`() {
        assertEquals(Score(9, 2), calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.PAPER)))
    }

    @Test
    fun `SCISSORS vs SCISSORS`() {
        assertEquals(Score(6, 6), calculateRoundScore(RoundInput(RPS.SCISSORS, RPS.SCISSORS)))
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(15, problem1(data))
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(12, problem2(data))
    }
}