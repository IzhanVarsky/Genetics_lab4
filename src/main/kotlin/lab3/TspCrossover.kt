package lab3

import org.uncommons.watchmaker.framework.operators.AbstractCrossover
import java.util.Random

open class TspCrossover(private val problemSize: Int) : AbstractCrossover<TspSolution>(1) {
    public override fun mate(p1: TspSolution, p2: TspSolution, i: Int, random: Random): List<TspSolution> =
        method1(p1, p2, random)

    private fun method1(p1: TspSolution, p2: TspSolution, random: Random): List<TspSolution> {
        val (a, b) = genTwoIndices(problemSize, random)
        //        val (a, b) = 3 to 7
        val subListLeft1 = mutableListOf<Int>()
        val subListMiddle1 = p1.permutation.subList(a, b)
        val subListRight1 = mutableListOf<Int>()
        val subListMiddle2 = mutableListOf<Int>()

        for (range in listOf(
            b until problemSize,
            0 until b
        )) {
            for (ind in range) {
                val curPointNum = p2.permutation[ind]
                if (curPointNum !in subListMiddle1) {
                    if (subListRight1.size < problemSize - b) {
                        subListRight1 += curPointNum
                    } else {
                        subListLeft1 += curPointNum
                    }
                } else {
                    subListMiddle2 += curPointNum
                }
            }
        }
        return listOf(
            TspSolution(subListLeft1 + subListMiddle1 + subListRight1),
            TspSolution(
                p1.permutation.subList(0, a) +
                        subListMiddle2 +
                        p1.permutation.subList(b, problemSize)
            ),
        )
    }

    private fun getOneOffspring(p1: TspSolution, p2: TspSolution, random: Random): TspSolution {
        val (a, b) = genTwoIndices(problemSize, random)
        val subListLeft1 = mutableListOf<Int>()
        val subListMiddle1 = p1.permutation.subList(a, b)
        val subListRight1 = mutableListOf<Int>()

        for (range in listOf(
            b until problemSize,
            0 until b
        )) {
            for (ind in range) {
                val curPointNum = p2.permutation[ind]
                if (curPointNum !in subListMiddle1) {
                    if (subListRight1.size < problemSize - b) {
                        subListRight1 += curPointNum
                    } else {
                        subListLeft1 += curPointNum
                    }
                }
            }
        }
        return TspSolution(subListLeft1 + subListMiddle1 + subListRight1)
    }

    private fun method2(p1: TspSolution, p2: TspSolution, random: Random): List<TspSolution> {
        return listOf(
            getOneOffspring(p1, p2, random),
            getOneOffspring(p2, p1, random),
        )
    }
}