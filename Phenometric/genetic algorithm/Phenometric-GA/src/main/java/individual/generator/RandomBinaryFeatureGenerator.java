package individual.generator;

import individual.FeatureIndividual;

import java.util.Random;

public class RandomBinaryFeatureGenerator extends IndividualGenerator<FeatureIndividual> {
    private final int individualSize;
    private final Random random;

    public RandomBinaryFeatureGenerator(int individualSize, Random random) {
        this.individualSize = individualSize;
        this.random = random;
    }

    @Override
    public FeatureIndividual generateIndividuals() {
        StringBuilder featureString = new StringBuilder();
        for(int i = 0; i < individualSize; i++) {
            if(random.nextDouble() < 0.3) {
                featureString.append("0");
            } else {
                featureString.append("1");
            }
        }
        // We always consider the label feature
        featureString.append("1");
        return new FeatureIndividual(featureString.toString());
    }
}
