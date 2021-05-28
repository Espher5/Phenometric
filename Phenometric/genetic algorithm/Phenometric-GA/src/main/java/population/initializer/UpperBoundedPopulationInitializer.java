package population.initializer;

import individual.Individual;
import individual.generator.IndividualGenerator;
import population.Population;
import population.UpperBoundedPopulation;

import java.util.List;

public class UpperBoundedPopulationInitializer<T extends Individual> extends PopulationInitializer<T> {
    private final int numberOfIndividuals;
    private final IndividualGenerator<T> individualGenerator;

    public UpperBoundedPopulationInitializer(int numberOfIndividuals, IndividualGenerator<T> individualGenerator) {
        this.numberOfIndividuals = Math.max(numberOfIndividuals, 1);
        this.individualGenerator = individualGenerator;
    }

    @Override
    public Population<T> initialize() {
        Population<T> population = new UpperBoundedPopulation<>(0, numberOfIndividuals);
        for(int i = 0; i < numberOfIndividuals; i++) {
            T individual = individualGenerator.generateIndividuals();
            population.add(individual);
        }
        return population;
    }
}
