fun main() {
    fun part1(input: List<String>): Int {
        val size = input[0].length
        val res = mutableListOf<BooleanArray>()
        repeat(input.size) {
            res.add(BooleanArray(size))
        }
        var tolls = IntArray(size) { -1 }
        for ((j, it) in input.withIndex()) {
            for ((i, el) in it.withIndex()) {
                val tree = el.digitToInt()
                if (tolls[i] < tree) {
                    tolls[i] = tree
                    res[j][i] = true
                }
            }
        }
        for ((j, it) in input.withIndex()) {
            var mx = -1
            for ((i, el) in it.withIndex()) {
                val tree = el.digitToInt()
                if (mx < tree) {
                    mx = tree
                    res[j][i] = true
                }
            }
        }
        for ((j, it) in input.withIndex()) {
            var mx = -1
            for ((i, el) in it.withIndex().reversed()) {
                val tree = el.digitToInt()
                if (mx < tree) {
                    mx = tree
                    res[j][i] = true
                }
            }
        }
        tolls = IntArray(size) { -1 }
        for ((j, it) in input.withIndex().reversed()) {
            for ((i, el) in it.withIndex()) {
                val tree = el.digitToInt()
                if (tolls[i] < tree) {
                    tolls[i] = tree
                    res[j][i] = true
                }
            }
        }
        var answer = 0
        res.forEach {
            it.forEach { el ->
                if (el) answer++
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        val size = input[0].length
        val res = mutableListOf<IntArray>()
        repeat(input.size) {
            res.add(IntArray(size) { 1 })
        }
        val nRows = input.size
        val nCols = input.size
        for ((rowInd, row) in input.withIndex()) {
            for ((colInd, value) in row.withIndex()) {
                var count = 0
                for (i in colInd + 1 until nCols) {
                    count++
                    if (input[rowInd][i] >= value) break
                }
                res[rowInd][colInd] *= count

                count = 0
                for (i in colInd - 1 downTo 0) {
                    count++
                    if (input[rowInd][i] >= value) break
                }
                res[rowInd][colInd] *= count

                count = 0
                for (i in rowInd + 1 until nRows) {
                    count++
                    if (input[i][colInd] >= value) break
                }
                res[rowInd][colInd] *= count

                count = 0
                for (i in rowInd - 1 downTo 0) {
                    count++
                    if (input[i][colInd] >= value) break
                }
                res[rowInd][colInd] *= count
            }
        }

        return res.map { it.max() }.max()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
