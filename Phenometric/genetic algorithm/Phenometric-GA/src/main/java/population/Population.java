package population;

import individual.Individual;

import java.util.HashSet;
import java.util.Objects;

public abstract class Population<T extends Individual> extends HashSet<T> implements Comparable<Population<T>> {
    private long id;
    private T bestIndividual;

    public Population(long id) {
        this.id = id;
        this.bestIndividual = null;
    }

    public double getAverageFitness() {
        return super.stream()
                .mapToDouble(Individual::getFitness)
                .average().orElse(0.0);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public T getBestIndividual() {
        return this.bestIndividual;
    }

    public void setBestIndividual(T bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    @Override
    public int compareTo(Population other) {
        return Double.compare(getAverageFitness(), other.getAverageFitness());
    }

    @Override
    public Population<T> clone() {
        return (Population<T>) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Population<?> that = (Population<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

}