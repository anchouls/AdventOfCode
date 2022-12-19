fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val inp = it.split(" ")
            val A = inp[0]
            val B = inp[1]
            when (B) {
                "X" -> {
                    answer += 1
                    when (A) {
                        "A" -> answer += 3
                        "C" -> answer += 6
                    }
                }
                "Y" -> {
                    answer += 2
                    when (A) {
                        "B" -> answer += 3
                        "A" -> answer += 6
                    }
                }
                "Z" -> {
                    answer += 3
                    when (A) {
                        "C" -> answer += 3
                        "B" -> answer += 6
                    }
                }
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val inp = it.split(" ")
            val A = inp[0]
            val B = inp[1]
            when (B) {
                "X" -> {
                    when (A) {
                        "A" -> answer += 3
                        "B" -> answer += 1
                        "C" -> answer += 2
                    }
                }
                "Y" -> {
                    answer += 3
                    when (A) {
                        "A" -> answer += 1
                        "B" -> answer += 2
                        "C" -> answer += 3
                    }
                }
                "Z" -> {
                    answer += 6
                    when (A) {
                        "A" -> answer += 2
                        "B" -> answer += 3
                        "C" -> answer += 1
                    }
                }
            }
        }
        return answer
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
