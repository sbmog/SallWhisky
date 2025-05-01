package application.model;

import java.time.LocalDate;

public class FadPlacering {
    private LocalDate datoPlaceret;

    private Fad fad;
    private Hylde hylde;

    public FadPlacering(LocalDate datoPlaceret, Fad fad, Hylde hylde) {
        this.datoPlaceret = datoPlaceret;
        this.fad = fad;
        this.hylde = hylde;
        hylde.setPladsFri(false);
    }

    public LocalDate getDatoPlaceret() {
        return datoPlaceret;
    }

    public void setDatoPlaceret(LocalDate datoPlaceret) {
        this.datoPlaceret = datoPlaceret;
    }

    public Fad getFad() {
        return fad;
    }

    public void setFad(Fad fad) {
        this.fad = fad;
    }

    public Hylde getHylde() {
        return hylde;
    }

    public void setHylde(Hylde hylde) {
        this.hylde = hylde;
    }
}
