fun main() {

    data class XY(val x: Int, val y: Int)

    fun shapes(type: Int): List<XY> {
        return when (type) {
            0 -> listOf(XY(0, 0), XY(0, 1), XY(0, 2), XY(0, 3))
            1 -> listOf(XY(0, 1), XY(1, 0), XY(1, 1), XY(1, 2), XY(2, 1))
            2 -> listOf(XY(0, 0), XY(0, 1), XY(0, 2), XY(1, 2), XY(2, 2))
            3 -> listOf(XY(0, 0), XY(1, 0), XY(2, 0), XY(3, 0))
            4 -> listOf(XY(0, 0), XY(1, 0), XY(0, 1), XY(1, 1))
            else -> listOf()
        }
    }

    fun move(type: Int, x: Int, y: Int, chamber: MutableList<BooleanArray>) : Boolean {
        if (x < 0) return false
        var check = true
        val shape = shapes(type)
        shape.forEach {
            val i = x + it.x
            val j = y + it.y
            if (j !in 0..6) check = false
            else if (i in chamber.indices && chamber[i][j]) check = false
        }
        return check
    }

    fun part1(input: List<String>): Int {
        val rocks = 2022
        val d = input[0].toCharArray()
        val chamber = mutableListOf<BooleanArray>()
        var i = 0
        var type = -1
        for (r in 0 until rocks) {
            type = (type + 1) % 5
            var x = chamber.size + 3
            var y = 2
            while (true) {
                when (d[i]) {
                    '<' -> if (move(type, x, y - 1, chamber)) y--
                    '>' -> if (move(type, x, y + 1, chamber)) y++
                }
                i = (i + 1) % d.size
                if (move(type, x - 1, y, chamber)) x--
                else break
            }
            val shape = shapes(type)
            shape.forEach {
                val x1 = x + it.x
                val y1 = y + it.y
                while (x1 > chamber.lastIndex) chamber += BooleanArray(7)
                chamber[x1][y1] = true
            }
        }
        return chamber.size
    }

    data class Cycle(val lastChamber: List<List<Boolean>>, val i: Int, val type: Int)

    fun part2(input: List<String>): Long {
        val rocks = 1000000000000L
        val d = input[0].toCharArray()
        val chamber = mutableListOf<BooleanArray>()
        var i = 0
        var type = -1
        val mapCycles = mutableMapOf<Cycle, Int>()
        val indexOfCycles = mutableMapOf<Int, Int>()
        lateinit var cycle: Pair<Int, Int>

        for (r in 0 until rocks) {
            type = (type + 1) % 5
            var x = chamber.size + 3
            var y = 2
            while (true) {
                when (d[i]) {
                    '<' -> if (move(type, x, y - 1, chamber)) y--
                    '>' -> if (move(type, x, y + 1, chamber)) y++
                }
                i = (i + 1) % d.size
                if (move(type, x - 1, y, chamber)) x--
                else break
            }
            val shape = shapes(type)
            shape.forEach {
                val x1 = x + it.x
                val y1 = y + it.y
                while (x1 > chamber.lastIndex) chamber += BooleanArray(7)
                chamber[x1][y1] = true
            }
            val currentTop = chamber.size
            indexOfCycles[r.toInt()] = currentTop
            if (currentTop > d.size) {
                val state = Cycle(chamber.subList(currentTop - d.size, currentTop).map { it.toList() }.toList(), i, type)
                val prevIndex = mapCycles[state]
                if (prevIndex != null) {
                    cycle = prevIndex to r.toInt()
                    break
                } else mapCycles[state] = r.toInt()
            }
        }
        val inCycleRocks = 1000000000000 - cycle.first - 1
        val cycleLength = cycle.second - cycle.first
        val cycleHeight = chamber.size - indexOfCycles[cycle.first]!!
        val inCycleHeight = (inCycleRocks / cycleLength) * cycleHeight
        val restHeight = indexOfCycles[(inCycleRocks % cycleLength).toInt() + cycle.first]!!
        return inCycleHeight + restHeight
    }

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
