package operator.mutation;

import individual.EncodedIndividual;
import individual.Individual;
import individual.beans.IndividualEncodingBean;
import individual.beans.TokenBean;

import java.util.List;
import java.util.Random;

public class TokenFlipMutation<T extends EncodedIndividual<IndividualEncodingBean>> extends MutationOperator<T> {
    public TokenFlipMutation(double mutationProbability, Random random) {
        super(mutationProbability, random);
    }

    @Override
    protected T mutate(T individual) throws CloneNotSupportedException {
        IndividualEncodingBean individualEncoding = individual.getEncoding();
        List<TokenBean> tokens = individualEncoding.getTokens();
        int numToFlip = tokens.size() * 5 / 100;

        for(int i = 0; i < numToFlip; i++) {
            int index = getRandom().nextInt(tokens.size());
            TokenBean tb = tokens.get(index);
            if(tb.getPresence() == 0) {
                tb.setPresence(1);
            } else {
                tb.setPresence(0);
            }
            tokens.add(index, tb);
            tokens.remove(i + 1);
        }

        T mutatedIndividual = (T) individual.clone();
        return mutatedIndividual;
    }
}
