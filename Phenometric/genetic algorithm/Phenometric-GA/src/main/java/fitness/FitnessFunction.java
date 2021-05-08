package fitness;

import individual.Individual;
import population.Population;

import java.util.Collections;

public abstract class FitnessFunction<T extends Individual> {
    private final boolean isMaximum;

    public FitnessFunction(boolean isMaximum) {
        this.isMaximum = isMaximum;
    }

    public void evaluate(Population<T> population) {
        for(T individual: population) {
            evaluate(individual);
        }
        T bestIndividual;
        if(isMaximum) {
            bestIndividual = Collections.max(population);
        } else {
            bestIndividual = Collections.min(population);
        }
        population.setBestIndividual(bestIndividual);
    }

    public abstract void evaluate(T individual);

    public boolean isMaximum() {
        return this.isMaximum;
    }
}
