package day10

// By Sebastian Raaphorst, 2002.

sealed class Operation
object Noop : Operation()
class AddX(val x: Int): Operation()

fun parseInput(data: String): Iterable<Operation> =
    data.split('\n')
        .map { when {
            it.startsWith("noop") -> Noop
            it.startsWith("addx") -> AddX(it.split(' ')[1].toInt())
            else -> throw IllegalArgumentException("Invalid op: $it")
        } }

// Executes the operations such that the return value lst is such that during cycle i, lst[i-1] is the value.
fun executeOperations(operations: Iterable<Operation>): List<Int> =
    operations.fold(listOf(1)) { currList, op -> when (op) {
        is Noop -> currList + currList.last()
        is AddX -> currList + currList.last() + (currList.last() + op.x)
    } }

fun problem1(values: List<Int>): Int =
    (19 until values.size step 40).sumOf { values[it] * (it + 1) }

fun problem2(values: List<Int>): String =
    values.dropLast(1).withIndex().fold("") { curStr, data ->
        // Drawing pixel idx % 40 on row idx / 6, sprite in positions (value-1, value, value + 1)
        val (idx, value) = data
        val col = idx % 40
        curStr + (if (col in ((value-1)..(value+1))) '#' else '.') + (if (col == 39 && idx < 239) "\n" else "")
    }

fun main() {
    val result = executeOperations(parseInput(object {}.javaClass.getResource("/aoc202210.txt")!!.readText()))

    println("--- Day 10: Cathode-Ray Tube ---")
    // Answer 1: 14220
    println("Part 1: ${problem1(result)}")

    // Answer 2: ZRARLFZU
    println("Part 2:")
    println(problem2(result))
}