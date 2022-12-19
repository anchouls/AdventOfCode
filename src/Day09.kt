fun main() {
    fun part1(input: List<String>): Int {
        val st = mutableSetOf<Pair<Int, Int>>()
        val H = mutableListOf(0, 0)
        val T = mutableListOf(0, 0)
        st.add(Pair(T[0], T[1]))
        input.forEach {
            val inp = it.split(" ")
            val side = inp[0]
            val num = inp[1].toInt()
            when (side) {
                "R" -> {
                    repeat(num) {
                        H[0]++
                        if (H[0] - T[0] == 2) {
                            T[0]++
                            T[1] += (H[1] - T[1])
                        }
                        st.add(Pair(T[0], T[1]))
                    }
                }
                "L" -> {
                    repeat(num) {
                        H[0]--
                        if (T[0] - H[0] == 2) {
                            T[0]--
                            T[1] += (H[1] - T[1])
                        }
                        st.add(Pair(T[0], T[1]))
                    }
                }
                "U" -> {
                    repeat(num) {
                        H[1]++
                        if (H[1] - T[1] == 2) {
                            T[1]++
                            T[0] += (H[0] - T[0])
                        }
                        st.add(Pair(T[0], T[1]))
                    }
                }
                "D" -> {
                    repeat(num) {
                        H[1]--
                        if (T[1] - H[1] == 2) {
                            T[1]--
                            T[0] += (H[0] - T[0])
                        }
                        st.add(Pair(T[0], T[1]))
                    }
                }
            }
        }
        return st.size
    }

    fun part2(input: List<String>): Int {
        val st = mutableSetOf(Pair(0, 0))
        val tr = List(10) { mutableListOf(0, 0) }
        input.forEach {
            val inp = it.split(" ")
            val side = inp[0]
            val num = inp[1].toInt()
            repeat(num) {
                when (side) {
                    "R" -> tr[0][0]++
                    "L" -> tr[0][0]--
                    "U" -> tr[0][1]++
                    "D" -> tr[0][1]--
                }
                tr.indices.toList().dropLast(1).forEach { i ->
                    val dx = tr[i][0] - tr[i + 1][0]
                    val dy = tr[i][1] - tr[i + 1][1]

                    if (dx == 2 && dy == 2) {
                        tr[i + 1][0]++
                        tr[i + 1][1]++
                    } else if (dx == 2 && dy == -2) {
                        tr[i + 1][0]++
                        tr[i + 1][1]--
                    } else if (dx == -2 && dy == 2) {
                        tr[i + 1][0]--
                        tr[i + 1][1]++
                    } else if (dx == -2 && dy == -2) {
                        tr[i + 1][0]--
                        tr[i + 1][1]--
                    } else if (dx == 2) {
                        tr[i + 1][0]++
                        tr[i + 1][1] += (tr[i][1] - tr[i + 1][1])
                    } else if (dx == -2) {
                        tr[i + 1][0]--
                        tr[i + 1][1] += (tr[i][1] - tr[i + 1][1])
                    } else if (dy == 2) {
                        tr[i + 1][1]++
                        tr[i + 1][0] += (tr[i][0] - tr[i + 1][0])
                    } else if (dy == -2) {
                        tr[i + 1][1]--
                        tr[i + 1][0] += (tr[i][0] - tr[i + 1][0])
                    }
                }
                st.add(Pair(tr.last()[0], tr.last()[1]))
            }
        }
        return st.size
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
