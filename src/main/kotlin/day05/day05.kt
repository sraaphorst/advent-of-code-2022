package day05

// By Sebastian Raaphorst, 2022.

typealias CrateStack = Iterable<Char>
typealias CrateStacks = List<CrateStack>
typealias Moves = Iterable<Move>
typealias Data = Pair<CrateConfiguration, Moves>

class CrateConfiguration(private val stacks: CrateStacks) {
    // In problem 1, the crane reverses the crates.
    // In problem 2, the crane does not reverse the crates.
    fun executeMove(move: Move, reverse: Boolean): CrateConfiguration =
        CrateConfiguration(stacks.withIndex().map { (idx, crate) ->
            when (idx) {
                move.from -> crate.drop(move.num)
                move.to -> fmap.getValue(reverse)(stacks[move.from].take(move.num)) + crate
                else -> crate
            }
        })

    fun tops(): String =
        stacks.map(CrateStack::first).joinToString("")

    companion object {
        // To differentiate between problems 1 and 2:
        // In problem 1, we reverse the order of the crates as they are moved one at a time.
        // In problem 2, the crates are moved all at once, so they maintain their order.
        val fmap: Map<Boolean, (CrateStack) -> CrateStack> = mapOf(
            true to CrateStack::reversed,
            false to { it }
        )
    }
}

class Move(val num: Int, val from: Int, val to: Int) {
    companion object {
        fun parse(data: String): Move {
            // Crates are indexed by 1 so subtract.
            // Data is predictably well-structured so use split instead of regex.
            val elems = data.split(" ")
            return Move(elems[1].toInt(), elems[3].toInt() - 1, elems[5].toInt() - 1)
        }
    }
}

fun parseInput(data: String): Data {
    // Input for this problem is very irritatingly structured.
    val lines = data.split('\n')

    val numCrates = lines
        .asSequence()
        .dropWhile{it.trim().first() == '['}
        .first()
        .trim()
        .split("   ")
        .last()
        .toInt()

    val crateCols = lines
        .takeWhile { it.trim().first() == '[' }
        .map { line -> (0 until numCrates).map { line[it * 4 + 1] } }

    // Transpose crateCols so that we have lists of the contents of each crate stack.
    val crateStacks = (0 until numCrates).map { i ->
        crateCols.indices
            .map { j -> crateCols[j][i] }
            .dropWhile { it == ' ' }
    }

    val moves = lines.drop(crateCols.size + 2).map(Move::parse)

    return Pair(CrateConfiguration(crateStacks), moves)
}

fun problem1(data: Data): String =
    data.second.fold(data.first) { crates, move -> crates.executeMove(move, true) }.tops()

fun problem2(data: Data): String =
    data.second.fold(data.first) { crates, move -> crates.executeMove(move, false) }.tops()


fun main() {
    val data = parseInput(object {}.javaClass.getResource("/aoc202205.txt")!!.readText())

    println("--- Day 5: Supply Stacks ---")

    // Answer 1: MQTPGLLDN
    println("Part 1: ${problem1(data)}")

    // Answer 2: LVZPSTTCZ
    println("Part 2: ${problem2(data)}")
}