import kotlin.math.pow

fun main() {

    fun toDecimal(s: String) : Long {
        var n = s.length - 1
        var ans = 0.0
        s.forEach { ch ->
            when (ch) {
                '1' -> ans += 5.0.pow(n)
                '2' -> ans += 5.0.pow(n) * 2
                '=' -> ans -= 5.0.pow(n) * 2
                '-' -> ans -= 5.0.pow(n)
            }
            n--
        }
        return ans.toLong()
    }

    fun toSNAFU(num: Long) : String {
        val ans = StringBuffer()
        val s = num.toString(5).map { it.digitToInt() }.reversed()
        var r = 0
        s.forEach {
            when (it + r) {
                in 0..2 -> ans.append(it + r)
                3 -> ans.append('=')
                4 -> ans.append('-')
                5 -> ans.append('0')
            }
            r = if (it + r > 2) 1 else 0
        }
        if (r != 0) ans.append(r)
        return ans.toString().reversed()
    }

    fun part1(input: List<String>): String {
        var res = 0L
        input.forEach {
            res += toDecimal(it)
        }
        return toSNAFU(res)
    }

    val input = readInput("Day25")
    println(part1(input))
}
