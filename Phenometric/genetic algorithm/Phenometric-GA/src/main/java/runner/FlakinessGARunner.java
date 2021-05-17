package runner;

import fitness.InfoGainFunction;
import individual.TestCaseIndividual;
import individual.generator.IndividualGenerator;
import individual.generator.TestCaseIndividualGenerator;
import metaheuristic.FlakinessGA;
import metaheuristic.GenerationalGA;
import operator.crossover.DoublePointCrossover;
import operator.mutation.TokenFlipMutation;
import operator.selection.RouletteWheelSelection;
import population.initializer.PopulationInitializer;
import population.initializer.UpperBoundedPopulationInitializer;
import results.GAResults;
import stopping.MaxIterationsStoppingCondition;
import stopping.MaxNoImprovementsStoppingCondition;
import stopping.MultipleStoppingCondition;

import java.util.Map;
import java.util.Random;

public class FlakinessGARunner extends GARunner {
    @Override
    public void run(Map<String, Double> input) throws CloneNotSupportedException {
        int numberOfIndividuals = input.get("numberOfIndividuals").intValue();
        int maxIterations = input.get("maxIterations").intValue();
        int maxIterationsNoImprovements = input.get("maxIterationsNoImprovements").intValue();
        double crossoverProbability = input.get("crossoverProbability");
        double mutationProbability = input.get("mutationProbability");

        Random random =  new Random();
        InfoGainFunction fitnessFunction = new InfoGainFunction();
        IndividualGenerator<TestCaseIndividual> individualGenerator = new TestCaseIndividualGenerator();
        PopulationInitializer<TestCaseIndividual> populationInitializer = new UpperBoundedPopulationInitializer<>(numberOfIndividuals, individualGenerator);
        RouletteWheelSelection<TestCaseIndividual> selectionOperator = new RouletteWheelSelection<>(random);
        DoublePointCrossover<TestCaseIndividual> crossoverOperator = new DoublePointCrossover<>(crossoverProbability, random);
        TokenFlipMutation<TestCaseIndividual> mutationOperator = new TokenFlipMutation<>(mutationProbability, random);
        MultipleStoppingCondition stoppingCondition = new MultipleStoppingCondition();
        stoppingCondition.add(new MaxIterationsStoppingCondition(maxIterations));
        stoppingCondition.add(new MaxNoImprovementsStoppingCondition(maxIterationsNoImprovements));


        GenerationalGA.GASetting<TestCaseIndividual> gaSetting = new GenerationalGA.GASetting<>(fitnessFunction, populationInitializer,
                selectionOperator, crossoverOperator, mutationOperator, stoppingCondition);
        FlakinessGA<TestCaseIndividual> geneticAlgorithm = new FlakinessGA<>(gaSetting);
        GAResults<TestCaseIndividual> gaResults = geneticAlgorithm.run();
        TestCaseIndividual bestIndividual = gaResults.getBestIndividual();
        System.out.printf("Search terminated in %d/%d iterations.%n", gaResults.getNumberOfIterations(), maxIterations);
        System.out.printf("Best individual is %s, with fitness %f.%n", bestIndividual.getEncoding(), bestIndividual.getFitness());
    }
}
