fun main() {
    fun part1(input: List<String>): Int {
        var maxi = 0
        var i = 0
        val size = input.size
        while(i < size) {
            var j = i
            var current = 0
            while (j < size && input[j] != "") {
                current += input[j].toInt()
                j++
            }
            if (current > maxi) maxi = current
            i = j + 1
        }
        return maxi
    }

    fun part2(input: List<String>): Int {
        var i = 0
        val size = input.size
        val answers = mutableListOf<Int>()
        while(i < size) {
            var j = i
            var current = 0
            while (j < size && input[j] != "") {
                current += input[j].toInt()
                j++
            }
            answers.add(current)
            i = j + 1
        }
        val maxi = answers.sortedDescending().take(3).sum()
        return maxi
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
