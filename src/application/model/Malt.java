package application.model;

public class Malt {
    private String kornSort;
    private String markNavn;
    private double mængde;

        Malt(String kornSort, String markNavn, double mængdeIKg) {
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

    public String getMarkNavn() {
        return markNavn;
    }

    public double getMængde() {
        return mængde;
    }

    public String toString() {
        return "Malt af kornsorten: " + kornSort + " på mark: " + markNavn;
    }
}