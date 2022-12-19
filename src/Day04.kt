fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val sp = it.split(",")
            val ab = sp[0].split("-")
            val cd = sp[1].split("-")
            val a = ab[0].toInt()
            val b = ab[1].toInt()
            val c = cd[0].toInt()
            val d = cd[1].toInt()
            if ((a <= c && b >= d) || (c <= a && d >= b)) {
                answer++
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val sp = it.split(",")
            val ab = sp[0].split("-")
            val cd = sp[1].split("-")
            val a = ab[0].toInt()
            val b = ab[1].toInt()
            val c = cd[0].toInt()
            val d = cd[1].toInt()
            if ((c in a..b) || (a in c..d) || (b in c..d) || (d in a..b)) {
                answer++
            }
        }
        return answer
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
