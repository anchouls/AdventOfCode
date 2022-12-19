fun main() {

    fun part1(input: List<String>): Int {
        val set = mutableSetOf<List<Int>>()
        input.forEach {
            val sp = it.split(",").map { it.toInt() }
            set.add(sp)
        }
        var answer = 0
        for (el in set) {
            for (i in 0..2) {
                val list = el.toMutableList()
                list[i]++
                if (list !in set) answer++
                list[i] -= 2
                if (list !in set) answer++

            }
        }
        return answer
    }

    fun neighbors(cube: List<Int>, set: MutableSet<List<Int>>, used: MutableSet<List<Int>>): MutableSet<List<Int>> {
        val neighbors = mutableSetOf<List<Int>>()
        for (i in 0..2) {
            for (j in listOf(-1, 1)) {
                val list = cube.toMutableList()
                list[i] += j
                if (list !in set && list !in used) neighbors.add(list)
            }
        }
        return neighbors
    }

    fun check(cube: List<Int>, set: MutableSet<List<Int>>, mn: List<Int>, mx: List<Int>): Boolean {
        val used = mutableSetOf<List<Int>>()
        var list = neighbors(cube, set, used)
        do {
            used.addAll(list)
            list = list.flatMap { neighbors(it, set, used) }.toMutableSet()
            val ok = list.all {
                it[0] in mn[0]..mx[0] && it[1] in mn[1]..mx[1] && it[2] in mn[2]..mx[2]
            }
        } while (ok && list.isNotEmpty())
        return list.isNotEmpty()
    }

    fun part2(input: List<String>): Int {
        val set = mutableSetOf<List<Int>>()
        input.forEach {
            val sp = it.split(",").map { it.toInt() }
            set.add(sp)
        }
        var answer = 0
        val mn = List(3) { i -> set.map { it[i] }.min() }
        val mx = List(3) { i -> set.map { it[i] }.max() }
        for (el in set) {
            for (i in 0..2) {
                for (j in listOf(-1, 1)) {
                    val list = el.toMutableList()
                    list[i] += j
                    if (list !in set) {
                        if (check(list, set, mn, mx)) answer++
                    }
                }
            }
        }
        return answer
    }

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
