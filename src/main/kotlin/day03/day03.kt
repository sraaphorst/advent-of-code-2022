package day03

// By Sebastian Raaphorst, 2022.

typealias RucksackContents = List<String>

// Map of item priorities.
val elements = ('a' .. 'z') + ('A' .. 'Z')
val priorityMap = elements.zip(1..52).toMap()

// Take each rucksack, split it in half, and find the common element in each half.
// Turn it into a priority and then sum over all the rucksacks.
fun problem1(rucksacks: RucksackContents): Int =
    rucksacks.sumOf {
        val halves = it.chunked(it.length / 2).map(String::toSet)
        priorityMap.getValue(halves[0].intersect(halves[1]).first())
    }

// Take consecutive triples of rucksacks and find their common element.
// Convert into a priority and then sum.
fun problem2(rucksacks: RucksackContents): Int =
    rucksacks.map(String::toSet)
        .chunked(3)
        .sumOf { chunks -> priorityMap.getValue(chunks[0].intersect(chunks[1]).intersect(chunks[2]).first()) }

fun main() {
    val data = object {}.javaClass.getResource("/aoc202203.txt")!!.readText().split('\n')

    println("--- Day 3: Rucksack Reorganization ---")

    // Answer 1: 8252
    println("Part 1: ${problem1(data)}")

    // Answer 2: 2828
    println("Part 2: ${problem2(data)}")
}