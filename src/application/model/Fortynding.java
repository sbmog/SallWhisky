package application.model;

public class Fortynding {
    private double vandmængde;

    public Fortynding(double vandmængde) {
        if (vandmængde <= 0) {
            throw new IllegalArgumentException("Vandmængde skal være et tal over 0.");
        }
        this.vandmængde = vandmængde;
    }

    public double getVandmængde() {
        return vandmængde;
    }

    public String toString() {
        return "Fortynding i liter: " + vandmængde;
    }
}