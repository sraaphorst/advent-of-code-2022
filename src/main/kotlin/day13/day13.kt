package day13

// By Sebastian Raaphorst, 2022.

// We're going to do this dynamically by evaling the strings into List[Any].
import javax.script.ScriptEngineManager

typealias Input=List<Pair<List<Any>, List<Any>>>

@Suppress("UNCHECKED_CAST")
fun comparePair(left: Any, right: Any): Int {
    println("Comparing left='$left' and right='$right'")
    return when {
        left == right -> 0

        (left is Int) && (right is Int) -> left - right

        (left is List<*>) && (right is List<*>) -> {
            if (left.isEmpty() && right.isEmpty()) 0
            else if (left.isEmpty()) -1
            else if (right.isEmpty()) 1
            else {
                val compare = comparePair(left[0]!!, right[0]!!)
                if (compare != 0) compare
                else comparePair(left.drop(1), right.drop(1))
            }
        }

        (left is Int) && (right is List<*>) ->
            comparePair(listOf(left), right)

        (left is List<*>) && (right is Int) ->
            comparePair(left, listOf(right))

        else ->
            throw IllegalArgumentException("Illegal arguments: left='$left', right='$right'")
    }
}

@Suppress("UNCHECKED_CAST")
fun parseInput(data: String): Input {
    val engine = ScriptEngineManager().getEngineByExtension("kts")!!
    return data
        .replace("[", "listOf<Any>(")
        .replace("]", ")")
        .replace("\n\n", "\n")
        .split('\n')
        .chunked(2)
        .map { lst ->
            val (expr1, expr2) = lst
            val lst1: List<Any> = engine.eval(expr1) as List<Any>
            val lst2: List<Any> = engine.eval(expr2) as List<Any>
            Pair(lst1, lst2)
        }
}

fun problem1(input: Input): Int =
    input.withIndex().sumOf { (idx, pair) ->
        val (left, right) = pair
        val result = comparePair(left, right)
        if (result <= 0) idx + 1 else 0
    }

fun problem2(input: Input): Int {
    val two = listOf(listOf(2))
    val six = listOf(listOf(6))

}


fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202213.txt")!!.readText())

    println("--- Day 13: Distress Signal ---")

    // Answer: 5503
    println("Problem 1: ${problem1(input)}")
}