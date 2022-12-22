fun main() {

    fun part1(input: List<String>): Int {
        val numbers = input.mapIndexed { index, i -> index to i.toInt() }
        val array = numbers.toMutableList()
        numbers.forEachIndexed { index, _ ->
            val i = array.indexOfFirst { it.first == index }
            val element = array[i]
            array.removeAt(i)
            val newIndex = (i + element.second).mod(array.size)
            if (newIndex == 0) array.add(element)
            else array.add(newIndex, element)
        }
        val res = array.map { it.second }
        val position0 = res.indexOf(0)
        var answer = 0
        for (i in listOf(1000, 2000, 3000)) {
            answer += res[(position0 + i) % res.size]
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val numbers = input.mapIndexed { index, i -> index to i.toLong() * 811589153L }
        val array = numbers.toMutableList()
        repeat(10) {
            numbers.forEachIndexed { index, _ ->
                val i = array.indexOfFirst { it.first == index }
                val element = array[i]
                array.removeAt(i)
                val newIndex = (i + element.second).mod(array.size)
                if (newIndex == 0) array.add(element)
                else array.add(newIndex, element)
            }
        }
        val res = array.map { it.second }
        val position0 = res.indexOf(0)
        var answer = 0L
        for (i in listOf(1000, 2000, 3000)) {
            answer += res[(position0 + i) % res.size]
        }
        return answer
    }

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
