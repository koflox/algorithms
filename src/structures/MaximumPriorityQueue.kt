package structures

import swap

/**
 * Операции вставки и получения максимального элемента занимают (log n) времени
 */
class MaximumPriorityQueue<T : Comparable<T>>(maxSize: Int) {

    private var pq: Array<T> = arrayOfNulls<Comparable<*>>(maxSize + 1) as Array<T>
    private var n = 0

    fun isEmpty() = n == 0

    fun size() = n

    fun insert(value: T) {
        pq[++n] = value
        pq.swim(n)
    }

    fun retrieveMax(): T {
        val max = pq[1]
        pq.swap(1, n--)
        pq.sink(1, n)
        return max
    }

    private fun <T : Comparable<T>> Array<T>.swim(k: Int) {
        var i = k
        while (i > 1 && this[i / 2] < this[i]) {
            swap(i / 2, i)
            i /= 2
        }
    }

    private fun <T : Comparable<T>> Array<T>.sink(k: Int, n: Int) {
        var i = k
        while (2 * i <= n) {
            var j = 2 * i
            if (j < n && this[j] < this[j + 1]) j++
            if (this[i] >= this[j]) break
            swap(i, j)
            i = j
        }
    }

}