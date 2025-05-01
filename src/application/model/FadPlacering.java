package application.model;

import java.time.LocalDate;

public class FadPlacering {
    private LocalDate datoPlaceret;

    private Fad fad;
    private HyldePlads hyldePlads;

    public FadPlacering(LocalDate datoPlaceret, Fad fad, HyldePlads hyldePlads) {
        this.datoPlaceret = datoPlaceret;
        this.fad = fad;
        this.hyldePlads = hyldePlads;
        hyldePlads.setPladsFri(false);
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

    public HyldePlads getHyldePlads() {
        return hyldePlads;
    }

    public void setHyldePlads(HyldePlads hyldePlads) {
        HyldePlads oldHyldePlads = this.hyldePlads;
        oldHyldePlads.setPladsFri(true);
        
        this.hyldePlads = hyldePlads;
        hyldePlads.setPladsFri(false);
    }
}
