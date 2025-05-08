package application.model;

import java.time.LocalDate;

public class Påfyldning {
    private String initialerForMedarbejder;
    private double antalLiterPåfyldt;
    private LocalDate datoForPåfyldning;

    private Fad fad;
    private Destillat destillat;

    public Påfyldning(String initialerForMedarbejder, double antalLiterPåfyldt, LocalDate datoForPåfyldning, Fad fad, Destillat destillat) {
        if (fad.getNuværendeIndhold() + antalLiterPåfyldt > fad.getFadILiter()) {
            throw new IllegalArgumentException("Påfyldning overstiger fadets størrelse.");
        } else if (antalLiterPåfyldt <= 0) {
            throw new IllegalArgumentException("Antal liter påfyldt skal være større end 0.");
        } else if (initialerForMedarbejder == null || initialerForMedarbejder.isEmpty()) {
            throw new NullPointerException("Initialer for medarbejder kan ikke være null eller tom.");
        } else if (datoForPåfyldning == null) {
            throw new NullPointerException("Dato for påfyldning kan ikke være null.");
        } else if (destillat == null) {
            throw new NullPointerException("Destillat kan ikke være null.");
        }

        this.initialerForMedarbejder = initialerForMedarbejder;
        this.antalLiterPåfyldt = antalLiterPåfyldt;
        this.datoForPåfyldning = datoForPåfyldning;
        this.fad = fad;
        this.destillat = destillat;
    }

    public String getInitialerForMedarbejder() {
        return initialerForMedarbejder;
    }

    public void setInitialerForMedarbejder(String initialerForMedarbejder) {
        this.initialerForMedarbejder = initialerForMedarbejder;
    }

    public double getAntalLiterPåfyldt() {
        return antalLiterPåfyldt;
    }

    public void setAntalLiterPåfyldt(double antalLiterPåfyldt) {
        this.antalLiterPåfyldt = antalLiterPåfyldt;
    }

    public LocalDate getDatoForPåfyldning() {
        return datoForPåfyldning;
    }

    public void setDatoForPåfyldning(LocalDate datoForPåfyldning) {
        this.datoForPåfyldning = datoForPåfyldning;
    }

    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public Destillat getDestillat() {
        return destillat;
    }

    public void setDestillat(Destillat destillat) {
        this.destillat = destillat;
    }

    public String toString() {
        return destillat + " påfyldt " + antalLiterPåfyldt + " liter på " + fad;
    }
}
