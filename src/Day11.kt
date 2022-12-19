import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        val monkeys = mutableListOf<MutableList<Int>>()
        val divisibleM = mutableListOf<MutableList<Int>>()
        val operation = mutableListOf<Pair<Boolean, String>>()
        val countM = mutableListOf<Int>()
        input.forEach {
            val inp = it.trim().split(" ")
            when (inp[0]) {
                "Starting" -> {
                    countM.add(0)
                    val list = mutableListOf<Int>()
                    for ((i, el) in inp.withIndex()) {
                        if (i > 1) list.add(el.substring(0, 2).toInt())
                    }
                    monkeys.add(list)
                }
                "Operation:" -> {
                    if (inp[4] == "*") operation.add(Pair(true, inp[5]))
                    else operation.add(Pair(false, inp[5]))
                }
                "Test:" -> divisibleM.add(mutableListOf(inp[3].toInt()))
                "If" -> {
                    if (inp[1] == "true:") divisibleM.last().add(inp[5].toInt())
                    else divisibleM.last().add(inp[5].toInt())
                }
            }
        }
        repeat(20) {
            for ((i, el) in monkeys.withIndex()) {
                el.forEach {
                    countM[i] += 1
                    var a = it
                    val op = operation[i]
                    if (op.first) {
                        if (op.second == "old") a *= a
                        else a *= op.second.toInt()
                    } else {
                        if (op.second == "old") a += a
                        else a += op.second.toInt()
                    }
                    a /= 3
                    if (a % divisibleM[i][0] == 0) {
                        monkeys[divisibleM[i][1]].add(a)
                    } else {
                        monkeys[divisibleM[i][2]].add(a)
                    }
                }
                el.clear()
            }
        }
        countM.sortDescending()
        return countM[0] * countM[1]
    }

    fun part2(input: List<String>): BigInteger {
        val monkeys = mutableListOf<MutableList<Long>>()
        val divisibleM = mutableListOf<MutableList<Int>>()
        val operation = mutableListOf<Pair<Boolean, String>>()
        val countM = mutableListOf<BigInteger>()
        input.forEach {
            val inp = it.trim().split(" ")
            when (inp[0]) {
                "Starting" -> {
                    countM.add(0.toBigInteger())
                    val list = mutableListOf<Long>()
                    for ((i, el) in inp.withIndex()) {
                        if (i > 1) list.add(el.substring(0, 2).toLong())
                    }
                    monkeys.add(list)
                }
                "Operation:" -> {
                    if (inp[4] == "*") operation.add(Pair(true, inp[5]))
                    else operation.add(Pair(false, inp[5]))
                }
                "Test:" -> divisibleM.add(mutableListOf(inp[3].toInt()))
                "If" -> {
                    if (inp[1] == "true:") divisibleM.last().add(inp[5].toInt())
                    else divisibleM.last().add(inp[5].toInt())
                }
            }
        }
        repeat(10000) {
            for ((i, el) in monkeys.withIndex()) {
                el.forEach {
                    countM[i] += 1.toBigInteger()
                    var a = it
                    val op = operation[i]
                    if (op.first) {
                        if (op.second == "old") a *= a
                        else a *= op.second.toLong()
                    } else {
                        if (op.second == "old") a += a
                        else a += op.second.toLong()
                    }
                    a %= 9699690 // product of all divisibility checks
                    val d = divisibleM[i][0].toLong()
                    if (a % d == 0L) {
                        monkeys[divisibleM[i][1]].add(a)
                    } else {
                        monkeys[divisibleM[i][2]].add(a)
                    }
                }
                el.clear()
            }
        }
        countM.sortDescending()
        return countM[0] * countM[1]
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
