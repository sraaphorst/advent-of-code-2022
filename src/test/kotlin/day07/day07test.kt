package day07

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day07Test {
    companion object {
        private val input = """
            ${'$'} cd /
            ${'$'} ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            ${'$'} cd a
            ${'$'} ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            ${'$'} cd e
            ${'$'} ls
            584 i
            ${'$'} cd ..
            ${'$'} cd ..
            ${'$'} cd d
            ${'$'} ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
        """.trimIndent()

        val data = parseInput(input)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(95437, problem1(data))
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(24933642, problem2(data))
    }
}