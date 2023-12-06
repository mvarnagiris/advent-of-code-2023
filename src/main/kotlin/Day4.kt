import kotlin.math.pow

val NumberRegex = "\\d+".toRegex()

fun day4Part1() = getResourceAsText("day4.txt")
    .lines()
    .sumOf { line ->
        val matches = line.matches()
        2.0.pow(matches - 1).toInt()
    }

fun day4Part2(): String {
    val countMap = mutableMapOf<Int, Int>()

    return getResourceAsText("day4.txt")
        .lines()
        .mapIndexed { index, line ->
            val matches = line.matches()
            val copies = countMap.getOrDefault(index, 1)

            countMap.remove(index)
            repeat(copies) {
                (0..<matches).forEach {
                    countMap.compute(index + 1 + it) { _, value -> (value ?: 1) + 1 }
                }
            }

            copies
        }
        .sum()
        .toString()
}

private fun String.matches(): Int {
    val split = split(": ")
    val numbersSplit = split[1].split(" | ")
    val winning = NumberRegex.findAll(numbersSplit[0]).map { it.value.toInt() }.toSet()
    val mine = NumberRegex.findAll(numbersSplit[1]).map { it.value.toInt() }.toSet()

    return winning.intersect(mine).size
}
