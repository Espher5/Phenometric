package individual;

public abstract class EncodedIndividual<T> extends Individual {
    protected T encoding;

    public EncodedIndividual(T encoding) {
        this.encoding = encoding;
    }

    public T getEncoding() {
        return this.encoding;
    }

    public void setEncoding(T encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "EcodedIndividual{+" +
                "coding=" + encoding +
                ",fitness=" + fitness +
                "}";
    }
}
