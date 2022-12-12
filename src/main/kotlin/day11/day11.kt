package day11

// By Sebastian Raaphorst, 2022.

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

typealias Items = List<Long>
typealias Input = Pair<List<Monkey>, List<Items>>

fun lcm(a: Long, b: Long): Long {
    if (a == 0L || b == 0L)
        return 0L

    val high = max(a.absoluteValue, b.absoluteValue)
    val low = min(a.absoluteValue, b.absoluteValue)

    fun aux(value: Long = high): Long = when (value % low) {
        0L -> value
        else -> aux(value + high)
    }

    return aux()
}

fun lcm(list: List<Long>): Long =
    list.fold(1L) { a, b -> lcm(a, b) }

// The trick here is to take the worry level modulo the lcm of all the monkey divisibility values
// when inspecting to keep the value low without affecting the modulo checks to see who to pass
// the item to. This calculation can be done because it will produce the same modulo number
// when the inspection is done and divided by the worry level. Thus, we need to pass the lcm
// to the inspect method for reduction on the worry level before we add or multiply.
class Monkey(val inspect: (Long, Long) -> Long, val divisibility: Long, val trueMonkey: Int, val falseMonkey: Int) {
    companion object {
        fun parseMonkey(input: List<String>): Pair<Monkey, Items> {
            // We don't care about the monkey number as these will be added to a list, so ignore the first line.
            val startingItems = input[1].trim()
                .replace("Starting items: ", "")
                .split(',')
                .map(String::trim)
                .map(String::toLong)

            // Determine the operation: always of the form new = old [*|+] [old|number]
            val (operator, operand) = input[2].trim()
                .replace("Operation: new = old ", "")
                .split(' ')

            val inspect: (Long, Long) -> Long = { old, monkeyLCM ->
                val rhs = if (operand == "old") old else operand.toLong()
                when (operator) {
                    "*" -> (old % monkeyLCM) * rhs
                    "+" -> (old % monkeyLCM) + rhs
                    else -> throw IllegalArgumentException("Operation: ${input[2]}")
                }
            }

            val divisibility = input[3].trim().replace("Test: divisible by ", "").toLong()
            val trueMonkey = input[4].trim().replace("If true: throw to monkey ", "").toInt()
            val falseMonkey = input[5].trim().replace("If false: throw to monkey ", "").toInt()
            return Pair(Monkey(inspect, divisibility, trueMonkey, falseMonkey), startingItems)
        }
    }
}

fun parseInput(data: String): Input =
    data.split('\n')
        .chunked(7)
        .map { Monkey.parseMonkey(it)  }
        .unzip()

fun executeMonkeyInTheMiddle(inputMonkeys: List<Monkey>,
                             inputItems: List<Items>,
                             rounds: Int,
                             worryFactor: Long): List<Int> {
    assert(rounds >= 1)

    // To keep numbers small, calculate the lcm of the monkey divisibility.
    val interestLCM = lcm(inputMonkeys.map(Monkey::divisibility))

    // Execute one monkey over one round.
    tailrec fun aux(monkeys: List<Monkey> = inputMonkeys,
                    items: List<Items> = inputItems,
                    monkey: Int = 0,
                    round: Int = 0,
                    inspections: List<Int> = List(inputMonkeys.size){0}): List<Int> = when {
        round == rounds -> inspections
        monkey == monkeys.size -> aux(monkeys, items, 0, round + 1, inspections)
        items[monkey].isEmpty() -> aux(monkeys, items, monkey + 1, round, inspections)
        else -> {
            val m = monkeys[monkey]
            val worryLevel = m.inspect(items[monkey].first(), interestLCM) / worryFactor
            val recipient = if (worryLevel % m.divisibility == 0L) m.trueMonkey else m.falseMonkey
            val newItems = items.withIndex().map { (idx, oldItems) -> when (idx) {
                monkey -> oldItems.drop(1)
                recipient -> oldItems + worryLevel
                else -> oldItems
            } }
            val newInspections = inspections.withIndex().map { (idx, oldInspections) -> when (idx) {
                monkey -> oldInspections + 1
                else -> oldInspections
            } }
            aux(monkeys, newItems, monkey, round, newInspections)
        }
    }

    return aux()
}

fun problem(input: Input, rounds: Int, worryFactor: Long): ULong =
    executeMonkeyInTheMiddle(input.first, input.second, rounds, worryFactor)
        .sorted()
        .takeLast(2)
        .map(Int::toULong)
        .reduce { a, b -> a * b }

fun problem1(input: Input): ULong =
    problem(input,20, 3L)

fun problem2(input: Input): ULong =
    problem(input, 10000, 1L)

fun main() {
    val data = parseInput(object {}.javaClass.getResource("/aoc202211.txt")!!.readText())

    println("--- Day 11: Monkey in the Middle ---")

    // Answer 1: 99840
    println("Problem 1: ${problem1(data)}")

    // Answer 2: 20683044837
    println("Problem 2: ${problem2(data)}")
}