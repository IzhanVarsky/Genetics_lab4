package lab3

import org.uncommons.watchmaker.framework.EvolutionaryOperator
import java.util.*

class TspMutation(private val problemSize: Int) : EvolutionaryOperator<TspSolution> {
    override fun apply(population: List<TspSolution>, random: Random): List<TspSolution> =
        insertMethod(population, random)

    private fun shuffleMethod(population: List<TspSolution>, random: Random) =
        population.map {
            val (a, b) = genTwoIndices(problemSize, random)
            TspSolution(
                it.permutation.subList(0, a) +
                        it.permutation.subList(a, b).shuffled() +
                        it.permutation.subList(b, problemSize)
            )
        }

    private fun inverseMethod(population: List<TspSolution>, random: Random) =
        population.map {
            val (a, b) = genTwoIndices(problemSize, random)
            TspSolution(
                it.permutation.subList(0, a) +
                        it.permutation.subList(a, b).reversed() +
                        it.permutation.subList(b, problemSize)
            )
        }

    private fun swapMethod(population: List<TspSolution>, random: Random) =
        population.map {
            val out = it.permutation.toMutableList()
            repeat(problemSize / 100) {
                val (a, b) = genTwoIndices(problemSize, random)
                Collections.swap(out, a, b)
            }
            TspSolution(out)
        }

    private fun insertMethod(population: List<TspSolution>, random: Random) =
        population.map {
            val out = it.permutation.toMutableList()
            repeat(problemSize / 100) {
                val (a, b) = genTwoIndices(problemSize, random)
                val gen = out.removeAt(b)
                out.add(a, gen)
            }
            TspSolution(out)
        }
}