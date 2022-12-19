import java.util.PriorityQueue

fun main() {

    data class Data(val oreR: Int, val clayR: Int, val obsidianROre: Int, val obsidianRClay: Int, val geodeROre: Int, val geodeRObsidian: Int)
    data class State(val timeLeft: Int, val ore: Int, val clay: Int, val obsidian: Int, val geode: Int, val oreR: Int, val clayR: Int, val obsidianR: Int, val geodeR: Int)

    fun solve(data: Data, allTime: Int): Int {
        val startState = State(allTime, 0, 0, 0, 0, 1, 0, 0, 0)
        val queue = PriorityQueue<State>(
            compareBy {
                val extra = if (it.timeLeft > 1) (it.timeLeft + 1) * (it.timeLeft) / 2 else 0
                Int.MAX_VALUE - (it.geode + it.geodeR * it.timeLeft + extra)
            }
        )
        queue.add(startState)
        val maxOre = listOf(data.oreR, data.clayR, data.obsidianROre, data.geodeROre).max()
        val map = mutableMapOf<State, Int>()
        while (queue.isNotEmpty()) {
            val state = queue.remove()
            if (state in map) {
                if (map[state]!! >= 0) continue
            } else map[state] = state.timeLeft

            if (state.timeLeft == 0) return state.geode
            val size = queue.size

            if (state.ore >= data.geodeROre && state.obsidian >= data.geodeRObsidian) {
                queue.add(State(state.timeLeft - 1,
                    state.ore + state.oreR - data.geodeROre,
                    state.clay + state.clayR,
                    state.obsidian + state.obsidianR - data.geodeRObsidian,
                    state.geode + state.geodeR,
                    state.oreR, state.clayR, state.obsidianR, state.geodeR + 1))
                continue
            }
            if (state.ore >= data.obsidianROre && state.clay >= data.obsidianRClay && state.obsidianR <= data.geodeRObsidian) {
                queue.add(State(state.timeLeft - 1,
                    state.ore + state.oreR - data.obsidianROre,
                    state.clay + state.clayR - data.obsidianRClay,
                    state.obsidian + state.obsidianR,
                    state.geode + state.geodeR,
                    state.oreR, state.clayR, state.obsidianR + 1, state.geodeR))
            }
            if (state.ore >= data.clayR && state.clayR <= data.obsidianRClay) {
                queue.add(State(state.timeLeft - 1,
                    state.ore + state.oreR - data.clayR,
                    state.clay + state.clayR,
                    state.obsidian + state.obsidianR,
                    state.geode + state.geodeR,
                    state.oreR, state.clayR + 1, state.obsidianR, state.geodeR))
            }
            if (state.ore >= data.oreR && state.oreR <= maxOre) {
                queue.add(State(state.timeLeft - 1,
                    state.ore + state.oreR - data.oreR,
                    state.clay + state.clayR,
                    state.obsidian + state.obsidianR,
                    state.geode + state.geodeR,
                    state.oreR + 1, state.clayR, state.obsidianR, state.geodeR))
            }
            if (queue.size != size + 3) {
                queue.add(State(state.timeLeft - 1,
                    state.ore + state.oreR,
                    state.clay + state.clayR,
                    state.obsidian + state.obsidianR,
                    state.geode + state.geodeR,
                    state.oreR, state.clayR, state.obsidianR, state.geodeR))
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        var answer = 0
        input.forEach {
            val (i, oreR, clayR, obsidianROre, obsidianRClay, geodeROre, geodeRObsidian) =
                Regex("Blueprint ([-0-9]+): Each ore robot costs ([-0-9]+) ore. Each clay robot costs ([-0-9]+) ore. Each obsidian robot costs ([-0-9]+) ore and ([-0-9]+) clay. Each geode robot costs ([-0-9]+) ore and ([-0-9]+) obsidian.")
                    .matchEntire(it)!!.groupValues.drop(1).map { it.toInt() }
            val g = solve(Data(oreR, clayR, obsidianROre, obsidianRClay, geodeROre, geodeRObsidian), 24)
            answer += g * i
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 1
        input.forEachIndexed { index, it ->
            if (index < 3) {
                val (i, oreR, clayR, obsidianROre, obsidianRClay, geodeROre, geodeRObsidian) =
                    Regex("Blueprint ([-0-9]+): Each ore robot costs ([-0-9]+) ore. Each clay robot costs ([-0-9]+) ore. Each obsidian robot costs ([-0-9]+) ore and ([-0-9]+) clay. Each geode robot costs ([-0-9]+) ore and ([-0-9]+) obsidian.")
                        .matchEntire(it)!!.groupValues.drop(1).map { it.toInt() }
                val g = solve(Data(oreR, clayR, obsidianROre, obsidianRClay, geodeROre, geodeRObsidian), 32)
                answer *= g
            }
        }
        return answer
    }

    val input = readInput("Day19")
    println(part1(input))
    println(part2(input))
}

private operator fun <E> List<E>.component6(): E = get(5)
private operator fun <E> List<E>.component7(): E = get(6)
