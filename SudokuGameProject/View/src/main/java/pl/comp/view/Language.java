package pl.comp.view;


public enum Language {
    POLISH(""),
    ENGLISH("");

    private String label;

    Language(String label) {
        this.label = label;
    }

    public Language setLabel(String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return label;
    }
}
