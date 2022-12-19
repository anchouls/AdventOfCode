import kotlin.math.abs

fun main() {

    data class SB(val sx: Int, val sy: Int, val bx: Int, val by: Int)

    data class Segment(val a: Int, val type: Boolean)

    fun part1(input: List<String>): Int {
        val sbList = mutableListOf<SB>()
        input.forEach {
            val (sx, sy, bx, by) =
                Regex("Sensor at x=([-0-9]+), y=([-0-9]+): closest beacon is at x=([-0-9]+), y=([-0-9]+)")
                .matchEntire(it)!!.groupValues.drop(1).map { it.toInt() }
            sbList.add(SB(sx, sy, bx, by))
        }
        val y = 2000000
        val segms = mutableListOf<Segment>()
        var answer = 0
        val bList = mutableSetOf<Int>()
        sbList.forEach { sb ->
            val d = abs(sb.sx - sb.bx) + abs(sb.sy - sb.by)
            if (sb.sy + d >= y || sb.sy - d <= y) {
                val dy = d - abs(y - sb.sy)
                if (sb.sx - dy <= sb.sx + dy + 1) {
                    segms.add(Segment(sb.sx - dy, true))
                    segms.add(Segment(sb.sx + dy + 1, false))
                }
                if (sb.by == y) bList.add(sb.bx)
            }
        }
        var last = -1
        var stack = 0
        segms.sortBy { it.a }
        segms.forEach {
            if (stack == 0) last = it.a
            if (it.type) stack++
            if (!it.type) stack--
            if (stack == 0) {
                answer += abs(it.a - last)
                bList.forEach { b ->
                    if (b in (last .. it.a)) answer--
                }
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val sbList = mutableListOf<SB>()
        input.forEach {
            val (sx, sy, bx, by) =
                Regex("Sensor at x=([-0-9]+), y=([-0-9]+): closest beacon is at x=([-0-9]+), y=([-0-9]+)")
                    .matchEntire(it)!!.groupValues.drop(1).map { it.toInt() }
            sbList.add(SB(sx, sy, bx, by))
        }
        val maxY = 4000000
        val segms = mutableListOf<Segment>()
//        val y = 2634249
        for (y in 0..maxY) {
            segms.clear()
            sbList.forEach { sb ->
                val d = abs(sb.sx - sb.bx) + abs(sb.sy - sb.by)
                if (sb.sy + d >= y || sb.sy - d <= y) {
                    val dy = d - abs(y - sb.sy)
                    if (sb.sx - dy <= sb.sx + dy + 1) {
                        segms.add(Segment(sb.sx - dy, true))
                        segms.add(Segment(sb.sx + dy + 1, false))
                    }
                }
            }
            segms.sortBy { it.a }
            var last = segms[0].a
            var stack = 0
            segms.forEach {
                if (it.a > last) {
                    if (stack == 0 && it.a in 0..maxY) {
                        return last * 4000000L + y
                    }
                    last = it.a
                }
                if (it.type) stack++
                if (!it.type) stack--
            }
        }
        return 0
    }

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
