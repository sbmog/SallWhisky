package application.model;

import java.time.LocalDate;

public class FadPlacering {
    private LocalDate datoPlaceret;

    private Fad fad;
    private HyldePlads hyldePlads;

    protected FadPlacering(LocalDate datoPlaceret, Fad fad, HyldePlads hyldePlads) {
        if (datoPlaceret == null) {
            throw new IllegalArgumentException("Dato kan ikke være null.");
        } else if (fad == null) {
            throw new IllegalArgumentException("Fad kan ikke være null.");
        } else if (hyldePlads == null) {
            throw new IllegalArgumentException("HyldePlads kan ikke være null.");
        } else if (!hyldePlads.isPladsFri()) {
            throw new IllegalStateException("Hyldepladsen er allerede optaget.");
        }
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
        if (this.hyldePlads != null) {
            HyldePlads oldHyldePlads = this.hyldePlads;
            oldHyldePlads.setPladsFri(true);
        }
        if (!this.hyldePlads.equals(hyldePlads)) {
            this.hyldePlads = hyldePlads;
            this.hyldePlads.setFadPlaceret(this);
            hyldePlads.setPladsFri(false);
        }
    }

    public String getFullFadPlacering() {
        Reol reol = hyldePlads.getReol();
        Lager lager = reol.getLager();
        return lager.getNavn() + " - " + reol.getReolID() + " - " + hyldePlads.getHyldePladsID();
    }
}
