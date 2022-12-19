import java.lang.Integer.min
import java.lang.Math.max

fun main() {

    fun readInput(input: List<String>): List<CharArray> {
        val r = List(200) { CharArray(500) { '.' } }
        input.forEach {
            val spl = it.split(" -> ")
            for ((i, s) in spl.withIndex()) {
                if (i != 0) {
                    val xy1 = s.split(",")
                    val x1 = xy1[0].toInt() - 300
                    val y1 = xy1[1].toInt()
                    val xy2 = spl[i - 1].split(",")
                    val x2 = xy2[0].toInt() - 300
                    val y2 = xy2[1].toInt()
                    if (x1 == x2) {
                        for (j in min(y1, y2)..max(y1, y2)) {
                            r[j][x1] = '#'
                        }
                    } else {
                        for (j in min(x1, x2)..max(x1, x2)) {
                            r[y1][j] = '#'
                        }
                    }
                }
            }
        }
        return r
    }

    fun part1(input: List<String>): Int {
        val r = readInput(input)
        var answer = 0
        while (true) {
            var x = 200
            var y = 0
            while (true) {
                if (y > 180) break
                if (r[y + 1][x] == '.') {
                    y++
                } else if (r[y + 1][x - 1] == '.') {
                    y++
                    x--
                } else if (r[y + 1][x + 1] == '.') {
                    y++
                    x++
                } else break
            }
            answer++
            r[y][x] = 'o'
            if (y > 180) break
        }
        return answer - 1
    }

    fun check(x: Int, y: Int, r: List<CharArray>): Boolean {
        if (x !in 0..499 || y !in 0..199) return false
        return r[y][x] == '.'
    }

    fun part2(input: List<String>): Int {
        val r = readInput(input)
        for (i in 0..499) {
            r[183][i] = '#'
        }
        var answer = 0
        while (true) {
            var x = 200
            var y = 0
            if (!check(x, y, r)) break
            while (true) {
                if (check(x, y + 1, r)) {
                    y++
                } else if (check(x - 1, y + 1, r)) {
                    y++
                    x--
                } else if (check(x + 1, y + 1, r)) {
                    y++
                    x++
                } else break
            }
            answer++
            r[y][x] = 'o'
        }
        return answer
    }

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
