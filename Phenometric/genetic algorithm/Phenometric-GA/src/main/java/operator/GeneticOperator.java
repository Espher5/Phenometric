package operator;

import individual.Individual;
import population.Population;

import java.util.Random;

public abstract class GeneticOperator<T extends Individual> {
    private final Random random;

    public GeneticOperator(Random random) {
        this.random = random;
    }

    public abstract Population<T> apply(Population<T> population) throws CloneNotSupportedException;

    public Random getRandom() {
        return random;
    }
}
