package day07

// By Sebastian Raaphorst, 2022.

typealias Lines = List<String>

abstract class Entry(val name: String, val size: Int) {
    abstract fun show(indent: Int = 0)
}

class Directory(name: String, val contents: List<Entry>): Entry(name, contents.map(Entry::size).sum()) {
    override fun show(indent: Int) {
        println("${"  ".repeat(indent)}- $name (dir, size=$size)")
        contents.forEach { it.show(indent + 1) }
    }
}

class File(name: String, size: Int): Entry(name, size) {
    override fun show(indent: Int) {
        println("${"  ".repeat(indent)}- $name (file, size=$size)")
    }
}

fun parseInput(data: String): Entry {
    // Preprocess the data for easier handling.
    // 1. Combine "$ cd dir" + "$ ls" into "CDLS dir".
    // 2. Make "$ cd .." into "BACK".
    // 3. Remove the "dir dirname" lines as they will be redundant. We always CDLS into the directories so
    //    we don't need to know what they are named in advance.
    val data1 = Regex("""\p{Sc}\s*cd\s+([^.\s]+)\n\p{Sc}\s+ls""").replace(data, "CDLS $1")
    val data2 = Regex("""\p{Sc}\s*cd\s+\.\.""").replace(data1, "BACK")
    val data3 = Regex("""dir\s+\S+\n""").replace(data2, "")

    fun parseFile(line: String): File {
        val (size, name) = line.split(' ')
        return File(name, size.toInt())
    }

    // Parsing:
    // 1. Stopping condition: One entry and it is named "/".
    // 2. BACK: Done processing a directory. Return remaining lines and entries of directory.
    // 3. FILE: Add to entries, drop the line, and recurse.
    // 4. CDLS: Starting a new directory.
    //          Start new call to aux with following lines to get directory entries and remaining lines after parsing.
    //          Add new directory to entries and recurse with remaining lines from call above to aux.
    fun aux(lines: Lines = data3.split('\n'),
            entries: List<Entry> = emptyList()): Pair<Lines, List<Entry>> = when {
        // 1. We have processed all the lines and have backtracked down to the base directory.
        lines.isEmpty() && entries.size == 1 && entries.first().name == "/" -> Pair(lines, entries)

        // 2. We reached a BACK or the end of the lines (which is an implicit BACK).
        lines.isEmpty() || lines.first() == "BACK" -> Pair(lines.drop(1), entries)

        // 3. We have hit a file. Add it to the list of entries and continue processing.
        lines.first().first().isDigit() -> aux(lines.drop(1), entries + listOf(parseFile(lines.first())))

        // 4. We reached a CDLS, which indicates the start of a directory.
        lines.first().startsWith("CDLS") -> {
            val dirName = lines.first().split(' ')[1]
            val (remainingLines, dirContents) = aux(lines.drop(1))
            aux(remainingLines, entries + listOf(Directory(dirName, dirContents)))
        }

        // This should never happen.
        else -> throw IllegalArgumentException("Illegal termination")
    }

    return aux().second[0]
}

fun extractDirectories(entry: Entry): List<Entry> {
    fun aux(entries: List<Entry> = listOf(entry), directories: List<Entry> = emptyList()): List<Entry> = when {
        entries.isEmpty() -> directories
        entries.first() is Directory ->
            aux(entries.drop(1) + (entries.first() as Directory).contents,
            directories + listOf(entries.first()))
        else -> aux(entries.drop(1), directories)
    }
    return aux()
}

fun problem1(data: Entry): Int =
    extractDirectories(data).filter { it.size <  100000 }.map(Entry::size).sum()

fun problem2(data: Entry): Int {
    val unusedSpace = 70000000 - data.size
    val spaceNeeded = 30000000 - unusedSpace
    return extractDirectories(data).filter { it.size >= spaceNeeded }.map(Entry::size).min()
}

fun main() {
    val data = parseInput(object {}.javaClass.getResource("/aoc202207.txt")!!.readText())

    println("--- Day 7: No Space Left On Device ---")

    // Answer 1: 1447046
    println("Part 1: ${problem1(data)}")

    // Answer 2: 578710
    println("Part 2: ${problem2(data)}")
}