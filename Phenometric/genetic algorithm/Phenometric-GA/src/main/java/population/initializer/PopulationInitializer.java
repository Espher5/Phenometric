package population.initializer;

import individual.Individual;
import population.Population;

public abstract class PopulationInitializer<T extends Individual> {
    public abstract Population<T> initialize();
}
