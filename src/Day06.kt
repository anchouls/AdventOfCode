fun main() {
    fun part1(input: List<String>): Int {
        val string = mutableListOf<Char>()
        input.forEach {
            var i = 0
            repeat(4) { _ ->
                string.add(it[i])
                i++
            }
            var a = string.toSet().size
            if (a == 4) return i
            do {
                string.removeFirst()
                string.add(it[i])
                a = string.toSet().size
                i++
            } while (a < 4)
            return i
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val string = mutableListOf<Char>()
        input.forEach {
            var i = 0
            repeat(14) { _ ->
                string.add(it[i])
                i++
            }
            var a = string.toSet().size
            if (a == 14) return i
            do {
                string.removeFirst()
                string.add(it[i])
                a = string.toSet().size
                i++
            } while (a < 14)
            return i
        }
        return 0
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
