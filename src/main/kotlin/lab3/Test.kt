package lab3

import java.util.*

fun main() {
    val problemSize = 9
    val p1 = TspSolution(MutableList(problemSize) { it + 1 })
    val p2 = TspSolution(listOf(3, 7, 5, 2, 8, 1, 4, 9, 6))
    val random = Random()
    val cross = TspCrossover(problemSize)
    val out = cross.mate(p1, p2, 1, random)
    println(out[0].permutation)
    println(out[1].permutation)
}