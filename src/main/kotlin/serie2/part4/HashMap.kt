package serie2.part4

import java.util.NoSuchElementException

class HashMap<K, V>(
    initialCapacity: Int = 16,
    val loadFactor: Float = 0.75f,
) : MutableMap<K, V> {
    private class HashNode<K, V>(
        override val key: K,
        override var value: V,
        var next: HashNode<K, V>? = null,
    ) : MutableMap.MutableEntry<K, V> {
        override fun setValue(newValue: V): V {
            val oldValue = value
            value = newValue
            return oldValue
        }
    }

    private var table: Array<HashNode<K, V>?> = arrayOfNulls(initialCapacity)
    override var size = 0
    override val capacity: Int
        get() = table.size

    private fun hash(key: K): Int {
        var idx = key.hashCode() % capacity
        if (idx < 0) idx += capacity
        return idx
    }

    override fun get(key: K): V? {
        val idx = hash(key)
        var curr = table[idx]
        while (curr != null) {
            if (key == curr.key) return curr.value
            curr = curr.next
        }
        return null
    }

    override fun put(
        key: K,
        value: V,
    ): V? {
        if (size.toFloat() / capacity >= loadFactor) {
            expand()
        }

        val idx = hash(key)
        var curr = table[idx]
        while (curr != null) {
            if (key == curr.key) {
                val oldValue = curr.value
                curr.value = value
                return oldValue
            }
            curr = curr.next
        }

        val newNode = HashNode(key, value, table[idx])
        table[idx] = newNode
        size++
        return null
    }

    private fun expand() {
        val newCapacity = capacity * 2
        val newTable = arrayOfNulls<HashNode<K, V>?>(newCapacity)
        for (i in table.indices) {
            var curr = table[i]
            while (curr != null) {
                val nextNode = curr.next
                val newIdx = curr.key.hashCode() % newCapacity
                if (newIdx < 0) newIdx + newCapacity

                curr.next = newTable[newIdx]
                newTable[newIdx] = curr
                curr = nextNode
            }
        }

        table = newTable
    }

    override fun iterator(): Iterator<MutableMap.MutableEntry<K, V>> {
        return object : Iterator<MutableMap.MutableEntry<K, V>> {
            private var tableIndex = 0
            private var currentNode: HashNode<K, V>? = null

            init {
                findNextNode()
            }

            private fun findNextNode() {
                if (currentNode?.next != null) {
                    currentNode = currentNode?.next
                    return
                }
                tableIndex++
                while (tableIndex < capacity) {
                    if (table[tableIndex] != null) {
                        currentNode = table[tableIndex]
                        return
                    }
                    tableIndex++
                }
                currentNode = null
            }

            override fun hasNext(): Boolean = currentNode != null

            override fun next(): MutableMap.MutableEntry<K, V> {
                if (!hasNext()) throw NoSuchElementException()
                val current = currentNode!!
                findNextNode()
                return current
            }
        }
    }
}
