package day15

// By Sebastian Raaphorst, 20222.

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

const val MaxPos = 4000000L

// Note that again, (x,y) coordinates counterintuitively correspond to (col,row).
typealias Coordinates = Pair<Int, Int>

fun Coordinates.manhattanDistance(other: Coordinates): Int =
    (this.first - other.first).absoluteValue + (this.second - other.second).absoluteValue

data class SensorData(val sensor: Coordinates, val beacon: Coordinates) {
    val manhattanDistance = sensor.manhattanDistance(beacon)
}

// We maintain a sorted list of IntRange and find if there is any overlap between
// the newRange and existing ranges.
operator fun List<IntRange>.plus(newRange: IntRange): List<IntRange> {
    // From documentation of binarySearch:
    // Return the index of the found element, if it is contained in the list within the specified range;
    // otherwise, the inverted insertion point (-insertion point - 1).
    // The insertion point is defined as the index at which the element should be inserted,
    // so that the list (or the specified subrange of list) still remains sorted.
    val lowIdx = binarySearch { it.last.compareTo(newRange.first - 1) }
        .let { -it - 1 }
        .coerceAtLeast(0)
//    val lowIdx2 = binarySearch { it.last.compareTo(newRange.first - 1) }.let { it shr 31 xor it }
    val highIdx = binarySearch(fromIndex = lowIdx) { it.first.compareTo(newRange.last + 1) }
        .let { -it - 1 }
        .coerceAtLeast(0)
//    val highIdx2 = binarySearch(fromIndex = lowIdx) { it.first.compareTo(newRange.last + 1) }.let { it shr 31 xor it }
    val mergedRange = if (lowIdx < highIdx)
        min(this[lowIdx].first, newRange.first)..max(this[highIdx-1].last, newRange.last)
    else
        newRange

    // sublist does not include highIdx so if this is empty, nothing will be removed.
    val cleared = this - subList(lowIdx, highIdx).toSet()
    return cleared.take(lowIdx) + listOf(mergedRange) + cleared.takeLast(cleared.size - lowIdx)
}

fun parseInput(data: String): Iterable<SensorData> =
    data.split('\n')
        .map {
            val sx = it.substring(it.indexOf("x=") + 2, it.indexOf(',')).toInt()
            val sy = it.substring(it.indexOf("y=") + 2, it.indexOf(':')).toInt()
            val bx = it.substring(it.lastIndexOf("x=") + 2, it.lastIndexOf(',')).toInt()
            val by = it.substring(it.lastIndexOf("y=") + 2).toInt()
            SensorData(Coordinates(sx, sy), Coordinates(bx, by))
        }

fun findNumPosNoBeaconInRow(sensorData: Iterable<SensorData>, row: Int = 2000000): Int =
    sensorData.fold(Pair(emptySet<Int>(), emptyList<IntRange>())) { data, sData ->
        val (beaconPosInRow, ranges) = data
        val (sensor, beacon) = sData

        // Determine the distance from the sensor to the row and then how far down the row
        // in each direction the beacon can detect, which gives an IntRange if it is non-negative.
        val distanceToRow = (sensor.second - row).absoluteValue
        val possibleDistance = sData.manhattanDistance - distanceToRow

        val newRanges = if (possibleDistance >= 0)
            ranges + ((sensor.first - possibleDistance)..(sensor.first + possibleDistance))
        else
            ranges

        // Check if the beacon is in this row, and if so, add its column to the set.
        val newBeaconPosInRow = if (beacon.second == row)
            beaconPosInRow + beacon.second
        else
            beaconPosInRow

        Pair(newBeaconPosInRow, newRanges)
    }.let { (beaconPosInRow, intRanges) -> intRanges.sumOf { it.last - it.first + 1} - beaconPosInRow.size }

fun findBeacon(sensorData: Iterable<SensorData>, maxSize: Int): Long = sequence {
//    for (row in 0..maxSize) {
//        val intRanges = sensorData.fold(emptyList<IntRange>()) { ranges, sData ->
//            val (sensor, _) = sData
//
//            // Determine the distance from the sensor to the row and then how far down the row
//            // in each direction the beacon can detect, which gives an IntRange if it is non-negative.
//            val distanceToRow = (sensor.second - row).absoluteValue
//            val possibleDistance = sData.manhattanDistance - distanceToRow
//            val low = (sensor.first - possibleDistance).coerceAtLeast(0)
//            val high = (sensor.first + possibleDistance).coerceAtMost(0)
//            if (low <= high) (ranges + (low..high)) else ranges
//        }
//
//        // Search for space between the ranges for an unoccupied cell.
//        // Note MaxPos is Long so the yields return Long.
//        val high = intRanges.fold(0) { prev, range ->
//            for (col in prev until range.first) yield(MaxPos * col + row)
//            range.last + 1
//        }
//
//        for (col in high..maxSize) yield(MaxPos * col + row)
//    }
    for (row in 0..maxSize) {
        val ranges = sensorData.fold(emptyList<IntRange>()) { ranges, sData ->
            val (sensor, _) = sData
//            print("\nITERATION: $ranges, $sData")
            // Determine the distance from the sensor to the row and then how far down the row
            // in each direction the beacon can detect, which gives an IntRange if it is non-negative.
            val distanceToRow = (sensor.second - row).absoluteValue
//            println("\tdistanceToRow: $distanceToRow")
            val possibleDistance = sData.manhattanDistance - distanceToRow
//            println("\tpossibleDistance: $possibleDistance")
            val low = (sensor.first - possibleDistance).coerceAtLeast(0)
            val high = (sensor.first + possibleDistance).coerceAtMost(maxSize)
            if (low <= high) (ranges + (low..high)) else ranges
        }
//        println("** Row: $row Ranges: $ranges")
        val hi = ranges.fold(0) { prev, range ->
            for (col in prev until range.first) {
//                println("**** $row $col")
                yield(MaxPos * col + row)
            }
            range.last + 1
        }
        for (col in hi..maxSize) {
//            println("****2 $row $col")
            yield(MaxPos * col + row)
        }
    }
}.single()


fun problem1(data: Iterable<SensorData>, row: Int = 2000000) =
    findNumPosNoBeaconInRow(data, row)

fun problem2(data: Iterable<SensorData>, maxSize: Int = MaxPos.toInt()) =
    findBeacon(data, maxSize)

fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202215.txt")!!.readText())

    println("--- Day 15: Beacon Exclusion Zone ---")

    // Answer 1: 5127797
    println("Problem 1: ${problem1(input)}")

    // Answer 2: 12518502636475
    println("Problem 2: ${problem2(input)}")
}