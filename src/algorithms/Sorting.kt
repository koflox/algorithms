package algorithms

import swap

/**
 * Selection sort
 *
 * Худшее время	    О(n ^ 2)
 * Лучшее время	    О(n ^ 2)
 * Среднее время	О(n ^ 2)
 * Затраты памяти	О(n) всего, O(1) дополнительно
 */
fun <T : Comparable<T>> Array<T>.selectionSort() {
    for (i in 0 until size) {
        var indexMin = i
        for (j in i + 1 until size) {
            if (this[j] < this[indexMin]) indexMin = j
        }
        swap(i, indexMin)
    }
}

/**
 * Insertion sort
 * Устойчивая сортировка
 *
 * Худшее время	    О(n ^ 2) сравнений и обменов
 * Лучшее время	    O(n) сравнений, 0 обменов
 * Среднее время	О(n ^ 2) сравнений и обменов
 * Затраты памяти	О(n) всего, O(1) вспомогательный
 */
fun <T : Comparable<T>> Array<T>.insertionSort() {
    for (i in 1 until size) {
        var j = i
        while (j > 0 && this[j] < this[j - 1]) {
            swap(j, j - 1)
            j--
        }
    }
}

/**
 * Shell sort
 * Усовершенствованный вариант сортировки вставками, работает быстрые за счет грубых проходов с шагом в h,
 * что позволяет переставлять элементы с конца списка.
 *
 * Худшее время	    O(n ^ 2)
 * Лучшее время	    O(n * log^2 n)
 * Среднее время	зависит от выбранных шагов
 * Затраты памяти	О(n) всего, O(1) дополнительно
 */
fun <T : Comparable<T>> Array<T>.shellSort() {
    var h = 1
    while (h < size / 3) h = 3 * h + 1
    while (h >= 1) {
        for (i in h until size) {
            var j = i
            while (j >= h && this[j] < this[j - h]) {
                swap(j, j - h)
                j -= h
            }
        }
        h /= 3
    }
}

/**
 * Downward merge sort
 * Устойчивая сортировка
 * Из минусов, одинаковая скорость работы на хаотично и частично упорядоченных массивах
 * Также требует затраты по памяти пропорциональные исходному массиву
 *
 * Худшее время 	O(n * log n)
 * Лучшее время	    O(n * log n)
 * Среднее время	O(n * log n)
 * Затраты памяти	O(n) дополнительно
 */
fun <T : Comparable<T>> Array<T>.downwardMergeSort() {
    val copy = this.copyOf()
    downwardSort(copy, 0, size - 1)
}

fun <T : Comparable<T>> Array<T>.downwardSort(copy: Array<T>, start: Int, end: Int) {
    if (end <= start) return
    val mid = start + (end - start) / 2
    downwardSort(copy, start, mid) // сортировка левой половины
    downwardSort(copy, mid + 1, end) // сортировка правой половины
    merge(copy, start, mid, end) // слияние результатов
}

/**
 * Слияние на месте
 */
fun <T : Comparable<T>> Array<T>.merge(copy: Array<T>, start: Int, mid: Int, end: Int) {
    var i = start
    var j = mid + 1
    for (k in start..end) copy[k] = this[k]
    for (k in start..end) when {
        i > mid -> this[k] = copy[j++]
        j > end -> this[k] = copy[i++]
        copy[j] < copy[i] -> this[k] = copy[j++]
        else -> this[k] = copy[i++]
    }
}

/**
 * Quick sort
 * Из минусов - выполнения за квадратичное время в худшем случае,
 * когда каждое разделение даёт два подмассива размерами 1 и n-1
 *
 * Худшее время	    O(n ^ 2) (от него может защитить перемешивание массива)
 * Лучшее время	    O(n * log n)  (обычное разделение)
 * или              O(n)        (разделение на 3 части)
 * Среднее время	O(n * log n)
 * Затраты памяти	O(n)        дополнительно
 *                  O(log n)    дополнительно
 */
fun <T : Comparable<T>> Array<T>.quickSort() {
    // Перемешивание защищает от худшего случая и делает время выполнения предсказуемым
    val shuffled = this.toList().shuffled()
    for (i in 0 until size) this[i] = shuffled[i]

    qSort(0, size - 1)
}

fun <T : Comparable<T>> Array<T>.qSort(start: Int, end: Int) {
    if (end <= start) return
    val j = partition(start, end)
    qSort(start, j - 1)
    qSort(j + 1, end)
}

/**
 * Разбиение для быстрой сортировки
 */
fun <T : Comparable<T>> Array<T>.partition(start: Int, end: Int): Int {
    var i = start
    var j = end + 1
    val v = this[start]
    while (true) {
        while (this[++i] < v) if (i == end) break
        while (this[--j] > v) if (j == start) break
        if (i >= j) break
        swap(i, j)
    }
    swap(start, j)
    return j
}

/**
 * Heapsort (пирамидальная сортировка)
 * Может быть очень удобным если требуется отсортировать не весь массив, а получить, например, только несколько максимальных элементов.
 *
 * Худшее время	    O(n * log n)
 * Затраты памяти	О(n) всего, O(1) дополнительно
 */
fun <T : Comparable<T>> Array<T>.heapsort() {
    var n = size
    for (k in (n / 2) downTo 1) sinkHeapsort(k, n)
    while (n > 1) {
        swap(0, n-- - 1)
        sinkHeapsort(1, n)
    }
}

/**
 * Just a version of sink method with indices reduced by 1 for the heapsort
 */
fun <T : Comparable<T>> Array<T>.sinkHeapsort(k: Int, n: Int) {
    var i = k
    while (2 * i <= n) {
        var j = 2 * i
        if (j < n && this[j - 1] < this[j]) j++
        if (this[i - 1] >= this[j - 1]) break
        swap(i - 1, j - 1)
        i = j
    }
}