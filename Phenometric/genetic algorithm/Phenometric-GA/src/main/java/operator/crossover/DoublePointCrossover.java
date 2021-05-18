package operator.crossover;

import individual.EncodedIndividual;
import individual.beans.IndividualEncodingBean;
import individual.beans.KeywordBean;
import individual.beans.TokenBean;

import java.util.List;
import java.util.Random;


/**
 * Given a test case entry, this operator randomly generates two split points, one among the keywords and the other
 * among the tokens, and redistributes the cut values between the offsprings
 */
public class DoublePointCrossover<T extends EncodedIndividual<IndividualEncodingBean>> extends CrossoverOperator<T> {
    public DoublePointCrossover(double crossoverProbability, Random random) {
        super(crossoverProbability, random);
    }

    /*
     * Determines how lines of code, keywords, and tokens are redistributed from the parents to
     * the two offsprings
     */
    @Override
    protected Pairing cross(Pairing pairing) throws CloneNotSupportedException {
        IndividualEncodingBean firstEncoding = pairing.firstIndividual.getEncoding();
        IndividualEncodingBean secondEncoding = pairing.secondIndividual.getEncoding();

        T offspring1 = (T) pairing.firstIndividual.clone();
        IndividualEncodingBean offspringEncoding1 = offspring1.getEncoding();
        T offspring2 = (T) pairing.secondIndividual.clone();
        IndividualEncodingBean offspringEncoding2 = offspring2.getEncoding();

        // Splits the java keywords and updates the total count
        int keywordSplitPoint = getRandom().nextInt(firstEncoding.getJavaKeywords().size() - 1) + 1;
        List<KeywordBean> keywords1 = offspringEncoding1.getJavaKeywords();
        List<KeywordBean> keywords2 = offspringEncoding2.getJavaKeywords();

        int keywordCount1 = 0;
        int keywordCount2 = 0;
        for(int i = keywordSplitPoint; i < keywords1.size(); i++) {
            keywordCount1 += keywords1.get(i).getCount();
            keywordCount2 += keywords2.get(i).getCount();
            keywords1.add(i, keywords2.get(i));
            keywords2.add(i, keywords1.get(i + 1));
            keywords2.remove(i + 1);
            keywords1.remove(i + 1);
        }
        int oldKeywordCount1 = offspringEncoding1.getKeywordCount();
        int oldKeywordCount2 = offspringEncoding2.getKeywordCount();
        offspringEncoding1.setKeywordCount(oldKeywordCount1 - keywordCount1 + keywordCount2);
        offspringEncoding2.setKeywordCount(oldKeywordCount2 - keywordCount2 + keywordCount1);

        // Splits the tokens
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
