fun day6Part1() = getResourceAsText("day6.txt")
    .let { text ->
        val lines = text.lines()
        val times = NumberRegex.findAll(lines[0]).map { it.value.toLong() }
        val distances = NumberRegex.findAll(lines[1]).map { it.value.toLong() }

        times.zip(distances) { time, distance -> Race(time, distance) }
    }
    .map { race ->
        (1..<race.time).count { holdTime -> holdTimeBeatsDistanceRecord(holdTime, race.time, race.distance) }
    }
    .reduce { acc, value -> acc * value }
    .toString()

fun day6Part2() = getResourceAsText("day6.txt")
    .let { text ->
        val lines = text.lines()
        val time = NumberRegex.findAll(lines[0]).map { it.value }.reduce { acc, value -> acc + value }.toLong()
        val distance = NumberRegex.findAll(lines[1]).map { it.value }.reduce { acc, value -> acc + value }.toLong()

        (1..<time).count { holdTime -> holdTimeBeatsDistanceRecord(holdTime, time, distance) }
    }
    .toString()

private fun holdTimeBeatsDistanceRecord(holdTime: Long, raceTime: Long, raceDistanceRecord: Long): Boolean {
    val remainingTime = raceTime - holdTime
    val boatDistance = remainingTime * holdTime
    return boatDistance > raceDistanceRecord
}

private class Race(
    val time: Long,
    val distance: Long,
)
