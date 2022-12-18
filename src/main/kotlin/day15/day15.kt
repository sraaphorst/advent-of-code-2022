package day15

// By Sebastian Raaphorst, 20222.

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

const val MinPos = 0
const val MaxPos = 4000000

// Note that again, (x,y) coordinates counterintuitively correspond to (col,row).
typealias Coordinates = Pair<Int, Int>

fun Coordinates.manhattanDistance(other: Coordinates): Int =
    (this.first - other.first).absoluteValue + (this.second - other.second).absoluteValue

fun Coordinates.tuningFrequency(): Int =
    this.first * MaxPos + this.second

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
    val lowIdx = binarySearch { it.last.compareTo(newRange.first - 1) }.let { -it - 1 }
    val highIdx = binarySearch(fromIndex = lowIdx) { it.first.compareTo(newRange.last + 1) }.let { -it - 1 }
    val lowIdx2 = binarySearch { it.last.compareTo(newRange.first - 1) }.let { it shr 31 xor it }
    val highIdx2 = binarySearch(fromIndex = lowIdx) { it.first.compareTo(newRange.last + 1) }.let { it shr 31 xor it }
    println("ADDING: $newRange into $this, low1=$lowIdx low2=$lowIdx2, high1=$highIdx, high2=$highIdx2")
    val mergedRange = if (lowIdx < highIdx)
        min(this[lowIdx].first, newRange.first)..max(this[highIdx-1].last, newRange.last)
    else
        newRange

    // sublist does not include highIdx.
    val cleared = this - subList(lowIdx, highIdx).toSet()
    val newList = cleared.take(lowIdx) + listOf(mergedRange) + cleared.takeLast(cleared.size - lowIdx)
    println("New list is: $newList")
    return newList
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

fun findNumPosNoBeaconInRow(sensorData: Iterable<SensorData>, row: Int = 2000000): Int {
    val (beaconsInRow, intRanges) = sensorData.fold(Pair(emptySet<Int>(), emptyList<IntRange>())) { data, sensorData ->
        val (beaconsInRow, ranges) = data
        val (sensor, beacon) = sensorData

        // Determine the distance from the sensor to the row and then how far down the row
        // in each direction the beacon can detect, which gives an IntRange if it is non-negative.
        val distanceToRow = (sensor.second - row).absoluteValue
        val possibleDistance = sensorData.manhattanDistance - distanceToRow

        val newRanges = if (possibleDistance >= 0)
            ranges + ((sensor.first - possibleDistance)..(sensor.first + possibleDistance))
        else
            ranges

        // Check if the beacon is in this row, and if so, add its column to the set.
        val newBeaconsInRow = if (beacon.second == row)
            beaconsInRow + beacon.second
        else
            beaconsInRow

        Pair(newBeaconsInRow, newRanges)
    }
    return intRanges.sumOf { it.last - it.first + 1} - beaconsInRow.size
}


fun findBeacon(minPos: Int, maxPos: Int, sensorData: Iterable<SensorData>): Int =
    (minPos..maxPos).flatMap { col -> (minPos..maxPos).map { row -> Coordinates(col, row) } }
        .first { pos -> sensorData.all { pos.manhattanDistance(it.sensor) > it.manhattanDistance } }
        .tuningFrequency()
//
//fun findBeacon(minPos: Int, maxPos: Int, sensorData: Iterable<SensorData>): Int =
//    (minPos..maxPos).flatMap { col -> (minPos..maxPos).map { row -> Coordinates(col, row) } }
//        .filter { pos ->
//            println("*** Checking $pos:")
//            sensorData.all {
//                println("\tSensor ${it.sensor} to beacon ${it.beacon} = ${it.manhattanDistance}, pos distance=${pos.manhattanDistance(it.sensor)}")
//                pos.manhattanDistance(it.sensor) > it.manhattanDistance
//            }
//        }.also {
//            println("*** Points left: $it")
//        }.first()
//        .tuningFrequency()

fun problem1(data: Iterable<SensorData>, row: Int = 2000000) =
    findNumPosNoBeaconInRow(data, row)

fun problem2(data: Iterable<SensorData>, minPos: Int = MinPos, maxPos: Int = MaxPos) =
    findBeacon(minPos, maxPos, data)

fun main() {
    val input = parseInput(object {}.javaClass.getResource("/aoc202215.txt")!!.readText())

    println("--- Day 15: Beacon Exclusion Zone ---")

    // Answer 1: 5127797
    println("Problem 1: ${problem1(input)}")

    // Answer 2:
//    println("Problem 2: ${problem2(input)}")
}