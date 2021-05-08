package individual;

public abstract class Individual implements Comparable<Individual>, Cloneable {
    protected double fitness;

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual other) {
        return Double.compare(this.fitness, other.fitness);
    }

    @Override
    public Individual clone() throws CloneNotSupportedException {
        return (Individual) super.clone();
    }

    @Override
    public String toString() {
        return "individual.example.Individual{" +
                "fitness=" + fitness +
                "}";
    }

}
