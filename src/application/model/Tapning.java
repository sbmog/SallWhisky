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
        this.tapningsDato = tapningsDato;
        this.initialerForMedarbejder = initialerForMedarbejder;
        this.antalLiterFraFad = antalLiterFraFad;
        this.fad = fad;
        fortyndinger = new ArrayList<>();

        LocalDate påfyldningsDato = fad.getPåfyldning().getDatoForPåfyldning();

        if (tapningsDato.isBefore(påfyldningsDato.plusYears(3))) {
            throw new IllegalArgumentException("Destilliatet kan ikke tappes før den har lagret i 3 år.");
        }
    }

    private void createFortynding(double vandMængde){
       fortyndinger.add(new Fortynding(vandMængde));
    }

    public LocalDate getTapningsDato() {
        return tapningsDato;
    }

    public void setTapningsDato(LocalDate tapningsDato) {
        this.tapningsDato = tapningsDato;
    }

    public String getInitialerForMedarbejder() {
        return initialerForMedarbejder;
    }

    public void setInitialerForMedarbejder(String initialerForMedarbejder) {
        this.initialerForMedarbejder = initialerForMedarbejder;
    }

    public double getAntalLiterFraFad() {
        return antalLiterFraFad;
    }

    public void setAntalLiterFraFad(double antalLiterFraFad) {
        this.antalLiterFraFad = antalLiterFraFad;
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

    public void setFortyndinger(ArrayList<Fortynding> fortyndinger) {
        this.fortyndinger = fortyndinger;
    }
}
