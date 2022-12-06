package day01

// By Sebastian Raaphorst, 2022.

typealias Calories = Int
typealias ElfData = Iterable<Calories>

fun parseInput(data: String): ElfData =
    data.split("\n\n")
        .map { it.split('\n').map(Integer::parseInt).sum() }

fun problem1(elfData: ElfData): Calories =
    elfData.max()

fun problem2(elfData: ElfData): Calories =
    elfData.sortedDescending().take(3).sum()

fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202201.txt")!!.readText())

    println("--- Day 1: Calorie Counting ---")

    // Answer 1: 70764
    println("Part 1: ${problem1(input)}")

    // Answer 2: 203905
    println("Part 2: ${problem2(input)}")
}
