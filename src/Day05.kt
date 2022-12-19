fun main() {
    fun part1(input: List<String>): String {
        var size = 0
        val cont = mutableListOf<MutableList<Char>>()
        input.forEach {
            if (size == 0) {
                size = (it.length + 1)/4
                repeat(size) {
                    cont.add(mutableListOf())
                }
            }
            if (it.startsWith("move")) {
                val move = it.split(" ")
                val num = move[1].toInt()
                val a = move[3].toInt() - 1
                val b = move[5].toInt() - 1
                repeat(num) {
                    val el = cont[a].removeFirst()
                    cont[b].add(0, el)
                }
            } else {
                val array = it.toCharArray()
                var ind = 1
                while (ind < array.size) {
                    if (array[ind] in 'A'..'Z') {
                        val index = (ind - 1)/4
                        val currentCont = cont.get(index)
                        currentCont.add(array[ind])
                        cont[index] = currentCont
                    }
                    ind += 4
                }
            }
        }
        val answer = StringBuffer()
        for (el in cont) {
            answer.append(el[0])
        }
        return answer.toString()
    }

    fun part2(input: List<String>): String {
        var size = 0
        val cont = mutableListOf<MutableList<Char>>()
        input.forEach {
            if (size == 0) {
                size = (it.length + 1)/4
                repeat(size) {
                    cont.add(mutableListOf())
                }
            }
            if (it.startsWith("move")) {
                val move = it.split(" ")
                val num = move[1].toInt()
                val a = move[3].toInt() - 1
                val b = move[5].toInt() - 1
                var index = 0
                repeat(num) {
                    val el = cont[a].removeFirst()
                    cont[b].add(index, el)
                    index++
                }
            } else {
                val array = it.toCharArray()
                var ind = 1
                while (ind < array.size) {
                    if (array[ind] in 'A'..'Z') {
                        val index = (ind - 1)/4
                        val currentCont = cont.get(index)
                        currentCont.add(array[ind])
                        cont[index] = currentCont
                    }
                    ind += 4
                }
            }
        }
        val answer = StringBuffer()
        for (el in cont) {
            answer.append(el[0])
        }
        return answer.toString()
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
