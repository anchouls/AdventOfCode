import java.util.ArrayDeque

data class State(val number: Int, val position: Pair<Int, Int>)

fun main() {
    fun bfs(
        nodes: MutableList<MutableList<Char>>, start: Pair<Int, Int>, M: Int, N: Int
    ): Int {
        val visited = List(N) { BooleanArray(M) { false } }
        val queue = ArrayDeque<State>()
        queue.add(State(0, start))
        visited[start.first][start.second] = true

        while (queue.isNotEmpty()) {
            val cur = queue.pop()
            if (nodes[cur.position.first][cur.position.second] == 'z' + 1)
                return cur.number
            val nextSteps = listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
                .map { cur.position.first + it.first to cur.position.second + it.second }
                .filter { it.first in 0 until N && it.second in 0 until M }
            for (step in nextSteps) {
                val curChar = nodes[cur.position.first][cur.position.second].code
                if (nodes[step.first][step.second].code <= curChar + 1 && !visited[step.first][step.second]) {
                    visited[step.first][step.second] = true
                    queue.add(State(cur.number + 1, step))
                }
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val nodes = mutableListOf<MutableList<Char>>()
        var s = Pair(0, 0)
        var e = Pair(0, 0)
        for ((i, it) in input.withIndex()) {
            for ((j, ch) in it.withIndex()) {
                if (ch == 'S') s = Pair(i, j)
                if (ch == 'E') e = Pair(i, j)
            }
            nodes.add(it.toCharArray().toMutableList())
        }
        nodes[s.first][s.second] = 'a' - 1
        nodes[e.first][e.second] = 'z' + 1

        return bfs(nodes, s, m, n)
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        val nodes = mutableListOf<MutableList<Char>>()
        var s = Pair(0, 0)
        var e = Pair(0, 0)
        val starts = mutableListOf<Pair<Int, Int>>()
        for ((i, it) in input.withIndex()) {
            for ((j, ch) in it.withIndex()) {
                if (ch == 'a') starts.add(Pair(i, j))
                if (ch == 'S') {
                    s = Pair(i, j)
                    starts.add(Pair(i, j))
                }
                if (ch == 'E') e = Pair(i, j)
            }
            nodes.add(it.toCharArray().toMutableList())
        }
        nodes[s.first][s.second] = 'a' - 1
        nodes[e.first][e.second] = 'z' + 1

        var answer = n*m
        starts.forEach {
            val ans = bfs(nodes, it, m, n)
            if (ans < answer && ans != -1) answer = ans
        }
        return answer
    }

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
