package day13

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import javax.script.ScriptEngineManager
import kotlin.test.assertEquals

class Day13Test {
    companion object {
        private val data = """
            [1,1,3,1,1]
            [1,1,5,1,1]

            [[1],[2,3,4]]
            [[1],4]

            [9]
            [[8,7,6]]

            [[4,4],4,4]
            [[4,4],4,4,4]

            [7,7,7,7]
            [7,7,7]

            []
            [3]

            [[[]]]
            [[]]

            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
        """.trimIndent()

        // I don't know why, but this code with the scripting engine is very, very fussy.
        // We need to redeclare the parser here or it fails with an exception.
        @Suppress("UNCHECKED_CAST")
        private fun parseTestInput(data: String): Input {
            // If we don't have this line, for some reason, we get an exception.
            ScriptEngineManager().engineFactories
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

        val input = parseTestInput(data)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(13, problem1(input))
    }
}