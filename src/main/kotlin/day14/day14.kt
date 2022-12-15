package day14

// By Sebastian Raaphorst, 2022.

import kotlin.math.max
import kotlin.math.min

// Remember that in this case, the coordinates are (col, row) and not (row, col).
typealias Coordinates = Pair<Int, Int>
typealias Move = Pair<Int, Int>
typealias Cave = Set<Coordinates>

val SandPosition = Coordinates(500, 0)
val S = Move(0, 1)
val SW = Move(-1, 1)
val SE = Move(1, 1)

fun minmax(c1: Coordinates, c2: Coordinates): Pair<Pair<Int, Int>, Pair<Int, Int>> =
    Pair(Pair(min(c1.first, c2.first), max(c1.first, c2.first)),
         Pair(min(c1.second, c2.second), max(c1.second, c2.second)))

operator fun Coordinates.plus(other: Pair<Int, Int>): Coordinates =
    Coordinates(this.first + other.first, this.second + other.second)

fun sandFall(originalCave: Cave): Cave {
    // Get the lowest height of the cave, where the void begins.
    val voidRow = originalCave.maxOf(Coordinates::second)

    tailrec fun aux(cave: Cave = originalCave, sand: Coordinates = SandPosition): Cave {
        println("Sand at $sand, voidRow=$voidRow")
        return when {
            sand.second >= voidRow -> cave
            ((sand + S) !in cave) -> aux(cave, (sand + S))
            ((sand + SW) !in cave) -> aux(cave, (sand + SW))
            ((sand + SE) !in cave) -> aux(cave, (sand + SE))
            else -> { println()
                if (sand == SandPosition) (cave + sand)
                else aux(cave + sand) }
        }
    }

    return aux()
}

fun parseInput(data: String): Cave =
    data.split('\n')
        .map { line ->
            line.split(" -> ")
                .map {
                    val (x, y) = it.trim().split(',')
                    Coordinates(x.toInt(), y.toInt())
                }.zipWithNext()
        }.flatMap {
            // We now have a line of pairs of consecutive coordinates. Map to Coordinates.
            it.fold(emptySet<Coordinates>()) { wall, coordPair ->
                // Now extrapolate the line segmenent.
                val (coord1, coord2) = coordPair
                val minmax = minmax(coord1, coord2)
                val (colDetails, rowDetails) = minmax
                val (minCol, maxCol) = colDetails
                val (minRow, maxRow) = rowDetails
                wall + (minCol..maxCol)
                    .flatMap { col ->
                        (minRow..maxRow).map { row -> Coordinates(col, row) }
                    }
            }
        }.toSet()

fun problem1(cave: Cave) =
    sandFall(cave).size - cave.size

//fun problem2(cave: Cave) {
//    // Add the floor to the cave.
//    val floorRow =
//}

fun main() {
    val cave = parseInput(object {}.javaClass.getResource("/aoc202214.txt")!!.readText())

    println("--- Day 14: Regolith Reservoir ---")

    // Answer 1: 817
    println("Problem 1: ${problem1(cave)}")
}