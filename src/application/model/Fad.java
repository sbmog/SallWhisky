package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Fad {
    private int fadID;
    private double fadStørrelse;
    private String materiale;
    private String leverandør;
    private int antalGangeBrugt;
    private double nuværendeIndhold = 0;

    private FadPlacering fadPlacering;
    private FadType fadType;
    private Påfyldning påfyldning;
    private static final double MAX_FAD_STØRRELSE = 500;

    public Fad(int fadID, double fadStørrelse, String materiale, String leverandør, int antalGangeBrugt, FadType fadType, Påfyldning påfyldning) {
         if (fadStørrelse > MAX_FAD_STØRRELSE) {
             throw new IllegalArgumentException("Fad størrelse kan ikke være over " + MAX_FAD_STØRRELSE + " liter.");
         }
        this.fadID = fadID;
        this.fadStørrelse = fadStørrelse;
        this.materiale = materiale;
        this.leverandør = leverandør;
        this.antalGangeBrugt = antalGangeBrugt;
        this.fadType = fadType;
        this.påfyldning = påfyldning;
    }

    public void placerPåHylde(HyldePlads hyldePlads, LocalDate dato) {
        if (hyldePlads.isPladsFri()) {
            this.fadPlacering = new FadPlacering(dato, this, hyldePlads);
        } else throw new IllegalStateException("Hyldepladsen er allerede optaget.");
    }

    public int beregnAntalFlasker(double flaskeStørrelse) {
        if ( flaskeStørrelse <= 0) {
            throw new IllegalArgumentException("Flaske størrelse skal være større end 0.");
        }
        return (int) (nuværendeIndhold * 100 / flaskeStørrelse); //Konvertere fra liter til CL
    }

    public int BeregnLagringstid() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        LocalDate nu = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(startDato,nu);
    }

    public int beregnTidTilWhisky() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        LocalDate whiskyDato = startDato.plusYears(3);

        if(LocalDate.now().isBefore(whiskyDato)) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), whiskyDato);
        } else {
            return 0;
        }
    }

    public double getNuværendeIndhold() {
        return nuværendeIndhold;
    }


    public int getFadID() {
        return fadID;
    }

    public void setFadID(int fadID) {
        this.fadID = fadID;
    }

    public double getFadStørrelse() {
        return fadStørrelse;
    }

    public void setFadStørrelse(double fadStørrelse) {
        if (fadStørrelse < MAX_FAD_STØRRELSE) {
            throw new IllegalArgumentException("Fad størrelse kan ikke være over " + MAX_FAD_STØRRELSE + " liter.");
        }
        this.fadStørrelse = fadStørrelse;

    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String getLeverandør() {
        return leverandør;
    }

    public void setLeverandør(String leverandør) {
        this.leverandør = leverandør;
    }

    public int getAntalGangeBrugt() {
        return antalGangeBrugt;
    }

    public void setAntalGangeBrugt(int antalGangeBrugt) {
        this.antalGangeBrugt = antalGangeBrugt;
    }

    public FadPlacering getFadPlacering() {
        return fadPlacering;
    }

    public void setFadPlacering(FadPlacering fadPlacering) {
        this.fadPlacering = fadPlacering;
    }

    public FadType getFadType() {
        return fadType;
    }

    public void setFadType(FadType fadType) {
        this.fadType = fadType;
    }

    public Påfyldning getPåfyldning() {
        return påfyldning;
    }

    public void setPåfyldning(Påfyldning påfyldning) {
        this.påfyldning = påfyldning;
    }
}
