package individual.generator;

import individual.Individual;
import java.util.List;

public abstract class IndividualGenerator<T extends Individual> {
    public abstract T generateIndividuals();
}
