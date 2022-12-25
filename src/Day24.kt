fun main() {

    data class XYD(val x: Int, val y: Int, val d: Char)
    data class XY(val x: Int, val y: Int)

    fun step(map: MutableList<XYD>, H: Int, W: Int): MutableList<XYD> {
        val newMap = mutableListOf<XYD>()
        map.forEach {
            when (it.d) {
                '^' -> {
                    var x = it.x - 1
                    if (x <= 0) x = H - 2
                    newMap.add(XYD(x, it.y, it.d))
                }
                'v' -> {
                    var x = it.x + 1
                    if (x >= H - 1) x = 1
                    newMap.add(XYD(x, it.y, it.d))
                }
                '<' -> {
                    var y = it.y - 1
                    if (y <= 0) y = W - 2
                    newMap.add(XYD(it.x, y, it.d))
                }
                '>' -> {
                    var y = it.y + 1
                    if (y >= W - 1) y = 1
                    newMap.add(XYD(it.x, y, it.d))
                }
                else -> error("?")
            }
        }
        return newMap
    }

    fun move(a: XY, H: Int, W: Int): MutableList<XY> {
        val list = mutableListOf<XY>()
        var x = a.x - 1
        if (x >= 1 && a.y >= 1 && a.y < W - 1) list.add(XY(x, a.y))
        x = a.x + 1
        if (x < H - 1 && a.y >= 1 && a.y < W - 1) list.add(XY(x, a.y))
        var y = a.y - 1
        if (y >= 1 && a.x >= 1 && a.x < H - 1) list.add(XY(a.x, y))
        y = a.y + 1
        if (y < W - 1 && a.x >= 1 && a.x < H - 1) list.add(XY(a.x, y))
        return list
    }

    fun part1(input: List<String>): Int {
        var map = mutableListOf<XYD>()
        val H = input.size
        val W = input[0].length
        input.forEachIndexed { index, s ->
            input[index].forEachIndexed { index2, ch ->
                if (ch != '#' && ch != '.') map.add(XYD(index, index2, ch))
            }
        }
        val start = XY(0, 1)
        val end = XY(H - 2, input.last().indexOf('.'))
        var answer = 0
        var points = mutableSetOf(start)
        while (end !in points) {
            map = step(map, H, W)
            val newPoints = points.flatMap { move(it, H, W) }
            points.addAll(newPoints)
            points = points.filter { map.none { p -> p.x == it.x && p.y == it.y } }.toMutableSet()
            answer++
        }
        return answer + 1
    }

    fun part2(input: List<String>): Int {
        var map = mutableListOf<XYD>()
        val H = input.size
        val W = input[0].length
        input.forEachIndexed { index, s ->
            input[index].forEachIndexed { index2, ch ->
                if (ch != '#' && ch != '.') map.add(XYD(index, index2, ch))
            }
        }
        var start = XY(0, 1)
        var end = XY(H - 2, input.last().indexOf('.'))
        var answer = 0
        var points = mutableSetOf(start)
        fun path() {
            while (end !in points) {
                map = step(map, H, W)
                val newPoints = points.flatMap { move(it, H, W) }
                points.addAll(newPoints)
                points = points.filter { map.none { p -> p.x == it.x && p.y == it.y } }.toMutableSet()
                answer++
            }
        }
        path()
        map = step(map, H, W)
        answer++
        start = XY(H - 1, input.last().indexOf('.'))
        end = XY(1, 1)
        points = mutableSetOf(start)
        path()
        map = step(map, H, W)
        answer++
        start = XY(0, 1)
        end = XY(H - 2, input.last().indexOf('.'))
        points = mutableSetOf(start)
        path()
        return answer + 1
    }

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
