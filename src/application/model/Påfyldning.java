package application.model;

import java.time.LocalDate;

public class Påfyldning {
    private String initialerForMedarbejder;
    private double antalLiterPåfyldt;
    private LocalDate datoForPåfyldning;

    private Fad fad;
    private Destillat destillat;

    public Påfyldning(String initialerForMedarbejder, double antalLiterPåfyldt, LocalDate datoForPåfyldning, Fad fad, Destillat destillat, HyldePlads hyldePlads) {
        this.fad = fad;
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
        this.destillat = destillat;

        fad.tilføjDestillat(destillat);
        fad.setNuværendeIndhold(antalLiterPåfyldt);

        if (hyldePlads != null && fad.getFadPlacering() == null) {
            fad.placerPåHylde(hyldePlads, datoForPåfyldning);
        }
        if (fad.getTapning() != null) {
            fad.setTapning(null);
        }
    }

    public String getInitialerForMedarbejder() {
        return initialerForMedarbejder;
    }

    public double getAntalLiterPåfyldt() {
        return antalLiterPåfyldt;
    }

    public LocalDate getDatoForPåfyldning() {
        return datoForPåfyldning;
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