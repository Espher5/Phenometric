package stopping;

import metaheuristic.GenerationalGA;
import population.Population;

import java.util.List;

public class MaxNoImprovementsStoppingCondition extends StoppingCondition {
    private final int tolerance;

    public MaxNoImprovementsStoppingCondition(int tolerance) {
        this.tolerance = Math.max(tolerance, 1);
    }

    @Override
    public boolean checkStop(GenerationalGA<?> ga) {
        if(ga.getNumberOfGenerations() <= tolerance) {
            return false;
        }

        List<? extends Population<?>> windowGenerations = ga.getLastGenerations(tolerance);
        Population<?> bestWindowGeneration = windowGenerations.stream()
                .max(Population::compareTo)
                .orElse(null);
        if(bestWindowGeneration == null) {
            return false;
        }
        Population<?> referenceGeneration = ga.getLastButGeneration(tolerance);
        return bestWindowGeneration.compareTo(referenceGeneration) <= 0;
    }

    public int getMaxIterations() {
        return tolerance;
    }
}
