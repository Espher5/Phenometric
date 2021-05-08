package results;

import individual.Individual;
import metaheuristic.GenerationalGA;
import population.Population;

public class GAResults<T extends Individual> {
    private final GenerationalGA<T> ga;

    public GAResults(GenerationalGA<T> ga) {
        this.ga = ga;
    }

    public GenerationalGA<T> getGa() {
        return ga;
    }

    public Population<T> getBestGeneration() {
        return ga.getBestGeneration();
    }

    public T getBestIndividual() {
        return getBestGeneration().getBestIndividual();
    }

    public int getNumberOfIterations() {
        return getGa().getNumberOfGenerations();
    }
}