package metaheuristic;

import fitness.FitnessFunction;
import individual.Individual;
import operator.crossover.CrossoverOperator;
import operator.mutation.MutationOperator;
import operator.selection.SelectionOperator;
import population.Population;
import population.initializer.PopulationInitializer;
import results.GAResults;
import stopping.StoppingCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public abstract class GenerationalGA<T extends Individual> {
    private final GASetting<T> gaSetting;
    private final Stack<Population<T>> generations;

    public GenerationalGA(GASetting<T> gaSetting) {
        this.gaSetting = gaSetting;
        this.generations = new Stack<>();
    }

    public abstract GAResults<T> run() throws CloneNotSupportedException;

    public Population<T> addGeneration(Population<T> newGeneration) {
        return generations.push(newGeneration);
    }

    public Population<T> getBestGeneration() {
        if (getFitnessFunction().isMaximum()) {
            return generations.stream().max(Population::compareTo).orElse(null);
        } else {
            return generations.stream().min(Population::compareTo).orElse(null);
        }
    }

    public Population<T> getLastGeneration() {
        return generations.peek();
    }

    public Population<T> getLastButGeneration(int n) {
        List<Population<T>> list = new ArrayList<>(generations);
        Collections.reverse(list);
        return list.get(n);
    }

    public List<Population<T>> getLastGenerations(int n) {
        List<Population<T>> list = new ArrayList<>(generations);
        return list.subList(n - 1, list.size());
    }

    public int getNumberOfGenerations() {
        return generations.size();
    }

    public FitnessFunction<T> getFitnessFunction() {
        return gaSetting.fitnessFunction;
    }

    public PopulationInitializer<T> getPopulationInitializer() {
        return gaSetting.populationInitializer;
    }

    public SelectionOperator<T> getSelectionOperator() {
        return gaSetting.selectionOperator;
    }

    public CrossoverOperator<T> getCrossoverOperator() {
        return gaSetting.crossoverOperator;
    }

    public MutationOperator<T> getMutationOperator() {
        return gaSetting.mutationOperator;
    }

    public StoppingCondition getStoppingCondition() {
        return gaSetting.stoppingCondition;
    }

    public GASetting<T> getGaSetting() {
        return gaSetting;
    }

    public static class GASetting<T extends Individual> {
        private final FitnessFunction<T> fitnessFunction;
        private final PopulationInitializer<T> populationInitializer;
        private final SelectionOperator<T> selectionOperator;
        private final CrossoverOperator<T> crossoverOperator;
        private final MutationOperator<T> mutationOperator;
        private final StoppingCondition stoppingCondition;

        public GASetting(FitnessFunction<T> fitnessFunction, PopulationInitializer<T> populationInitializer,
                         SelectionOperator<T> selectionOperator, CrossoverOperator<T> crossoverOperator,
                         MutationOperator<T> mutationOperator, StoppingCondition stoppingCondition) {
            this.fitnessFunction = fitnessFunction;
            this.populationInitializer = populationInitializer;
            this.selectionOperator = selectionOperator;
            this.crossoverOperator = crossoverOperator;
            this.mutationOperator = mutationOperator;
            this.stoppingCondition = stoppingCondition;
        }
    }
}
