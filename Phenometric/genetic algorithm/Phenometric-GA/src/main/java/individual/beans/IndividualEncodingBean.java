package individual.beans;

import java.util.List;

public class IndividualEncodingBean {
    private int loc;
    private List<KeywordBean> javaKeywords;
    private int keywordCount;
    private List<TokenBean> tokens;
    private int label;

    public IndividualEncodingBean(int loc, List<KeywordBean> javaKeywords, int keywordCount,
                                  List<TokenBean> tokens, int label) {
        this.loc = loc;
        this.javaKeywords = javaKeywords;
        this.keywordCount = keywordCount;
        this.tokens = tokens;
        this.label = label;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public List<KeywordBean> getJavaKeywords() {
        return javaKeywords;
    }

    public void setJavaKeywords(List<KeywordBean> javaKeywords) {
        this.javaKeywords = javaKeywords;
    }

    public int getKeywordCount() {
        return keywordCount;
    }

    public void setKeywordCount(int keywordCount) {
        this.keywordCount = keywordCount;
    }

    public List<TokenBean> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenBean> tokens) {
        this.tokens = tokens;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "IndividualEncodingBean{" +
                "loc=" + loc +
                ",javaKeywords=" + javaKeywords.toString() +
                ",keywordCount=" + keywordCount +
                ",tokens=" + tokens.toString() +
                ",label=" + label +
                "}";
    }
}
