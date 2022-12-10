package day08

import kotlin.math.max

// By Sebastian Raaphorst, 2022.

typealias TreeHeight = Int
typealias Line = List<TreeHeight>
typealias Grid = Map<Int, Map<Int, TreeHeight>>
typealias Coordinates = Pair<Int, Int>

fun parseInput(data: String): Grid =
    data.split('\n')
        .withIndex()
        .associate { (row, line) ->
            row to line.toCharArray()
                .map(Char::digitToInt)
                .withIndex()
                .associate { (col, entry) -> col to entry } }

fun extractRow(grid: Grid, rowIdx: Int): Line =
    grid.getValue(rowIdx)
        .toList()
        .sortedBy { it.first }
        .map { it.second }

fun extractCol(grid: Grid, colIdx: Int): Line =
    grid.toList()
        .map { it.first to it.second.getValue(colIdx) }
        .sortedBy { it.first }
        .map{ it.second }

/**
 * Given a line of trees, the line visibility is the number of trees of increasing height that we can
 * see looking down the line.
 */
fun lineVisibility(line: Line): Set<Int> =
    line.withIndex()
        .fold(Pair<TreeHeight, Set<Int>>(-1, emptySet())) { acc, curr ->
            val (maxHeight, indices) = acc
            val (index, height) = curr
            val newHeight = max(maxHeight, height)
            val newIndices = if (height > maxHeight) (indices + index) else indices
            Pair(newHeight, newIndices)
    }.second

fun rowVisibilityForwards(grid: Grid, rowIdx: Int): Set<Coordinates> =
    lineVisibility(extractRow(grid, rowIdx)).map { rowIdx to it }.toSet()

fun rowVisibilityBackwards(grid: Grid, rowIdx: Int): Set<Coordinates> =
    lineVisibility(extractRow(grid, rowIdx).reversed()).map { rowIdx to (grid.getValue(0).size - it - 1)}.toSet()

fun colVisibilityForwards(grid: Grid, colIdx: Int): Set<Coordinates> =
    lineVisibility(extractCol(grid, colIdx)).map { it to colIdx }.toSet()

fun colVisibilityBackwards(grid: Grid, colIdx: Int): Set<Coordinates> =
    lineVisibility(extractCol(grid, colIdx).reversed()).map { (grid.size - it - 1) to colIdx }.toSet()

/**
 * Given a line segment, calculate its scenic score contribution.
 * If the line is empty, this is a tree on the border, and thus, it has a scenic score contribution of 0.
 * Otherwise, we assume that we are counting the scenic score component from the first tree on the line.
 * Count the number of trees down the line that are shorter than the tree under consideration.
 * If we hit the edge, that is the number of visible trees.
 * If we stop before the edge, then we hit a tree taller than the tree under consideration, and can see it, so add 1.
 */
fun scenicScoreComponent(line: Line): Int {
    if (line.isEmpty()) return 0
    val height = line[0]
    val trees = line.drop(1).takeWhile { it < height }
    return if (trees.size == line.size - 1) trees.size else trees.size + 1
}

fun scenicScore(grid: Grid, coords: Coordinates): Int {
    val rowCount = grid.size
    val colCount = grid.getValue(0).size

    val (rowIdx, colIdx) = coords
    val row = extractRow(grid, rowIdx)
    val col = extractCol(grid, colIdx)

    val rightVisibility = scenicScoreComponent(row.drop(colIdx))
    val leftVisibility = scenicScoreComponent(row.dropLast(colCount - colIdx - 1).reversed())
    val downVisibility = scenicScoreComponent(col.drop(rowIdx))
    val upVisibility = scenicScoreComponent(col.dropLast(rowCount - rowIdx - 1).reversed())
    return rightVisibility * leftVisibility * downVisibility * upVisibility
}

/**
 * Get the sets of coordinates for all of the:
 * 1. Row visibilities looking from the left and looking from the right
 * 2. Column visibilities looking from the left and looking from the right
 * and take their set union to remove duplicates.
 * The number of remaining coordinates is the total visibility.
 */
fun problem1(data: Grid): Int =
    ((0 until data.size)
        .map{ rowVisibilityForwards(data, it) + rowVisibilityBackwards(data, it) } +
     (0 until data.getValue(0).size)
         .map { colVisibilityForwards(data, it) + colVisibilityBackwards(data, it) }
    ).fold(emptySet<Coordinates>()){ curr, new -> curr + new }.size

fun problem2(data: Grid): Int =
    (0 until data.size).maxOf { rowIdx ->
        (0 until data.getValue(0).size).maxOf { colIdx ->
            scenicScore(data, Coordinates(rowIdx, colIdx))
        }
    }

fun main() {
    val data = parseInput(object {}.javaClass.getResource("/aoc202208.txt")!!.readText())

    println("--- Day 8: Treetop Tree House ---")

    // Answer 1: 1690
    println("Part 1: ${problem1(data)}")

    // Answer 2: 535680
    println("Part 2: ${problem2(data)}")
}