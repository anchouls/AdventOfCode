fun main() {

    fun compare(list1: Any, list2: Any): Int {
        if (list1 is Int && list2 is Int) {
            return list1.compareTo(list2)
        } else if (list1 is Int) {
            return compare(listOf(list1), list2)
        } else if (list2 is Int) {
            return compare(list1, listOf(list2))
        } else if (list1 is List<*> && list2 is List<*>) {
            var i = 0
            while (i < list1.size && i < list2.size) {
                val c = compare(list1[i]!!, list2[i]!!)
                if (c != 0) return c
                i++
            }
            return list1.size.compareTo(list2.size)
        }
        error("?")
    }

    fun parse(str: String, i: Int = 0): Pair<Any, Int> {
        var j = i
        if (str[j] == '[') {
            j++
            val list = mutableListOf<Any>()
            while (true) {
                if (str[j] == ']') {
                    j++
                    return list to j
                }
                val res = parse(str, j)
                list.add(res.first)
                j = res.second
                if (str[j] == ']') {
                    j++
                    return list to j
                }
                j++
            }
        }
        var num = 0
        while (str[j] in '0'..'9') {
            num = num * 10 + (str[j] - '0')
            j++
        }
        return num to j
    }

    fun part1(input: List<String>): Int {
        var answer = 0
        var j = 1
        for ((i, array) in input.withIndex()) {
            if (array == "") j += 1
            else if (i%3 == 0) {
                val list1 = parse(array).first
                val list2 = parse(input[i+1]).first
                val res = compare(list1, list2)
                if (res < 0) answer += j
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        val divider1 = parse("[[2]]").first
        val divider2 = parse("[[6]]").first
        var lists = mutableListOf<Any>()
        lists.add(divider1)
        lists.add(divider2)
        for (array in input) {
            if (array != "") lists.add(parse(array).first)
        }
        lists = lists.sortedWith { a, b -> compare(a, b) } as MutableList<Any>
        val i1 = lists.indexOf(divider1) + 1
        val i2 = lists.indexOf(divider2) + 1
        return i1 * i2
    }

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
