@file:Suppress("ktlint:standard:filename")

package serie2.part3

class Node<T>(
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null,
)

fun <T> showList(list: Node<T>) {
    var x = list.next

    while (x != null && x != list) {
        print("${x.value} ")
        x = x.next
    }
    println()
}

fun <T> showListLinear(list: Node<T>?) {
    var x = list

    while (x != null) {
        print("${x.value} ")
        x = x.next
    }
    println()
}

fun <T> add(
    list: Node<T>,
    k: T,
) {
    val value = Node(k)
    value.next = list.next
    list.next?.previous = value
    list.next = value
    value.previous = list
}

fun <T> addAfter(
    node: Node<T>,
    newNode: Node<T>,
) {
    newNode.next = node.next
    newNode.previous = node
    node.next?.previous = newNode
    node.next = newNode
}

// Remove um nó EXISTENTE da lista
fun <T> removeNode(node: Node<T>) {
    node.previous?.next = node.next
    node.next?.previous = node.previous
    node.next = null
    node.previous = null
}

fun splitEvensAndOdds(list: Node<Int>) {
    var current = list.next
    var lastEven: Node<Int> = list

    while (current != null && current != list) {
        val next = current.next
        if (current.value % 2 == 0) {
            removeNode(current)
            addAfter(lastEven, current)
            lastEven = current
        }
        current = next
    }
}

fun <T> intersection(
    list1: Node<T>,
    list2: Node<T>,
    cmp: Comparator<T>,
): Node<T>? {
    var x = list1.next
    var y = list2.next
    var resultHead: Node<T>? = null
    var resultTail: Node<T>? = null

    while (x != null && x != list1 && y != null && y != list2) {
        val comparation = cmp.compare(x.value, y.value)

        when {
            comparation < 0 -> x = x.next
            comparation > 0 -> y = y.next
            else -> {
                // Aqui verificamos se há elemento repetido
                if (resultTail == null || cmp.compare(resultTail.value, x.value) != 0) {
                    // Remove da list1    Antes : A <-> x <-> B   Depois  A <-> B
                    x.previous?.next = x.next
                    x.next?.previous = x.previous

                    // Remove da list2     Antes : A <-> x <-> B   Depois  A <-> B
                    y.previous?.next = y.next
                    y.next?.previous = y.previous

                    val node = x
                    x = x.next
                    y = y.next

                    // Nesta parte, preparamos o nó para que nao seja circular e sem sentinela, para isso obrigamos a null
                    node.next = null
                    node.previous = resultTail

                    if (resultHead == null) {
                        resultHead = node
                    } else {
                        resultTail?.next = node
                    }
                    resultTail = node
                } else {
                    x = x.next
                    y = y.next
                }
            }
        }
    }
    return resultHead
}

fun main() {
    // Testes para splitEvensAndOdds
    val list = Node<Int>()
    add(list, 7)
    add(list, 1)
    add(list, 2)
    add(list, 5)
    add(list, 4)
    add(list, 8)
    add(list, 3)
    add(list, 21)
    showList(list)
    splitEvensAndOdds(list)
    showList(list)

    // Testes para intersection
    val list1 = Node<Int>()
    add(list1, 5)
    add(list1, 4)
    add(list1, 3)
    add(list1, 2)
    add(list1, 1)

    val list2 = Node<Int>()
    add(list2, 7)
    add(list2, 6)
    add(list2, 5)
    add(list2, 4)
    add(list2, 3)

    println("\nList1 antes da interseção:")
    showList(list1)

    println("List2 antes da interseção:")
    showList(list2)

    val result = intersection(list1, list2, Comparator { a, b -> a - b })

    println("\nInterseção (não circular):")
    showListLinear(result)

    println("\nList1 depois da interseção:")
    showList(list1)

    println("List2 depois da interseção:")
    showList(list2)
}
