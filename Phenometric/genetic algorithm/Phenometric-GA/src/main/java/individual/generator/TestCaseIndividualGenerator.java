package individual.generator;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import individual.TestCaseIndividual;
import individual.beans.IndividualEncodingBean;
import individual.beans.KeywordBean;
import individual.beans.TokenBean;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestCaseIndividualGenerator extends IndividualGenerator<TestCaseIndividual> {
    @Override
    public List<TestCaseIndividual> generateIndividuals() {
        List<TestCaseIndividual> individuals = new ArrayList<>();
        String datasetPath = "dataset.csv";
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        try(CSVReader reader = new CSVReaderBuilder(new FileReader(datasetPath))
                .withCSVParser(parser)
                .build()
        ) {
            int keywordCountIndex = -1;
            String[] headersLine = reader.readNext();
            // Gets the index of the keywordcount columns
            for(int i = 0; i < headersLine.length; i++) {
                if(headersLine[i].equals("keywordcount")) {
                    keywordCountIndex = i;
                    break;
                }
            }

            List<String[]> rows = reader.readAll();
            for(String[] row: rows) {
                int loc = Integer.parseInt(row[0]);
                int keywordCount = Integer.parseInt(row[keywordCountIndex]);

                List<KeywordBean> keywords = new ArrayList<>();
                for(int i = 1; i <= keywordCountIndex - 1; i++) {
                    keywords.add(new KeywordBean(headersLine[i], Integer.parseInt(row[i])));
                }

                List<TokenBean> tokens = new ArrayList<>();
                for(int i = keywordCountIndex + 1; i < row.length - 1; i++) {
                    tokens.add(new TokenBean(headersLine[i], Integer.parseInt(row[i])));
                }
                // Flaky = 0, Non flaky = 1 -> we want to maximize the non-flakiness
                int label = Integer.parseInt(row[row.length - 1]);

                IndividualEncodingBean ib = new IndividualEncodingBean(loc, keywords, keywordCount, tokens, label);
                individuals.add(new TestCaseIndividual(ib));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace(System.err);
        }
        return individuals;
    }
}
