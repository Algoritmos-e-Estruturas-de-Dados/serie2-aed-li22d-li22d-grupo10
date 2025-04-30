package serie2.part1_2

class IntArrayList: Iterable<Int> {

    private var list: Array<Int?>

    var init = 0
    var end = 0
    var size = 0
    var offSet = 0

    constructor(size: Int) {
        list = arrayOfNulls<Int?>(size)
    }

    fun append(x: Int): Boolean {
        if (size == list.size) return false

        list[end] = x - offSet
        end = (end + 1) % list.size // Garante comportamento circular
        size++
        return true
    }

    fun get(n: Int): Int?  {
        if (n < 0 || n >= size) return null
        val idx = (init + n) % list.size
        return list[idx]?.plus(offSet)
    }

    fun addToAll(x: Int) {
        offSet += x
    }

    fun remove(): Boolean {
        if (size == 0) return false
        else {
            list[init] = null
            init = (init + 1) % list.size
            size--
            return true
        }
    }

    override fun iterator(): Iterator<Int> { // Opcional
        TODO("Not yet implemented")
    }
}

fun main() {
    val list = IntArrayList(3)
    println(list.append(10))
    println(list.append(4))
    println(list.get(0))
    println(list.get(1))
    println(list.addToAll(100))
    println(list.get(0))
    println(list.get(1))
    println(list.append(6))
    println(list.get(2))
}