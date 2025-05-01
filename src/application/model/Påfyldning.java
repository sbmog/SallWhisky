package application.model;

import java.time.LocalDate;

public class Påfyldning {
    private String initialerForMedarbejder;
    private double antalLiterPåfyldt;
    private LocalDate datoForPåfyldning;

    private Fad fad;
    private Destilat destilat;

    public Påfyldning(String initialerForMedarbejder, double antalLiterPåfyldt, LocalDate datoForPåfyldning, Fad fad, Destilat destilat) {
        this.initialerForMedarbejder = initialerForMedarbejder;
        this.antalLiterPåfyldt = antalLiterPåfyldt;
        this.datoForPåfyldning = datoForPåfyldning;
        this.fad = fad;
        this.destilat = destilat;
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

    public Destilat getDestilat() {
        return destilat;
    }

    public void setDestilat(Destilat destilat) {
        this.destilat = destilat;
    }
}
