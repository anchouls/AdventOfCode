class Node(val name: String, val parent: Node?) {
    val children = mutableListOf<Node>()
    var size = 0
}

fun traversal(node: Node): Int {
    var size = node.size
    node.children.forEach {
        size += traversal(it)
    }
    node.size = size
    return size
}

fun traver(node: Node): Int {
    var size = 0
    if (node.size <= 100000) {
        size += node.size
    }
    node.children.forEach {
        size += traver(it)
    }
    return size
}

fun find(node: Node, x: Int): Int {
    var min = node.size
    var size = 0
    if (node.size >= x && (node.size - x) < min) {
        min = node.size - x
        size = node.size
    }
    node.children.forEach {
        val childrenSize = find(it, x)
        if (childrenSize >= x && (childrenSize - x) < min) {
            min = childrenSize - x
            size = childrenSize
        }
    }
    return size
}


fun main() {
    fun part1(input: List<String>): Int {
        val root = Node("/", null)
        var curr = root
        input.forEach {
            val inp = it.split(" ")
            when {
                inp[1] == "cd" -> {
                    if (inp[2] == "..") {
                        curr = curr.parent!!
                    } else {
                        val name = inp[2]
                        curr.children.forEach { ch ->
                            if (ch.name == name) {
                                curr = ch
                            }
                        }
                    }
                }
                inp[0] == "dir" -> {
                    val name = inp[1]
                    val newNode = Node(name, curr)
                    curr.children.add(newNode)
                }
                inp[0][0].isDigit() -> {
                    val num = inp[0].toInt()
                    curr.size += num
                }
            }
        }
        traversal(root)
        return traver(root)
    }

    fun part2(input: List<String>): Int {
        val root = Node("/", null)
        var curr = root
        input.forEach {
            val inp = it.split(" ")
            when {
                inp[1] == "cd" -> {
                    if (inp[2] == "..") {
                        curr = curr.parent!!
                    } else {
                        val name = inp[2]
                        curr.children.forEach { ch ->
                            if (ch.name == name) {
                                curr = ch
                            }
                        }
                    }
                }
                inp[0] == "dir" -> {
                    val name = inp[1]
                    val newNode = Node(name, curr)
                    curr.children.add(newNode)
                }
                inp[0][0].isDigit() -> {
                    val num = inp[0].toInt()
                    curr.size += num
                }
            }
        }
        traversal(root)
        val x = 8729145
        return find(root, x)
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
