fun main() {
    fun part1(input: List<String>): Int {
        var x = 1
        var cycle = 0
        val cycles = listOf(20, 60, 100, 140, 180, 220)
        var answer = 0
        input.forEach {
            val inp = it.split(" ")
            when (inp[0]) {
                "noop" -> {
                    cycle++
                    if (cycle in cycles) {
                        answer += cycle * x
                    }
                }
                "addx" -> {
                    val num = inp[1].toInt()
                    cycle++
                    if (cycle in cycles) {
                        answer += cycle * x
                    }
                    cycle++
                    if (cycle in cycles) {
                        answer += cycle * x
                    }
                    x += num
                }
            }
        }
        return answer
    }

    fun part2(input: List<String>): String {
        var x = 1
        var cycle = 0
        val answer = StringBuffer()
        input.forEach {
            val inp = it.split(" ")
            when (inp[0]) {
                "noop" -> {
                    if (cycle <= x+1 && cycle >= x-1) {
                        answer.append('#')
                    } else {
                        answer.append('.')
                    }
                    cycle++
                    if (cycle == 40) cycle = 0
                }
                "addx" -> {
                    val num = inp[1].toInt()
                    if (cycle <= x+1 && cycle >= x-1) {
                        answer.append('#')
                    } else {
                        answer.append('.')
                    }
                    cycle++
                    if (cycle == 40) cycle = 0
                    if (cycle <= x+1 && cycle >= x-1) {
                        answer.append('#')
                    } else {
                        answer.append('.')
                    }
                    cycle++
                    if (cycle == 40) cycle = 0
                    x += num
                }
            }
        }
        return answer.toString()
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
