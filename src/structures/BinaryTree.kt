package structures

import java.util.*


/**
 * put and get methods for a binary tree takes ~2*(ln n),
 * searching is longer for 39% in comparison with sorted array
 * but insertion is faster because it mostly takes (n) for a sorted array
 *
 * Example of typical case for a binary tree:
 *
 *                          27
 *                     ┌─────┴─────┐
 *                    13          29
 *              ┌──────┴──────┐  ┌─┴─┐
 *              8             23 28  30
 *           ┌──┴──┐
 *           4     11
 *         ┌─┴─┐   ┌┴┐
 *         2   5   9 12
 */
class BinaryTree<K : Comparable<K>, V> {

    private var root: Node? = null

    private inner class Node(
        val key: K,
        var value: V,
        var n: Int,
        var left: Node? = null,
        var right: Node? = null
    )

    fun size(): Int = size(root)

    fun get(key: K): V? = get(root, key)

    fun put(key: K, value: V) {
        root = put(root, key, value)
    }

    fun delete(key: K) {
        root = delete(root, key)
    }

    fun deleteMin() {
        root = deleteMin(root)
    }

    fun min(): K? = min(root)?.key

    fun max(): K? = max(root)?.key

    fun keys(): Iterable<K> {
        val min = min()
        val max = max()
        return when {
            min == null || max == null -> listOf<K>()
            else -> keys(min, max)
        }
    }

    fun keys(start: K, end: K): Iterable<K> {
        val queue: Queue<K> = LinkedList()
        keys(root, queue, start, end)
        return queue
    }

    private fun size(node: Node?): Int = when (node) {
        null -> 0
        else -> node.n
    }

    private fun get(node: Node?, key: K): V? = when (node) {
        null -> null
        else -> {
            val cmp = key.compareTo(node.key)
            when {
                cmp < 0 -> get(node.left, key)
                cmp > 0 -> get(node.right, key)
                else -> node.value
            }
        }
    }

    private fun put(node: Node?, key: K, value: V): Node = when (node) {
        null -> Node(key, value, 1)
        else -> {
            val cmp = key.compareTo(node.key)
            when {
                cmp < 0 -> node.left = put(node.left, key, value)
                cmp > 0 -> node.right = put(node.right, key, value)
                else -> node.value = value
            }
            node.n = size(node.left) + size(node.right) + 1
            node
        }
    }

    private fun delete(node: Node?, key: K): Node? {
        var node: Node = node ?: return null
        val cmp: Int = key.compareTo(node.key)
        when {
            cmp < 0 -> node.left = delete(node.left, key)
            cmp > 0 -> node.right = delete(node.right, key)
            else -> {
                if (node.right == null) return node.left
                if (node.left == null) return node.right
                val t: Node = node
                node = min(t.right) ?: return null
                node.right = deleteMin(t.right)
                node.left = t.left
            }
        }
        node.n = size(node.left) + size(node.right) + 1
        return node
    }

    private fun deleteMin(node: Node?): Node? = when (node?.left) {
        null -> node?.right
        else -> {
            node.left = deleteMin(node.left)
            node.n = size(node.left) + size(node.right) + 1
            node
        }
    }

    private fun min(node: Node?): Node? = when (node?.left) {
        null -> node
        else -> min(node.left)
    }

    private fun max(node: Node?): Node? = when (node?.right) {
        null -> node
        else -> max(node.right)
    }

    private fun keys(node: Node?, queue: Queue<K>, lo: K, hi: K) {
        if (node == null) return
        val cmpStart: Int = lo.compareTo(node.key)
        val cmpEnd: Int = hi.compareTo(node.key)
        if (cmpStart < 0) keys(node.left, queue, lo, hi)
        if (cmpStart <= 0 && cmpEnd >= 0) queue.offer(node.key)
        if (cmpEnd > 0) keys(node.right, queue, lo, hi)
    }

}