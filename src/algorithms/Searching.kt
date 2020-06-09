package algorithms

/**
 * Binary search uses less than (lg n) comparisons for a sorted array
 */
fun <T : Comparable<T>> Array<T>.binarySearch(key: Int): Int? {
    var start = 0
    var end = size - 1
    while (start <= end) {
        val middle = start + (end - start) / 2
        when {
            key > middle -> start = middle + 1
            key < middle -> end = middle - 1
            else -> return middle
        }
    }
    return null
}