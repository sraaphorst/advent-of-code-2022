package day02

// By Sebastian Raaphorst, 2022.

typealias RoundInput = Pair<RPS, RPS>
typealias GameInput = Iterable<RoundInput>

data class Score(val player: Int, val opponent: Int) {
    operator fun plus(other: Score): Score =
        Score(this.player + other.player, this.opponent + other.opponent)
    companion object {
        val ZeroScore = Score(0, 0)
    }
}

enum class RPS(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3)
}

enum class Outcomes(val score: Int) {
    LOSE(0),
    TIE(3),
    WIN(6)
}

enum class OpponentData(val rps: RPS) {
    A(RPS.ROCK),
    B(RPS.PAPER),
    C(RPS.SCISSORS)
}

// X, Y, and Z can be interpreted two different ways depending on if we are in problem 1 or 2.
enum class PlayerData(val rps: RPS, val outcome: Outcomes) {
    X(RPS.ROCK, Outcomes.LOSE),
    Y(RPS.PAPER, Outcomes.TIE),
    Z(RPS.SCISSORS, Outcomes.WIN)
}

// For problem 2, given the opponent's move and the desired outcome, we need to determine the player's move.
val toPlayerMoves: Map<RPS, Map<Outcomes, RPS>> = mapOf(
    RPS.ROCK to mapOf(Outcomes.LOSE to RPS.SCISSORS, Outcomes.TIE to RPS.ROCK, Outcomes.WIN to RPS.PAPER),
    RPS.PAPER to mapOf(Outcomes.LOSE to RPS.ROCK, Outcomes.TIE to RPS.PAPER, Outcomes.WIN to RPS.SCISSORS),
    RPS.SCISSORS to mapOf(Outcomes.LOSE to RPS.PAPER, Outcomes.TIE to RPS.SCISSORS, Outcomes.WIN to RPS.ROCK)
)

fun calculateRoundScore(input: RoundInput): Score {
    val (playerRPS, opponentRPS) = input

    fun aux(p1: RPS, p2: RPS): Outcomes = when {
        (p1.ordinal + 1) % 3 == p2.ordinal -> Outcomes.LOSE
        (p2.ordinal + 1) % 3 == p1.ordinal -> Outcomes.WIN
        else -> Outcomes.TIE
    }

    return Score(
        aux(playerRPS, opponentRPS).score + playerRPS.score,
        aux(opponentRPS, playerRPS).score + opponentRPS.score
        )
}

fun calculateGameScore(data: String, parser: (String) -> GameInput): Score =
    parser(data).map(::calculateRoundScore).fold(Score.ZeroScore){ s1, s2 -> s1 + s2 }

fun parseInput1(data: String): GameInput =
    data.split('\n').map { line ->
        val (opponent, player) = line.split(' ')
        RoundInput(PlayerData.valueOf(player).rps, OpponentData.valueOf(opponent).rps)
    }

fun parseInput2(data: String): GameInput =
    data.split('\n').map { line ->
        val (opponent, outcome) = line.split(' ')
        val opponentMove = OpponentData.valueOf(opponent).rps
        val playerMove = toPlayerMoves.getValue(opponentMove).getValue(PlayerData.valueOf(outcome).outcome)
        RoundInput(playerMove, opponentMove)
    }

fun problem1(data: String): Int =
    calculateGameScore(data, ::parseInput1).player

fun problem2(data: String): Int =
    calculateGameScore(data, ::parseInput2).player

fun main() {
    val data = object {}.javaClass.getResource("/aoc202202.txt")!!.readText()

    println("--- Day 2: Rock Paper Scissors ---")

    // Answer 1: 10404
    println("Part 1: ${problem1(data)}")

    // Answer 2: 10334
    println("Part 2: ${problem2(data)}")
}