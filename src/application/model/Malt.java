package application.model;

public class Malt {
    private String kornSort;
    private String markNavn;
    private double mængde;

    public Malt(String kornSort, String markNavn, double mængde) {
        this.kornSort = kornSort;
        this.markNavn = markNavn;
        this.mængde = mængde;
    }

    public String getKornSort() {
        return kornSort;
    }

    public void setKornSort(String kornSort) {
        this.kornSort = kornSort;
    }

    public String getMarkNavn() {
        return markNavn;
    }

    public void setMarkNavn(String markNavn) {
        this.markNavn = markNavn;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }
}
