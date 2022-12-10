package day09

import kotlin.math.abs

// By Sebastian Raaphorst, 2022.

typealias Coordinates = Pair<Int, Int>
val ZERO = Coordinates(0, 0)

enum class Move(val coords: Coordinates) {
    L(Coordinates(0, -1)),
    R(Coordinates(0, 1)),
    U(Coordinates(-1, 0)),
    D(Coordinates(1, 0))
}

operator fun Coordinates.plus(other: Coordinates): Coordinates =
    Coordinates(this.first + other.first, this.second + other.second)

operator fun Coordinates.plus(move: Move): Coordinates =
    this + move.coords

operator fun Coordinates.unaryMinus(): Coordinates =
    Coordinates(-this.first, -this.second)

operator fun Coordinates.minus(other: Coordinates): Coordinates =
    this + (-other)

fun <A, B> Pair<A, B>.swap(): Pair<B, A> =
    Pair(this.second, this.first)

// A map where:
// 1. Keys are the distance from the previous knot k1 to the next knot k2; and
// 2. Values are how k2 should move to be adjacent to k1.
val tailMoves: Map<Coordinates, Coordinates> = run {
    // No movement: the knots are on top of each other.
    val noMove = mapOf(
        ZERO to ZERO
    )

    // Already adjacent: don't move.
    val nonMoves = mapOf(
        Coordinates(1, 0) to ZERO,
        Coordinates(1, 1) to ZERO,
        Coordinates(0, 1) to ZERO,
        Coordinates(1, -1) to ZERO,
    )

    // Horizontal moves.
    // Swaps on both key and value give the vertical moves.
    val horizontalMoves = mapOf(
        Coordinates(2, 0) to Coordinates(1, 0),
        Coordinates(-2, 0) to Coordinates(-1, 0)
    )

    // Half of the short diagonal moves.
    // Swaps on both key and value give the rest of the short diagonal moves.
    val diagonalMoves = mapOf(
        Coordinates(2, 1) to Coordinates(1, 1),
        Coordinates(2, -1) to Coordinates(1, -1),
        Coordinates(-2, 1) to Coordinates(-1, 1),
        Coordinates(-2, -1) to Coordinates(-1, -1)
    )

    // Half of the large diagonal moves.
    // The negation of key and value give the other half of the large diagonal moves.
    val bigDiagonalMoves = mapOf(
        Coordinates(2, 2) to Coordinates(1, 1),
        Coordinates(2, -2) to Coordinates(1, -1)
    )

    // Construct the full set of possible moves based on the above instructions.
    (noMove +
            nonMoves + nonMoves.map { (c1, c2) -> (-c1) to c2 } +
            horizontalMoves + diagonalMoves +
            (horizontalMoves + diagonalMoves).map { (c1, c2) -> c1.swap() to c2.swap() } +
            bigDiagonalMoves +
            bigDiagonalMoves.map { (c1, c2) -> (-c1) to (-c2) })
}

fun executeMoves(moves: List<Move>, knots: Int): Int {
    tailrec fun aux(coords: List<Coordinates> = List(knots) { ZERO },
                    movesLeft: List<Move> = moves,
                    coordsVisited: Set<Coordinates> = setOf(ZERO)): Set<Coordinates> =
        if (movesLeft.isEmpty())
            coordsVisited
        else {
            // Move the first knot.
            val newHCoord = coords[0] + movesLeft[0]

            // Move the rest using fold to build up a list.
            // We cannot use zipWithNext because that will give us the previous value of the knot ahead
            // and we need the new value of the know ahead.
            val newCoords = coords.drop(1).fold(listOf(newHCoord)) { lst, coord ->
                val prevCoord = lst.last()
                lst + (coord + tailMoves.getValue(prevCoord - coord))
            }
            aux(newCoords, movesLeft.drop(1), coordsVisited + newCoords.last())
        }
    return aux().size
}

fun parseInput(data: String): List<Move> =
    data.split('\n')
        .map { it.split(' ') }
        .map { Pair(Move.valueOf(it[0]), it[1].toInt()) }
        .flatMap { (move, repeat) -> List(repeat) { move } }

fun problem1(data: List<Move>): Int =
    executeMoves(data, 2)

fun problem2(data: List<Move>): Int =
    executeMoves(data, 10)

fun main() {
    val data = parseInput(object {}.javaClass.getResource("/aoc202209.txt")!!.readText())

    println("--- Day 9: Rope Bridge ---")

    // Answer 1: 6311
    println("Part 1: ${problem1(data)}")

    // Answer 2: 2482
    println("Part 2: ${problem2(data)}")

}
