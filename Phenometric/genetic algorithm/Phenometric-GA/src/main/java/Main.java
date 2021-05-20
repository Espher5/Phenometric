import runner.FlakinessGARunner;

import java.util.*;

public class Main {
    private static final double NUM_OF_INDIVIDUALS = 2804;
    private static final double NUM_ITERATIONS = 200.0;
    private static final double NUM_ITERATIONS_NO_IMPROVEMENTS = 20.0;
    private static final double CROSSOVER_PROBABILITY = 0.5;
    private static final double MUTATION_PROBABILITY = 0.3;

    public static void main(String[] args) throws CloneNotSupportedException {
        Map<String, Double> inputMap = new HashMap<>();
        inputMap.put("numberOfIndividuals", NUM_OF_INDIVIDUALS);
        inputMap.put("maxIterations",NUM_ITERATIONS);
        inputMap.put("maxIterationsNoImprovements", NUM_ITERATIONS_NO_IMPROVEMENTS);
        inputMap.put("crossoverProbability", CROSSOVER_PROBABILITY);
        inputMap.put("mutationProbability", MUTATION_PROBABILITY);
        new FlakinessGARunner().run(inputMap);
    }
}