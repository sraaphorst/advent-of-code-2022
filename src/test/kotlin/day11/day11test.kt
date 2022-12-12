package day11

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    companion object {
        private val data = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3

            Monkey 1:
              Starting items: 54, 65, 75, 74
              Operation: new = old + 6
              Test: divisible by 19
                If true: throw to monkey 2
                If false: throw to monkey 0

            Monkey 2:
              Starting items: 79, 60, 97
              Operation: new = old * old
              Test: divisible by 13
                If true: throw to monkey 1
                If false: throw to monkey 3

            Monkey 3:
              Starting items: 74
              Operation: new = old + 3
              Test: divisible by 17
                If true: throw to monkey 0
                If false: throw to monkey 1
        """.trimIndent()

        val input = parseInput(data)
    }

    @Test
    fun `lcm list example 1`() {
        // 15 * 4 * 7 = 420
        assertEquals(lcm(listOf(2L, 15L, 4L, 3L, 7L)), 420L)
    }

    @Test
    fun `lcm list example 2`() {
        assertEquals(lcm(listOf(23L, 19L, 13L, 17L)), 96577L)
    }

    @Test
    fun `Problem 1 example`() {
        assertEquals(problem1(input), 10605UL)
    }

    @Test
    fun `Problem 2 example`() {
        assertEquals(problem2(input), 2713310158UL)
    }
}
