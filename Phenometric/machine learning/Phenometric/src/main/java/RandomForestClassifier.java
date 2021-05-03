import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.core.tokenizers.WordTokenizer;
import weka.core.stemmers.NullStemmer;
import weka.core.stopwords.Null;
import weka.filters.Filter;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class RandomForestClassifier {
    public static void main(String[] args) {
        Instances data = preprocess();

        if(data != null) {
            classify(data);
        }

    }

    public static Instances preprocess() {
        String datasetPath = "C:/Users/ikime/Desktop/Temp/dataset.arff";
        try {
            DataSource source = new DataSource(datasetPath);
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);

            StringToWordVector filter = new StringToWordVector();
            filter.setInputFormat(instances);
            filter.setWordsToKeep(1000);
            filter.setMinTermFreq(1);
            filter.setDoNotCheckCapabilities(false);
            filter.setInvertSelection(false);
            filter.setLowerCaseTokens(false);
            filter.setStemmer(new NullStemmer());
            filter.setStopwordsHandler(new Null());
            filter.setPeriodicPruning(-1.0);
            filter.setIDFTransform(false);
            filter.setTFTransform(false);

            WordTokenizer tokenizer = new WordTokenizer();
            //tokenizer.setDelimiters("\"\r\n\t.,;:\"\"()?!\"");
            filter.setTokenizer(tokenizer);

            Instances processedInstances = Filter.useFilter(instances, filter);

            CSVSaver saver = new CSVSaver();
            saver.setInstances(processedInstances);
            saver.setFile(new File("./dataset.csv"));
            saver.writeBatch();

            return processedInstances;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void classify(Instances data) {
        int classIndex = 0;
        int trainsize = (int) Math.round(data.numInstances() * 0.8);
        int testSize = data.numInstances() - trainsize;
        Instances trainSet = new Instances(data, 0, trainsize);
        Instances testSet = new Instances(data, trainsize, testSize);

        try {
            RandomForest classifier = new RandomForest();
            classifier.setMaxDepth(1000);
            classifier.buildClassifier(trainSet);

            Evaluation eval = new Evaluation(data);
            eval.evaluateModel(classifier, testSet);

            double tp = eval.numTruePositives(classIndex);
            double tn = eval.numTrueNegatives(classIndex);
            double fp = eval.numFalsePositives(classIndex);
            double fn = eval.numFalseNegatives(classIndex);

            double accuracy = (tp + tn) / (tp + fp + fn + tn);
            double precision = eval.precision(classIndex);
            double recall = eval.recall(classIndex);
            double fmeasure = 2 * ((precision * recall) / (precision + recall));
            double auc = eval.areaUnderROC(classIndex);
            double MCC = ((tp * tn) - (fp * fn)) / (Math.sqrt(((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn))));

            System.out.println("TP;TN;FP;FN;Accuracy; Precision; Recall; fmeasure; areaUnderROC; MCC\n");
            System.out.println("TP: " + tp);
            System.out.println("TN: " + tn);
            System.out.println("FP: " + fp);
            System.out.println("FN: " + fn);
            System.out.println("Accuracy: " + accuracy);
            System.out.println();
            System.out.println("Precision: " + precision + " Reference: 0.99");
            System.out.println("Recall: " + recall + " Reference: 0.91");
            System.out.println("FMeasure: " + fmeasure + " Reference: 0.95");
            System.out.println("AUC: "+ auc + " Reference: 0.98");
            System.out.println("MCC: " + MCC + " Reference: 0.90");
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
