package fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import individual.FeatureIndividual;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.Filter;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;


public class AccuracyFunction extends FitnessFunction<FeatureIndividual> {
    private final Instances data;

    public AccuracyFunction() {
        super(true);
        data = loadData();
    }

    public void evaluate(FeatureIndividual individual) {
        Instances consideredInstances = removeFeatures(new Instances(data), individual.getEncoding());
        assert consideredInstances != null;
        double accuracy = classify(consideredInstances);
        individual.setFitness(accuracy);
    }

    private Instances loadData() {
        String datasetPath = "dataset.arff";
        try {
            DataSource source = new DataSource(datasetPath);
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            return instances;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    private Instances removeFeatures(Instances features, String encoding) {
        List<Integer> indicesList = new ArrayList<>();
        StringBuilder builder = new StringBuilder(encoding);
        for(int i = 0; i < builder.length(); i++) {
            if(builder.charAt(i) == '0') {
                indicesList.add(features.attribute(i).index());
            }
        }

        int[] indices = Arrays.stream(indicesList.toArray()).mapToInt(o -> (int) o).toArray();
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndicesArray(indices);
        removeFilter.setInvertSelection(false);
        try {
            removeFilter.setInputFormat(features);
            return Filter.useFilter(features, removeFilter);
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    private double classify(Instances data) {
        int classIndex = 0;
        int trainsize = (int) Math.round(data.numInstances() * 0.8);
        int testSize = data.numInstances() - trainsize;
        Instances trainSet = new Instances(data, 0, trainsize);
        Instances testSet = new Instances(data, trainsize, testSize);
        try {
            NaiveBayes classifier = new NaiveBayes();
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

            System.out.println("TP: " + tp);
            System.out.println("TN: " + tn);
            System.out.println("FP: " + fp);
            System.out.println("FN: " + fn);
            System.out.println("Accuracy: " + accuracy);
            System.out.println("Precision: " + precision + " Reference: 0.99");
            System.out.println("Recall: " + recall + " Reference: 0.91");
            System.out.println("FMeasure: " + fmeasure + " Reference: 0.95");
            System.out.println("AUC: "+ auc + " Reference: 0.98");
            System.out.println("MCC: " + MCC + " Reference: 0.90");
            System.out.println();

            return accuracy;
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
        return -1;
    }
}
