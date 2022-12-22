fun main() {

    data class Monkey(val number: Long? = null, val operator: Char? = null, val waitMonkeys: List<String> = listOf())

    fun calc(monkeys: MutableMap<String, Monkey>, name: String) : Long {
        val current = monkeys[name]!!
        return current.number
            ?: when (current.operator) {
                '+' -> calc(monkeys, current.waitMonkeys[0]) + calc(monkeys, current.waitMonkeys[1])
                '*' -> calc(monkeys, current.waitMonkeys[0]) * calc(monkeys, current.waitMonkeys[1])
                '-' -> calc(monkeys, current.waitMonkeys[0]) - calc(monkeys, current.waitMonkeys[1])
                '/' -> calc(monkeys, current.waitMonkeys[0]) / calc(monkeys, current.waitMonkeys[1])
                else -> error("?")
            }
    }

    fun part1(input: List<String>): Long {
        val monkeys = mutableMapOf<String, Monkey>()
        input.forEach {
            val name = it.substringBefore(":")
            val after = it.substringAfter(":").trim()
            if (after[0].isDigit()) {
                monkeys[name] = Monkey(after.toLong())
            }else {
                val sp = after.split(" ")
                monkeys[name] = Monkey(null, sp[1][0], listOf(sp[0], sp[2]))
            }
        }
        return calc(monkeys, "root")
    }

    fun dependOnHuman(monkeys: MutableMap<String, Monkey>, name: String) : Boolean {
        if (name == "humn") return true
        val current = monkeys[name]!!
        return if (current.number != null) false
        else dependOnHuman(monkeys, current.waitMonkeys[0]) || dependOnHuman(monkeys, current.waitMonkeys[1])
    }

    fun calcNumberHuman(monkeys: MutableMap<String, Monkey>, name: String, number: Long? = null): Pair<Long, Long?> {
        val current = monkeys[name]!!
        if (current.number != null) return current.number to null
        else {
            val first = dependOnHuman(monkeys, current.waitMonkeys[0])
            val second = dependOnHuman(monkeys, current.waitMonkeys[1])
            if (!first && !second) return calc(monkeys, name) to null
            val monkeyDepOnHuman = if (first) current.waitMonkeys[0] else current.waitMonkeys[1]
            val notDepOnHuman = if (first) current.waitMonkeys[1] else current.waitMonkeys[0]
            var expNumber = calc(monkeys, notDepOnHuman)
            if (name != "root") {
                expNumber = when (current.operator) {
                    '+' -> number!! - expNumber
                    '*' -> number!! / expNumber
                    '-' -> if (first) number!! + expNumber else expNumber - number!!
                    '/' -> if (first) number!! * expNumber else expNumber / number!!
                    else -> error("?")
                }
            }
            return if (monkeyDepOnHuman == "humn") expNumber to expNumber
            else calcNumberHuman(monkeys, monkeyDepOnHuman, expNumber)
        }
    }

    fun part2(input: List<String>): Long? {
        val monkeys = mutableMapOf<String, Monkey>()
        input.forEach {
            val name = it.substringBefore(":")
            val after = it.substringAfter(":").trim()
            if (after[0].isDigit()) {
                monkeys[name] = Monkey(after.toLong())
            }else {
                val sp = after.split(" ")
                monkeys[name] = Monkey(null, sp[1][0], listOf(sp[0], sp[2]))
            }
        }
        return calcNumberHuman(monkeys, "root").second
    }

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
