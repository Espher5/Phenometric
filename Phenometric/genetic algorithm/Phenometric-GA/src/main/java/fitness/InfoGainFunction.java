package fitness;

import individual.beans.IndividualEncodingBean;
import individual.TestCaseIndividual;
import individual.beans.KeywordBean;
import individual.beans.TokenBean;

import java.util.ArrayList;
import java.util.List;

/**
 * The idea behind this fitness function is to compute the fitness score based on the presence
 * of the most common flakiness-inducing terms inside of the test case taken in consideration.
 *
 * The 20 most common tokens, along with their weights, expressed by the infoGain value they provided
 * during the Ml testing, are put into a map, to later be confronted with the test case's actual tokens.
 *
 * The fitness score is then calculated as a weighted sum of these common tokens.
 */
public class InfoGainFunction extends FitnessFunction<TestCaseIndividual> {
    private static final double LOC_INFOGAIN = 0.0978;
    private final List<InfoGainParam> params;

    public InfoGainFunction() {
        super(true);
        params = initParameters();
    }

    public void evaluate(TestCaseIndividual individual) {
        IndividualEncodingBean individualEncoding = individual.getEncoding();
        int loc = individualEncoding.getLoc();
        // Keywords are not currently used to calculate the fitness score
        List<KeywordBean> javaKeywords = individualEncoding.getJavaKeywords();
        int keywordCount = individualEncoding.getKeywordCount();
        List<TokenBean> tokens = individualEncoding.getTokens();
        int label = individualEncoding.getLabel();

        // Fitness score calculation
        double fitness = 0.0;
        for(TokenBean tb : tokens) {
            for(InfoGainParam param : params) {
                if(tb.getName().equals(param.getName()) && tb.getPresence() == 1) {
                    fitness += param.getInfogain() * param.getOccurrences() / param.getPosition();
                }
            }
        }

        fitness += loc * LOC_INFOGAIN;
        individual.setFitness(fitness);
    }


    private List<InfoGainParam> initParameters() {
        List<InfoGainParam> params = new ArrayList<>();
        params.add(new InfoGainParam("job", 20, 0.2053, 254));
        params.add(new InfoGainParam("table", 19, 0.1449, 406));
        params.add(new InfoGainParam("id", 18, 0.1419, 522));
        params.add(new InfoGainParam("action", 17, 0.1366, 387));
        params.add(new InfoGainParam("oozie", 16, 0.1360, 274));
        params.add(new InfoGainParam("services", 15, 0.1310, 371));
        params.add(new InfoGainParam("coord", 14, 0.1192, 307));
        params.add(new InfoGainParam("getid", 13, 0.1077, 287));
        params.add(new InfoGainParam("coordinator", 12, 0.1070, 258));
        params.add(new InfoGainParam("xml", 11, 0.1062, 147));
        //params.add(new InfoGainParam("loc", 10, 0.0978, 0));
        params.add(new InfoGainParam("workflow", 9, 0.0914, 207));
        params.add(new InfoGainParam("getstatus", 8, 0.0885, 246));
        //params.add(new InfoGainParam("throws", 7, 0.0874, 3));
        params.add(new InfoGainParam("record", 6, 0.0845, 296));
        params.add(new InfoGainParam("jpa", 5, 0.0781, 207));
        params.add(new InfoGainParam("jpaservice", 4, 0.0753, 200));
        params.add(new InfoGainParam("service", 3, 0.0733, 367));
        params.add(new InfoGainParam("wf", 2, 0.0721, 192));
        params.add(new InfoGainParam("coordinatorjob", 1, 0.0689, 184));
        return params;
    }
}