fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val size = it.length / 2
            val a = it.substring(0, size)
            val b = it.substring(size)
            for (ch in a) {
                if (b.contains(ch)) {
                    var num = ch.code
                    if (ch.isLowerCase()) {
                        num -= 96
                    } else {
                        num -= 38
                    }
                    answer += num
                    break
                }
            }

        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        var i = 0
        while (i < input.size) {
            val a = input[i]
            val b = input[i + 1]
            val c  = input[i + 2]
            for (ch in a) {
                if (b.contains(ch) && c.contains(ch)) {
                    var num = ch.code
                    if (ch.isLowerCase()) {
                        num -= 96
                    } else {
                        num -= 38
                    }
                    answer += num
                    break
                }
            }
            i += 3
        }
        return answer
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
