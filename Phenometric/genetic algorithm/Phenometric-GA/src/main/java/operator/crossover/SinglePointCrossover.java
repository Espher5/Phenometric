package operator.crossover;

import individual.EncodedIndividual;

import java.util.Random;


/**
 * Given a test case entry, this operator randomly generates two split points, one among the keywords and the other
 * among the tokens, and redistributes the cut values between the offsprings
 */
public class SinglePointCrossover<T extends EncodedIndividual<String>> extends CrossoverOperator<T> {
    public SinglePointCrossover(double crossoverProbability, Random random) {
        super(crossoverProbability, random);
    }

    @Override
    protected Pairing cross(Pairing pairing) throws CloneNotSupportedException {
        String firstEncoding = pairing.firstIndividual.getEncoding();
        String secondEncoding = pairing.secondIndividual.getEncoding();

        int cutPoint = getRandom().nextInt(firstEncoding.length() - 1) + 1;
        String firstEncodingLeft = firstEncoding.substring(0, cutPoint);
        String firstEncodingRight = firstEncoding.substring(cutPoint);
        String secondEncodingLeft = secondEncoding.substring(0, cutPoint);
        String secondEncodingRight = secondEncoding.substring(cutPoint);

        T offspring1 = (T) pairing.firstIndividual.clone();
        offspring1.setEncoding(firstEncodingLeft + secondEncodingRight);
        T offspring2 = (T) pairing.secondIndividual.clone();
        offspring2.setEncoding(secondEncodingLeft + firstEncodingRight);
        return new Pairing(offspring1, offspring2);
    }
}
