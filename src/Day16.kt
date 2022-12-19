import java.lang.Math.max

fun main() {

    data class RV(val index: Int, val rate: Int, val values: List<String>)

    fun part1(input: List<String>): Int {
        val map = mutableMapOf<String, RV>()
        input.forEachIndexed { index, it ->
            val (value, rate, values) = Regex("Valve ([A-Z][A-Z]) has flow rate=(\\d+); tunnel[s]? lead[s]? to valve[s]? ([A-Z, ]+)")
                .matchEntire(it)!!.groupValues.drop(1)
            map[value] = RV(index, rate.toInt(), values.split(", "))
        }
        val res = Array(31) { HashMap<Pair<Long, String>, Int>() }
        res[0][Pair(0, "AA")] = 0
        for (t in 0 until 30) {
            for ((pr, rate) in res[t]) {
                val value = pr.second
                val rv = map[value]!!
                val mask = 1L shl rv.index
                if (rv.rate > 0 && (mask and pr.first) == 0L) {
                    res[t+1][Pair(pr.first or mask, value)] = max(res[t+1][Pair(pr.first or mask, value)] ?: 0, rate + (30 - t - 1) * rv.rate)
                }
                for (v in rv.values) {
                    res[t+1][Pair(pr.first, v)] = max(res[t+1][Pair(pr.first, v)] ?: 0, rate)
                }
            }
        }
        return res[30].map { it.value }.max()
    }

    data class MV(val mask: Long, val value: String, val valueE: String)

    fun part2(input: List<String>): Int {
        val map = mutableMapOf<String, RV>()
        input.forEachIndexed { index, it ->
            val (value, rate, values) = Regex("Valve ([A-Z][A-Z]) has flow rate=(\\d+); tunnel[s]? lead[s]? to valve[s]? ([A-Z, ]+)")
                .matchEntire(it)!!.groupValues.drop(1)
            map[value] = RV(index, rate.toInt(), values.split(", "))
        }
        val res = Array(27) { HashMap<MV, Int>() }
        res[0][MV(0, "AA", "AA")] = 0
        for (t in 0 until 26) {
            for ((pr, rate) in res[t]) {
                val (m, value, valueE) = pr
                val rv = map[value]!!
                val rvE = map[valueE]!!
                val mask = 1L shl rv.index
                val maskE = 1L shl rvE.index
                if (rv.rate > 0 && (mask and m) == 0L) {
                    if (rvE.rate > 0 && (maskE and m) == 0L && rv.index != rvE.index) {
                        res[t+1][MV(m or mask or maskE, value, valueE)] = max(res[t+1][MV(m or mask or maskE, value, valueE)] ?: 0, rate + (26 - t - 1) * (rv.rate + rvE.rate))
                    }
                    for (v in rvE.values) {
                        res[t+1][MV(m or mask, value, v)] = max(res[t+1][MV(m or mask, value, v)] ?: 0, rate + (26 - t - 1) * rv.rate)
                    }
                }
                if (rvE.rate > 0 && (maskE and m) == 0L) {
                    for (v in rv.values) {
                        res[t + 1][MV(m or maskE, v, valueE)] = max(res[t + 1][MV(m, v, valueE)] ?: 0, rate + (26 - t - 1) * rvE.rate)
                    }
                }
                for (v in rv.values) {
                    for (vE in rvE.values) {
                        res[t + 1][MV(m, v, vE)] = max(res[t + 1][MV(m, v, vE)] ?: 0, rate)
                    }
                }
            }
        }
        return res[26].map { it.value }.max()
    }

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
