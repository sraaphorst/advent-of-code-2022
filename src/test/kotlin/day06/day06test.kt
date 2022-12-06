package day06

// By Sebastian Raaphorst, 2022.

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day06Test {
    companion object {
        val examples1 = listOf(
            "bvwbjplbgvbhsrlpgdmjqwftvncz",
            "nppdvjthqldpwncqszvftbrmjlhg",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        )

        val examples2 = listOf(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
            "bvwbjplbgvbhsrlpgdmjqwftvncz",
            "nppdvjthqldpwncqszvftbrmjlhg",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
        )
    }

    @Test
    fun `Problem 1`() {
        assertEquals(examples1.map{ uniqueSubstring(it, 4) }, listOf(5, 6, 10, 11))
    }

    @Test
    fun `Problem 2`() {
        assertEquals(examples2.map{ uniqueSubstring(it, 14) }, listOf(19, 23, 23, 29, 26))
    }
}