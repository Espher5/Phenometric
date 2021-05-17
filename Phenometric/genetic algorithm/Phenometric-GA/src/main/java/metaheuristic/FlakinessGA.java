package metaheuristic;

import individual.Individual;
import population.Population;
import results.GAResults;

public class FlakinessGA<T extends Individual> extends GenerationalGA<T> {

    public FlakinessGA(GASetting<T> gaSetting) {
        super(gaSetting);
    }

    @Override
    public GAResults<T> run() throws CloneNotSupportedException {
        Population<T> firstGeneration = getPopulationInitializer().initialize();
        this.addGeneration(firstGeneration);

        this.getFitnessFunction().evaluate(firstGeneration);
        System.out.println("Gen 1) " + "(Average fitness: " + firstGeneration.getAverageFitness() + ") (CurrentAvg)");
        int currentIteration = 1;
        do {
            Population<T> currentPopulation = getLastGeneration();
            Population<T> matingPool = getSelectionOperator().apply(currentPopulation);
            Population<T> offsprings = getCrossoverOperator().apply(matingPool);
            Population<T> newGeneration = getMutationOperator().apply(offsprings);

            this.getFitnessFunction().evaluate(newGeneration);
            this.addGeneration(newGeneration);
            currentIteration++;
            System.out.println("Gen " + currentIteration + ") " +
                    "(Average fitness: " + currentPopulation.getAverageFitness() +
                    ") (Best individual: " + currentPopulation.getBestIndividual().getFitness() + ")");
        } while (!getStoppingCondition().checkStop(this));
        return new GAResults<>(this);
    }
}
