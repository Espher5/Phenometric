package individual.beans;

public class TokenBean {
    private String name;
    private int presence;

    public TokenBean(String name, int present) {
        this.name = name;
        this.presence = present;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    @Override
    public String toString() {
        return "\nTokenBean{" +
                "name=" + name +
                ", presence=" + presence +
                "}";
    }
}
