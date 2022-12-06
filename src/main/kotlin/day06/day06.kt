package day06

// By Sebastian Raaphorst, 2022.

fun uniqueSubstring(data: String, length: Int): Int {
    // Since this is AoC, assume there is always a solution and ignore the case where the remaining is less than length,
    // which would return null in production code.
    tailrec fun aux(remaining: String = data, start: Int = 0): Int = when(remaining.take(length).toSet().size) {
        length -> start
        else -> aux(remaining.drop(1), start + 1)
    }
    return aux() + length
}

fun problem1(data: String) =
    uniqueSubstring(data, 4)

fun problem2(data: String) =
    uniqueSubstring(data, 14)

fun main() {
    val data = object {}.javaClass.getResource("/aoc202206.txt")!!.readText()

    println("--- Day 6: Tuning Trouble ---")

    // Answer 1: 1896
    println("Part 1: ${problem1(data)}")

    // Answer 2: 3452
    println("Part 2: ${problem2(data)}")
}