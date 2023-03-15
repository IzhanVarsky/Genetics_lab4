package lab3

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory
import java.util.*

class TspFactory(private val problemSize: Int) : AbstractCandidateFactory<TspSolution>() {
    override fun generateRandomCandidate(random: Random): TspSolution =
        TspSolution(MutableList(problemSize) { it }.shuffled())
}