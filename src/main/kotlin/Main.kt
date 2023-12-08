import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
//    measured { print("Day 1 Part 1 = ${day1Part1()}") }
//    measured { print("Day 1 Part 2 = ${day1Part2()}") }
//    measured { print("Day 2 Part 1 = ${day2Part1()}") }
//    measured { print("Day 2 Part 2 = ${day2Part2()}") }
//    measured { print("Day 3 Part 1 = ${day3Part1()}") }
//    measured { print("Day 3 Part 2 = ${day3Part2()}") }
//    measured { print("Day 4 Part 1 = ${day4Part1()}") }
//    measured { print("Day 4 Part 2 = ${day4Part2()}") }
//    measured { print("Day 5 Part 1 = ${day5Part1()}") }
//    measured { print("Day 5 Part 2 = ${day5Part2()}") }
    measured { print("Day 6 Part 1 = ${day6Part1()}") }
    measured { print("Day 6 Part 2 = ${day6Part2()}") }
}

private fun measured(code: () -> Unit) {
    measureTimeMillis { code() }.also { print(" | In: ${it}ms\n") }
}
