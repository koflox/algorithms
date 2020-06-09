import structures.BinaryTree
import java.util.*
import kotlin.collections.HashMap

fun main() {
    val rnd = Random()
    val array = Array(5) { i -> (i + rnd.nextInt(100)) }

    val bt = BinaryTree<Int, Int>().apply {
        put(1, 2)
        put(2, 4)
        put(3, 9)
        put(4, 16)
    }
    bt.keys().forEach {
        println("key: $it")
    }
    println("get(3): ${bt.get(3)}")
    bt.put(3, 3)
    println("get(3): ${bt.get(3)}")
    bt.delete(3)
//    bt.keys().forEach {
//        println("key: $it")
//    }
    bt.put(6, 25)
    bt.put(10, 25)
    bt.put(20, 25)
    bt.put(15, 25)
    bt.put(12, 25)
    bt.put(7, 25)
    bt.keys().forEach {
        println("key: $it")
    }
}