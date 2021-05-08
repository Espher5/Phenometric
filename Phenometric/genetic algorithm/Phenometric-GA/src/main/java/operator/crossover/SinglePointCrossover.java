package operator.crossover;

import individual.EncodedIndividual;
import individual.beans.IndividualEncodingBean;
import individual.beans.TokenBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointCrossover<T extends EncodedIndividual<IndividualEncodingBean>> extends CrossoverOperator<T> {
    public SinglePointCrossover(double crossoverProbability, Random random) {
        super(crossoverProbability, random);
    }

    /*
     *
     */
    @Override
    protected Pairing cross(Pairing pairing) throws CloneNotSupportedException {
        IndividualEncodingBean firstEncoding = pairing.firstIndividual.getEncoding();
        IndividualEncodingBean secondEncoding = pairing.secondIndividual.getEncoding();

        T offspring1 = (T) pairing.firstIndividual.clone();
        IndividualEncodingBean offspringEncoding1 = offspring1.getEncoding();
        T offspring2 = (T) pairing.secondIndividual.clone();
        IndividualEncodingBean offspringEncoding2 = offspring2.getEncoding();


        // Swaps the two token sections
        int tokenSplitPoint = getRandom().nextInt(firstEncoding.getTokens().size() - 1) + 1;
        List<TokenBean> tokens1 = offspringEncoding1.getTokens();
        List<TokenBean> tokens2 = offspringEncoding2.getTokens();
        for(int i = tokenSplitPoint; i < tokens1.size(); i++) {
            tokens1.add(i, tokens2.get(i));
            tokens2.add(i, tokens1.get(i + 1));
            tokens2.remove(i + 1);
            tokens1.remove(i + 1);
        }
        offspringEncoding1.setTokens(tokens1);
        offspringEncoding2.setTokens(tokens2);

        // For now keywords are not crossed
        // TODO: 08/05/2021

        // Calculates the percentage of own tokens removed from each individual and updates the loc
        int splitPercentage =  (tokens1.size() - tokenSplitPoint) * 100 / tokens1.size();
        int loc1 = offspringEncoding1.getLoc();
        int loc2 = offspringEncoding2.getLoc();
        loc1 = loc1 - (loc1 * splitPercentage / 100) + (loc2 * splitPercentage / 100);
        loc2 = loc2 - (loc2 * splitPercentage / 100) + (loc1 * splitPercentage / 100);
        offspringEncoding1.setLoc(loc1);
        offspringEncoding2.setLoc(loc2);

        return new Pairing(offspring1, offspring2);
    }
}
