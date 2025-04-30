package serie2.part1_2

class IntArrayList: Iterable<Int> {

    private var list: Array<Int?>
    var size: Int = 0

    constructor(size: Int) {
        list = arrayOfNulls<Int?>(size)
    }

    private fun increaseCapacity() {
        val newArray = arrayOfNulls<Int?>(2 * list.size)
        // arraycopy(src, srcStart, dst, dstStart, size)
        System.arraycopy(list, 0, newArray, 0, size)
        list = newArray

    }

    fun append(x: Int): Boolean {
        var counter = size
        if (x in list) return false
        if (size == list.size) {
           increaseCapacity()
       }
            list[size++] = x
            counter++
            return true
    }

    fun get(n: Int): Int?  {
        return if (n in list.indices) list[n] else null
    }

    fun addToAll(x: Int) {
        for (i in 0 until size) {
            if (list[i] != null)
                list[i] = list[i]!! + x
        }
    }

    fun remove(): Boolean {
        if (list.isEmpty()) return false
        else {
            list[0] = null
            size--
            if (size < 0) return false
            else {
                System.arraycopy(list, 0, list, 0, size)
                return true
            }
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
    println(list.get(0))
    println(list.get(1))
    println(list.addToAll(10))
    println(list.get(0))
    println(list.get(1))
    println(list.remove())
    println(list.get(0))
}