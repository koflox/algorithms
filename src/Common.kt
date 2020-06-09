fun <T> Array<T>.swap(i1: Int, i2: Int) {
    val temp = this[i1]
    this[i1] = this[i2]
    this[i2] = temp
}