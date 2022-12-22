fun main() {

    fun part2(input: List<String>, flag: Boolean = true): Int {
        val W = input.maxOf { it.length }
        val map = input.take(input.size - 2).map { it.toCharArray() + CharArray(W - it.length) { ' ' } }
        val instructions = "(\\d+)|([LR])".toRegex().findAll(input.last()).map(MatchResult::value).toList()
        var position = map[0].indexOfFirst { it == '.' } to 0
        var facing = Facing.R
        instructions.forEach {
            when {
                it.all { ch -> ch.isDigit() } -> {
                    val steps = it.toInt()
                    repeat(steps) {
                        val newPosition: Pair<Int, Int>
                        if (flag) {
                            val pfc = facing.moveCube(position, map)
                            newPosition = pfc.position
                            if (map[newPosition.second][newPosition.first] == '.') {
                                position = newPosition
                                facing = pfc.nextFacing
                            }
                        } else {
                            newPosition = facing.move(position, map)
                            if (map[newPosition.second][newPosition.first] == '.') position = newPosition
                        }
                    }
                }
                else -> {
                    facing = facing.turn(it[0])
                }
            }
        }
        return 1000 * (position.second + 1) + 4 * (position.first + 1) + facing.value
    }

    fun part1(input: List<String>): Int = part2(input, false)

    val input = readInput("Day22")
    println(part1(input))
    println(part2(input))
}

private enum class Facing(val value: Int) {
    R(0),
    D(1),
    L(2),
    U(3);

    fun turn(ch: Char): Facing {
        val next = if (ch == 'R') ordinal + 1 else ordinal - 1
        return values()[next.mod(values().size)]
    }

    fun move(position: Pair<Int, Int>, map: List<CharArray>): Pair<Int, Int> {
        when (ordinal) {
            0 -> {
                var x = position.first + 1
                val y = position.second
                if (x >= map[0].size) x = 0
                if (map[y][x] == ' ') x = map[y].indexOfFirst { it == '.' || it == '#' }
                return x to y
            }
            1 -> {
                var y = position.second + 1
                val x = position.first
                if (y >= map.size) y = 0
                if (map[y][x] == ' ') y = map.indices.first { map[it][x] == '.' || map[it][x] == '#' }
                return x to y
            }
            2 -> {
                var x = position.first - 1
                val y = position.second
                if (x < 0) x = map[y].size - 1
                if (map[y][x] == ' ') x = map[y].indexOfLast { it == '.' || it == '#' }
                return x to y
            }
            else -> {
                var y = position.second - 1
                val x = position.first
                if (y < 0) y = map.size - 1
                if (map[y][x] == ' ') y = map.indices.reversed().first { map[it][x] == '.' || map[it][x] == '#' }
                return x to y
            }
        }
    }

    data class PFC(val position: Pair<Int, Int>, val nextFacing: Facing)

    private fun getNextCube(facing: Facing, cube: Int, x: Int): PFC {
        return when(cube) {
            1 -> {
                when (facing) {
                    U -> PFC(Pair(0, 150 + x), R)
                    L -> PFC(Pair(0, 149 - x), R)
                    else -> error("?")
                }
            }
            2 -> {
                when (facing) {
                    U -> PFC(Pair(x, 199), U)
                    R -> PFC(Pair(99, 149 - x), L)
                    D -> PFC(Pair(99, 50 + x), L)
                    else -> error("?")
                }
            }
            3 -> {
                when (facing) {
                    R -> PFC(Pair(100 + x, 49), U)
                    L -> PFC(Pair(x, 100), D)
                    else -> error("?")
                }
            }
            4 -> {
                when (facing) {
                    R -> PFC(Pair(149, 49 - x), L)
                    D -> PFC(Pair(49, 150 + x), L)
                    else -> error("?")
                }
            }
            5 -> {
                when (facing) {
                    U -> PFC(Pair(50, 50 + x), R)
                    L -> PFC(Pair(50, 49 - x), R)
                    else -> error("?")
                }
            }
            6 -> {
                when (facing) {
                    R -> PFC(Pair(50 + x, 149), U)
                    D -> PFC(Pair(100 + x, 0), D)
                    L -> PFC(Pair(50 + x, 0), D)
                    else -> error("?")
                }
            }
            else -> error("!")
        }
    }

    fun getCube(position: Pair<Int, Int>) : Int {
        return when {
            position.second in 0 until 50 && position.first in 50 until 100 -> 1
            position.second in 0 until 50 && position.first in 100 until 150 -> 2
            position.second in 50 until 100 && position.first in 50 until 100 -> 3
            position.second in 100 until 150 && position.first in 50 until 100 -> 4
            position.second in 100 until 150 && position.first in 0 until 50 -> 5
            position.second in 150 until 200 && position.first in 0 until 50 -> 6
            else -> error("!")
        }
    }

    fun moveCube(position: Pair<Int, Int>, map: List<CharArray>): PFC {
        val cube = getCube(position)
        when (ordinal) {
            0 -> {
                val x = position.first + 1
                val y = position.second
                return if (x >= map[y].size || map[y][x] == ' ') getNextCube(R, cube, y % 50)
                else PFC(Pair(x, y), R)
            }
            1 -> {
                val y = position.second + 1
                val x = position.first
                return if (y >= map.size || map[y][x] == ' ') getNextCube(D, cube, x % 50)
                else PFC(Pair(x, y), D)
            }
            2 -> {
                val x = position.first - 1
                val y = position.second
                return if (x < 0 || map[y][x] == ' ') getNextCube(L, cube, y % 50)
                else PFC(Pair(x, y), L)
            }
            else -> {
                val y = position.second - 1
                val x = position.first
                return if (y < 0 || map[y][x] == ' ') getNextCube(U, cube, x % 50)
                else PFC(Pair(x, y), U)
            }
        }
    }
}
