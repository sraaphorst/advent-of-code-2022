package day12.mutable

// By Sebastian Raaphorst, 2012.

import kotlin.math.min

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.awaitAll

typealias Coordinates = Pair<Int, Int>
typealias Landscape = Map<Coordinates, Int>
typealias Input = Triple<Landscape, Coordinates, Coordinates>
typealias Distances = MutableMap<Coordinates, Int>

val directions: List<Coordinates> = listOf(
    Coordinates(-1, 0),
    Coordinates(0, 1),
    Coordinates(1, 0),
    Coordinates(0, -1)
)

val Zero = Coordinates(0, 0)

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

operator fun Coordinates.plus(other: Coordinates): Coordinates =
    Coordinates(this.first + other.first, this.second + other.second)

// This immutable implementation is very slow due to all the copying, so we parallelize for
// part 2.
fun findMinPathLength(landscape: Landscape,
                      start: Coordinates,
                      end: Coordinates,
                      bound: Int = Int.MAX_VALUE): Int {
    // Get the grid coordinates.
    val height = landscape.maxOf { it.key.first }
    val width = landscape.maxOf { it.key.second }
    val max = (height + 1) * (width + 1)

    fun coordIsValid(point: Coordinates): Boolean =
        point.first in (0 .. height) && point.second in (0 .. width)

    fun heightIsValid(point: Coordinates, other: Coordinates): Boolean =
        landscape.getValue(point) <= landscape.getValue(other) + 1

    // All neighbours of a node.
    fun neighbourhood(point: Coordinates): Set<Coordinates> =
        directions.map { point + it }
            .filter { coordIsValid(it) && heightIsValid(it, point) }
            .toSet()

    fun smallestUnvisited(distances: Distances, unvisited: Set<Coordinates>): Coordinates =
        unvisited.minBy { distances.getOrDefault(it, max) }

    val initialDistances: Distances = mutableMapOf(start to 0)

    // Djisktra: I was too concerned about part 2 to just use BFS.
    tailrec fun aux(current: Coordinates = start,
                    distances: Distances = initialDistances,
                    unvisited: MutableSet<Coordinates> = landscape.keys.toMutableSet()): Int {
        val dist = distances.getOrDefault(current, max)
        return when {
            end == current -> dist
            dist >= bound -> Int.MAX_VALUE
            else -> {
                val neighbours: Set<Coordinates> = neighbourhood(current)
                neighbours.map { distances[it] = min(dist + 1, distances.getOrDefault(it, max)) }
                unvisited.minusAssign(current)
                val nextNode: Coordinates = smallestUnvisited(distances, unvisited)
                aux(nextNode, distances, unvisited)
            }
        }
    }

    return aux()
}

fun parseInput(board: String): Input {
    val heightMap: Map<Char, Int> =
        ('a'..'z').zip(0 ..25).toMap() +
                mapOf('S' to 0, 'E' to 25)

    tailrec fun aux(landscape: Landscape = emptyMap(),
                    current: Coordinates = Zero,
                    remain: String = board,
                    start: Coordinates = Zero,
                    end: Coordinates = Zero
    ): Input = when {
        remain.isEmpty() -> Input(landscape, start, end)
        remain.first() == '\n' ->
            aux(landscape,
                Coordinates(current.first + 1, 0),
                remain.drop(1),
                start,
                end)
        else -> {
            val ch = remain.first()
            aux(landscape + (current to heightMap.getValue(remain.first())),
                Coordinates(current.first, current.second + 1),
                remain.drop(1),
                if (ch == 'S') current else start,
                if (ch == 'E') current else end)
        }
    }
    return aux()
}

fun problem1(input: Input): Int {
    val (landscape, start, end) = input
    return findMinPathLength(landscape, start, end)
}

// We ignore the start position here, as we want to try every possible start position.
fun problem2(input: Input): Int = runBlocking(Dispatchers.Default) {
    val (landscape, start, end) = input
    val bound = findMinPathLength(landscape, start, end)
    val coords = landscape.filterValues { it == 0 }.keys
    coords.toList().pmap {
        findMinPathLength(landscape, it, end, bound)
    }.min()
}

fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202212.txt")!!.readText())

    println("--- Day 12: Hill Climbing Algorithm ---")

    // Answer 1: 380
    println("Problem 1: ${problem1(input)}")

    // Answer 2:
    println("Problem 2: ${problem2(input)}")
}