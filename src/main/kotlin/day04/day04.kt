package day04

// By Sebastian Raaphorst, 2022.

typealias IntervalPair = Pair<Interval, Interval>
typealias IntervalPairs = Collection<IntervalPair>

data class Interval(val begin: Int, val end: Int) {
    operator fun contains(other: Interval): Boolean =
        begin <= other.begin && end >= other.end

    fun overlap(other: Interval): Boolean =
        begin <= other.end && other.begin <= end
}

fun parseIntervalPair(input: String): IntervalPair {
    val (interval1, interval2) = input.split(',').map {
        val (begin, end) = it.split('-').map(Integer::parseInt)
        Interval(begin, end)
    }
    return Pair(interval1, interval2)
}

fun problem1(data: IntervalPairs): Int =
    data.count { (i1, i2) -> i1 in i2 || i2 in i1 }

fun problem2(data: IntervalPairs): Int =
    data.count { (i1, i2) -> i1.overlap(i2) }


fun main() {
    val data = object {}.javaClass.getResource("/aoc202204.txt")!!.readText()
        .split('\n')
        .map(::parseIntervalPair)

    println("--- Day 4: Camp Cleanup ---")

    // Answer 1: 515
    println("Part 1: ${problem1(data)}")

    // Answer 2: 883
    println("Part 2: ${problem2(data)}")
}