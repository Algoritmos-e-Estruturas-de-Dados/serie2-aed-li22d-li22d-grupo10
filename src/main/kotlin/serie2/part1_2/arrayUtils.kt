package serie2.part1_2

// Como estamos a trabalhar num maxHeap, o menor elemento não pode estar nos nós internos pois são maiores que os valores que estão abaixo.
// Temos então de procurar nessas folhas
// As folhas começam no índice heapSize / 2  pois o maior valor é o do primeiro indice.
// Percorremos apenas os elementos folha (da metade em diante) para encontrar o menor.

fun minimum(
    maxHeap: Array<Int>,
    heapSize: Int,
): Int {
    val firstLeaf = heapSize / 2
    var min = maxHeap[firstLeaf]

    for (i in firstLeaf + 1 until heapSize) {
        if (maxHeap[i] < min) {
            min = maxHeap[i]
        }
    }
    return min
}
