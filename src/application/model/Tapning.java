package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Tapning {
    private LocalDate tapningsDato;
    private String initialerForMedarbejder;
    private double antalLiterFraFad;

    private Fad fad;
    private ArrayList<Fortynding> fortyndinger;

    public Tapning(LocalDate tapningsDato, String initialerForMedarbejder, double antalLiterFraFad, Fad fad) {
        if (tapningsDato == null) {
            throw new IllegalArgumentException("Tapningsdato kan ikke være null.");
        } else if (initialerForMedarbejder == null || initialerForMedarbejder.isEmpty()) {
            throw new IllegalArgumentException("Initialer for medarbejder kan ikke være null eller tom.");
        } else if (antalLiterFraFad <= 0) {
            throw new IllegalArgumentException("Antal liter fra fad skal være større end 0.");
        }
        this.tapningsDato = tapningsDato;
        this.initialerForMedarbejder = initialerForMedarbejder;
        this.antalLiterFraFad = antalLiterFraFad;
        this.fad = fad;
        fortyndinger = new ArrayList<>();

        fad.fjernFraHyldeHvisTom();
        fad.setTapning(this);
        LocalDate påfyldningsDato = fad.getPåfyldning().getDatoForPåfyldning();

        if (tapningsDato.isBefore(påfyldningsDato)) {
            throw new IllegalArgumentException("Tapningsdato kan ikke være før påfyldningsdato.");
        } else if (tapningsDato.isBefore(påfyldningsDato.plusYears(3))) {
            throw new IllegalArgumentException("Destillatet kan ikke tappes før den har lagret i 3 år.");
        }
    }

    public void createFortynding(double vandMængde) {
        fortyndinger.add(new Fortynding(vandMængde));
    }

    public LocalDate getTapningsDato() {
        return tapningsDato;
    }

    public String getInitialerForMedarbejder() {
        return initialerForMedarbejder;
    }

    public double getAntalLiterFraFad() {
        return antalLiterFraFad;
    }

    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public ArrayList<Fortynding> getFortyndinger() {
        return fortyndinger;
    }

    public String toString() {
        return "Tapning fra " + fad;
    }

    public int beregnAntalFlasker(double flaskeStørrelseCl) {
            if (flaskeStørrelseCl <= 0) {
                throw new IllegalArgumentException("Flaskestørrelse skal være større end 0.");
            }
            double totalVæske = antalLiterFraFad + getTotalFortydnigMængde();

            return (int) ((totalVæske * 100) / flaskeStørrelseCl);
    }

    public double getTotalFortydnigMængde() {
        double totalFortynding = 0;
        for (Fortynding fortynding : fortyndinger) {
            totalFortynding += fortynding.getVandmængde();
        }
        return totalFortynding;
    }
}