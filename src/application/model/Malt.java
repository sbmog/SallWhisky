package application.model;

public class Malt {
    private String kornSort;
    private String markNavn;
    private double mængde;

    public Malt(String kornSort, String markNavn, double mængdeIKg) {
        if (kornSort == null || kornSort.isEmpty()) {
            throw new IllegalArgumentException("Kornsort kan ikke være null eller tom.");
        } else if (markNavn == null || markNavn.isEmpty()) {
            throw new IllegalArgumentException("Marknavn kan ikke være null eller tom.");
        } else if (mængdeIKg <= 0) {
            throw new IllegalArgumentException("Mængde skal være et tal over 0.");
        }
        this.kornSort = kornSort;
        this.markNavn = markNavn;
        this.mængde = mængdeIKg;
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
