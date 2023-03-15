package lab3

import org.uncommons.watchmaker.framework.FitnessEvaluator
import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

typealias Point = Pair<Int, Int>

class TspFitnessFunction(private val problem: String) : FitnessEvaluator<TspSolution> {
    private val points: List<Point> = readProblem()

    private fun readProblem(): List<Pair<Int, Int>> =
        File("$problem.tsp").readLines()
            .drop(8).dropLast(1)
            .map {
                val lst = it.split(" ").drop(1).map(String::toInt)
                Pair(lst[0], lst[1])
            }

    private fun dist(p1: Point, p2: Point): Double =
        sqrt(
            (p1.first - p2.first).toDouble().pow(2) +
                    (p1.second - p2.second).toDouble().pow(2)
        )

    override fun getFitness(solution: TspSolution, list: List<TspSolution>): Double {
        val permutation = solution.permutation
        var lastPointInd = permutation.first()
        var sum = 0.0
        for (pointInd in permutation.drop(1) + permutation.first()) {
            sum += dist(points[lastPointInd], points[pointInd])
            lastPointInd = pointInd
        }
        return sum
    }

    override fun isNatural(): Boolean {
        return false
    }
}