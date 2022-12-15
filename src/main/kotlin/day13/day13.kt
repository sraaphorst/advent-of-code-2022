package day13

// By Sebastian Raaphorst, 2022.

// We're going to do this dynamically by evaling the strings into List[Any].
// Note that the resources/META-INF/services/javax.script.ScriptEngineFactory
// file is absolutely essential to dynamically eval Kotlin code.

import javax.script.ScriptEngineManager

typealias Input=List<Pair<List<Any>, List<Any>>>

@Suppress("UNCHECKED_CAST")
fun comparePair(left: List<Any>, right: List<Any>): Boolean {
    println("Comparing left='$left' and right='$right'")
    return when {
        left.isEmpty() -> true
        right.isEmpty() -> false
        (left[0] is Int) && (right[0] is Int) ->
            ((left[0] as Int) < (right[0] as Int)) ||
                    ((left[0] as Int) == (right[0] as Int) && comparePair(left.drop(1), right.drop(1)))

        (left[0] is List<*>) && (right[0] is List<*>) ->
            comparePair(left[0] as List<Any>, right[0] as List<Any>) && comparePair(left.drop(1), right.drop(1))

        (left[0] is Int) && (right[0] is List<*>) ->
            comparePair(listOf(left[0]), right[0] as List<Any>) && comparePair(left.drop(1), right.drop(1))

        (left[0] is List<*>) && (right[0] is Int) ->
            comparePair(left[0] as List<Any>, listOf(right[0])) && comparePair(left.drop(1), right.drop(1))

        else ->
            throw IllegalArgumentException("Illegal arguments: left='$left', right='$right'")
    }
}

@Suppress("UNCHECKED_CAST")
fun parseInput(data: String): Input {
    // If we don't have this line, for some reason, we get an exception.
//    ScriptEngineManager().engineFactories
//    val engine = ScriptEngineManager().getEngineByExtension("kts")!!
    val factory = ScriptEngineManager().getEngineByExtension("kts").factory
    val engine = factory!!.scriptEngine

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
        println("*** COMPARING $left AND $right ****")
        val result = comparePair(left, right)
        println("Result is $result, idx=${idx+1}")
        if (result) idx + 1 else 0
    }


fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202213.txt")!!.readText())

    println("--- Day 13: Distress Signal ---")

    // Answer: 284 TOO LOW
    println("Problem 1: ${problem1(input)}")
}