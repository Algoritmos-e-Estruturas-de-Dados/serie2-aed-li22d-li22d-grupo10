package serie2.part3

class Node<T> (
    var value: T = Any() as T,
    var next: Node<T>? = null,
    var previous: Node<T>? = null) {}


fun <T> showList(list: Node<T>) {
    var x = list.next

    while (x != list) {
        print("${x!!.value} ")
        x = x.next
    }
    println()
}




fun <T> add(list: Node<T>, k: T) {
    val value = Node(k)
    value.next = list.next
    list.next?.previous = value
    list.next = value
    value.previous = list
}

fun <T> remove(head: Node<T>?, k: T) {
    var x = head?.next

    while (x != head) {
        if (x!!.value == k) {
            x.previous!!.next = x.next
            x.next!!.previous = x.previous
            break
        }
        x = x.next
    }
}





fun splitEvensAndOdds(list: Node<Int>) {
    var x = list.next

    while (x != list) {
        val current = x!!
        x = x.next

        if (current.value % 2 == 0) {
            remove(list, current.value)
            add(list, current.value)
        }
    }
}

fun <T> intersection(list1: Node<T>, list2: Node<T>, cmp: Comparator<T>): Node<T>? {
    TODO()
}




fun main() {

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
}