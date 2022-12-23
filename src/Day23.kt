fun main() {

    data class XY(val x: Int, val y: Int)

    fun part1(input: List<String>, number: Int = 10, flag: Boolean = false): Int {
        var elves = input.mapIndexed { index, s -> s.withIndex().filter { ch -> ch.value == '#' }.map { ch -> XY(ch.index, index) } }.flatten().toSet()
        repeat(number) { r ->
            val moves = mutableMapOf<XY, MutableList<XY>>()
            var finish = 0
            elves.forEach { elf ->
                val x = elf.x
                val y = elf.y
                val emptyBlocks = listOf(XY(x, y - 1), XY(x, y + 1), XY(x - 1, y), XY(x + 1, y),
                    XY(x - 1, y - 1), XY(x - 1, y + 1),
                    XY(x + 1, y - 1), XY(x + 1, y + 1)).filter { it !in elves }
                if (emptyBlocks.size == 8) {
                    moves[elf]?.add(elf) ?: run { moves[elf] = mutableListOf(elf) }
                    finish++
                    return@forEach
                }
                for (i in 0 until 4) {
                    when ((i + r) % 4) {
                        0 -> {
                            if (listOf(XY(x, y - 1), XY(x - 1, y - 1), XY(x + 1, y - 1)).all { it in emptyBlocks }) {
                                val nextMove = XY(x, y - 1)
                                moves[nextMove]?.add(elf) ?: run { moves[nextMove] = mutableListOf(elf) }
                                return@forEach
                            }
                        }
                        1 -> {
                            if (listOf(XY(x, y + 1), XY(x - 1, y + 1), XY(x + 1, y + 1)).all { it in emptyBlocks }) {
                                val nextMove = XY(x, y + 1)
                                moves[nextMove]?.add(elf) ?: run { moves[nextMove] = mutableListOf(elf) }
                                return@forEach
                            }
                        }
                        2 -> {
                            if (listOf(XY(x - 1, y - 1), XY(x - 1, y), XY(x - 1, y + 1)).all { it in emptyBlocks }) {
                                val nextMove = XY(x - 1, y)
                                moves[nextMove]?.add(elf) ?: run { moves[nextMove] = mutableListOf(elf) }
                                return@forEach
                            }
                        }
                        3 -> {
                            if (listOf(XY(x + 1, y - 1), XY(x + 1, y), XY(x + 1, y + 1)).all { it in emptyBlocks }) {
                                val nextMove = XY(x + 1, y)
                                moves[nextMove]?.add(elf) ?: run { moves[nextMove] = mutableListOf(elf) }
                                return@forEach
                            }
                        }
                    }
                }
                moves[elf]?.add(elf) ?: run { moves[elf] = mutableListOf(elf) }
            }
            elves = moves.flatMap { (move, oldElves) ->
                if (oldElves.size == 1) listOf(move)
                else oldElves
            }.toSet()
            if (finish == elves.size && flag) return r + 1
        }
        val a = elves.maxBy { it.x }.x - elves.minBy { it.x }.x + 1
        val b = elves.maxBy { it.y }.y - elves.minBy { it.y }.y + 1
        return a * b - elves.size
    }

    fun part2(input: List<String>): Int {
        return part1(input, Int.MAX_VALUE, true)
    }

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}
