package operator.selection;

import individual.Individual;
import operator.GeneticOperator;

import java.util.Random;

public abstract class SelectionOperator<T extends Individual> extends GeneticOperator<T> {
    public SelectionOperator(Random random) {
        super(random);
    }
}
