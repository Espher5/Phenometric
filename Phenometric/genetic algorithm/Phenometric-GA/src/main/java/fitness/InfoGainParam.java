package fitness;

public class InfoGainParam {
    private String name;
    private int position;
    private double infogain;
    private int occurrences;

    public InfoGainParam(String name, int position, double infogain, int occurrences) {
        this.name = name;
        this.position = position;
        this.infogain = infogain;
        this.occurrences = occurrences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getInfogain() {
        return infogain;
    }

    public void setInfogain(double infogain) {
        this.infogain = infogain;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
