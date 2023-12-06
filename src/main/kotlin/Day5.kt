fun day5Part1() = getResourceAsText("day5.txt")
    .split("$NewLine$NewLine")
    .let { split ->
        val seeds = split[0]
            .split(": ")[1]
            .split(" ")
            .map { it.toLong()..it.toLong() }

        val maps = split.maps()
        seeds.minLocation(maps)
    }


fun day5Part2() = getResourceAsText("day5.txt")
    .split("$NewLine$NewLine")
    .let { split ->
        val seeds = split[0]
            .split(": ")[1]
            .split(" ")
            .map { it.toLong() }
            .let { rawSeedRanges ->
                rawSeedRanges.chunked(2).map { range -> range[0]..<(range[0] + range[1]) }
            }

        val maps = split.maps()
        seeds.minLocation(maps)
    }

private fun List<String>.maps() = drop(1).map { mapText ->
    val mappings = mapText.lines().drop(1).map { mapLine ->
        val mapLineSplit = mapLine.split(" ")
        val sourceStart = mapLineSplit[1].toLong()
        val destinationStart = mapLineSplit[0].toLong()
        val length = mapLineSplit[2].toLong()
        Mapping(
            source = sourceStart..<(sourceStart + length),
            destination = destinationStart..<(destinationStart + length),
        )
    }
    Map(mappings)
}

private fun List<LongRange>.minLocation(maps: List<Map>): String {
    return map { seedRange -> maps.fold(listOf(seedRange)) { seedRanges, map -> map.mapToDestinations(seedRanges) } }
        .flatten()
        .minOf { it.first }
        .toString()
}

private class Map(
    val mappings: List<Mapping>
) {
    fun mapToDestinations(sourceRanges: List<LongRange>): List<LongRange> {
        return sourceRanges.map { range ->
            val mappedRanges = mappings.mapNotNull { mapping ->
                val sourceRangeSlice = mapping.overlapSource(range)
                if (sourceRangeSlice.isEmpty()) {
                    null
                } else {
                    sourceRangeSlice to mapping
                }
            }
            val unmappedRanges = range.toUnmappedRanges(mappedRanges.map { it.first })

            unmappedRanges + mappedRanges.map { it.second.toDestination(it.first) }
        }.flatten()
    }
}

private fun LongRange.toUnmappedRanges(ranges: List<LongRange>): List<LongRange> {
    var currentFirst = first
    return ranges
        .sortedBy { it.first }
        .map { usedRange ->
            val result = if (currentFirst < usedRange.first) {
                currentFirst..<usedRange.first
            } else {
                null
            }
            currentFirst = usedRange.last + 1
            result
        }
        .plus(if (currentFirst <= last) currentFirst..last else null)
        .filterNotNull()
}

private class Mapping(
    val source: LongRange,
    val destination: LongRange,
) {
    fun overlapSource(range: LongRange) = source.overlap(range)
    fun toDestination(range: LongRange): LongRange {
        val offset = range.first - source.first
        return (destination.first + offset)..(destination.first + offset + range.count())
    }
}

private fun LongRange.overlap(other: LongRange): LongRange {
    if (other.last < first || other.first > last) {
        return LongRange.EMPTY
    }

    return other.first.coerceIn(this)..other.last.coerceIn(this)
}
