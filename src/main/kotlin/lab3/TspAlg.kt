package lab3

import org.uncommons.watchmaker.framework.EvolutionaryOperator
import org.uncommons.watchmaker.framework.SteadyStateEvolutionEngine
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline
import org.uncommons.watchmaker.framework.selection.RankSelection
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection
import org.uncommons.watchmaker.framework.selection.SigmaScaling
import org.uncommons.watchmaker.framework.termination.GenerationCount
import java.util.*

object TspAlg {
    private var bestValue = Double.MAX_VALUE
    private var bestIter = -1

    @JvmStatic
    fun main(args: Array<String>) {
        val problemSize = 343
        val problem = "pma$problemSize" // name of problem or path to input file

        val populationSize = 200 // size of population
        val generations = 10000 // number of generations

        val random = Random() // random

        val factory = TspFactory(problemSize) // generation of solutions

        val operators = listOf<EvolutionaryOperator<TspSolution>>(
            TspCrossover(problemSize), // Crossover,
            TspMutation(problemSize) // Mutation
        )
        val pipeline = EvolutionPipeline(operators)

        val selection = RankSelection() // Selection operator

        val evaluator = TspFitnessFunction(problem) // Fitness function

        val algorithm = SteadyStateEvolutionEngine(
            factory, pipeline, evaluator, selection,
            populationSize, false, random
        )
        algorithm.addEvolutionObserver { populationData ->
            val bestFit = populationData.bestCandidateFitness
            if (bestFit < bestValue) {
                bestValue = bestFit
                bestIter = populationData.generationNumber
            }
            println("Generation " + populationData.generationNumber + ": " + bestFit)
            val best = populationData.bestCandidate as TspSolution
            println("\tBest solution = ${best.permutation}")
        }

        val terminate = GenerationCount(generations)
        algorithm.evolve(populationSize, 30, terminate)
        println("===================")
        println("Best fit: $bestValue")
        println("Best iteration: $bestIter")
    }
}