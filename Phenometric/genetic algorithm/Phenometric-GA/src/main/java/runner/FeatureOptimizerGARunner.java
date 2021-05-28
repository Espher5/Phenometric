package runner;

import fitness.AccuracyFunction;
import individual.FeatureIndividual;
import individual.generator.IndividualGenerator;
import individual.generator.RandomBinaryFeatureGenerator;
import metaheuristic.FlakinessGA;
import metaheuristic.GenerationalGA;
import operator.crossover.SinglePointCrossover;
import operator.mutation.BitFlipMutation;
import operator.selection.RouletteWheelSelection;
import population.initializer.PopulationInitializer;
import population.initializer.UpperBoundedPopulationInitializer;
import results.GAResults;
import stopping.MaxIterationsStoppingCondition;
import stopping.MaxNoImprovementsStoppingCondition;
import stopping.MultipleStoppingCondition;

import java.util.Map;
import java.util.Random;

public class FeatureOptimizerGARunner extends GARunner {
    @Override
    public void run(Map<String, Double> input) throws CloneNotSupportedException {
        int numberOfIndividuals = input.get("numberOfIndividuals").intValue();
        int individualSize = input.get("individualSize").intValue();
        int maxIterations = input.get("maxIterations").intValue();
        int maxIterationsNoImprovements = input.get("maxIterationsNoImprovements").intValue();
        double crossoverProbability = input.get("crossoverProbability");
        double mutationProbability = input.get("mutationProbability");

        Random random =  new Random();
        AccuracyFunction fitnessFunction = new AccuracyFunction();
        IndividualGenerator<FeatureIndividual> individualGenerator = new RandomBinaryFeatureGenerator(individualSize, random);
        PopulationInitializer<FeatureIndividual> populationInitializer = new UpperBoundedPopulationInitializer<>(numberOfIndividuals, individualGenerator);
        RouletteWheelSelection<FeatureIndividual> selectionOperator = new RouletteWheelSelection<>(random);
        SinglePointCrossover<FeatureIndividual> crossoverOperator = new SinglePointCrossover<>(crossoverProbability, random);
        BitFlipMutation<FeatureIndividual> mutationOperator = new BitFlipMutation<>(mutationProbability, random);
        MultipleStoppingCondition stoppingCondition = new MultipleStoppingCondition();
        stoppingCondition.add(new MaxIterationsStoppingCondition(maxIterations));
        stoppingCondition.add(new MaxNoImprovementsStoppingCondition(maxIterationsNoImprovements));


        GenerationalGA.GASetting<FeatureIndividual> gaSetting = new GenerationalGA.GASetting<>(fitnessFunction, populationInitializer,
                selectionOperator, crossoverOperator, mutationOperator, stoppingCondition);
        FlakinessGA<FeatureIndividual> geneticAlgorithm = new FlakinessGA<>(gaSetting);
        GAResults<FeatureIndividual> gaResults = geneticAlgorithm.run();
        FeatureIndividual bestIndividual = gaResults.getBestIndividual();
        System.out.printf("Search terminated in %d/%d iterations.%n", gaResults.getNumberOfIterations(), maxIterations);
        System.out.printf("Best individual is %s, with fitness %f.%n", bestIndividual.getEncoding(), bestIndividual.getFitness());
    }
}
