package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Fad {
    private int fadID;
    private double fadILiter;
    private String materiale;
    private String leverandør;
    private int antalGangeBrugt = 0;
    private static final int maksAntalGangeBrugt = 3;
    private double nuværendeIndhold = 0;
    private Tapning tapning;
    private static final double maxFadStørrelse = 500;
    private FadPlacering fadPlacering;
    private FadType fadType;
    private Påfyldning påfyldning;


    public Fad(int fadID, double fadILiter, String materiale, String leverandør, int antalGangeBrugt, FadType fadType, Påfyldning påfyldning) {
         if (fadILiter > maxFadStørrelse) {
             throw new IllegalArgumentException("Fad størrelse kan ikke være over " + maxFadStørrelse + " liter.");
            }  else if (leverandør == null || leverandør.isEmpty() || materiale == null || materiale.isEmpty()) {
                throw new IllegalArgumentException("Leverandør og/eller Materiale kan ikke være null eller tom.");
         } else if (antalGangeBrugt < 0) {
             throw new IllegalArgumentException("Antal gange brugt kan ikke være negativ.");
         } else if (fadID <= 0) {
             throw new IllegalArgumentException("Fad ID kan ikke være negativ eller 0.");
         }  else if (fadType == null || påfyldning == null) {
             throw new IllegalArgumentException("FadType og/eller Påfyldning kan ikke være null.");

         }
        this.fadID = fadID;
        this.fadILiter = fadILiter;
        this.materiale = materiale;
        this.leverandør = leverandør;
        this.antalGangeBrugt = 0;
        this.fadType = fadType;
        this.påfyldning = påfyldning;
    }

    public Tapning getTapning() {
        return tapning;
    }

    public void setTapning(Tapning tapning) {
        this.tapning = tapning;
    }

    public void placerPåHylde(HyldePlads hyldePlads, LocalDate dato) {
        if (hyldePlads.isPladsFri()) {
            this.fadPlacering = new FadPlacering(dato, this, hyldePlads);
        } else throw new IllegalStateException("Hyldepladsen er allerede optaget.");
    }
//TODO hvor får vi flaskestørrelse fra? ift. brugen i GUI
    public int beregnAntalFlasker(double flaskeStørrelse) {
        if ( flaskeStørrelse <= 0) {
            throw new IllegalArgumentException("Flaske størrelse skal være større end 0.");
        }
        return (int) (tapning.getAntalLiterFraFad() / flaskeStørrelse); //Konvertere fra liter til CL
    }

    public int BeregnLagringstid() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        LocalDate nu = LocalDate.now();
        if (startDato == null) {
            throw new IllegalStateException("Startdato kan ikke være null.");
        } else if (startDato.isBefore(nu)){
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        return (int) ChronoUnit.YEARS.between(startDato,nu);
    }

    public int beregnTidTilWhisky() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        LocalDate whiskyDato = startDato.plusYears(3);
        if (startDato == null) {
            throw new IllegalStateException("Startdato kan ikke være null.");
        } else if (startDato.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        if(LocalDate.now().isBefore(whiskyDato)) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), whiskyDato);
        } else {
            return 0;
        }
    }

    public void setPåfyldning(Påfyldning nyPåfyldning) {
        if (antalGangeBrugt >= maksAntalGangeBrugt) {
            throw new IllegalStateException("Fadet kan ikke bruges mere end " + maksAntalGangeBrugt + " gange.");
        }
        this.påfyldning = nyPåfyldning;
        antalGangeBrugt++;
    }

    public int getMaksAntalGangeBrugt() {
        return maksAntalGangeBrugt;
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

    public double getFadILiter() {
        return fadILiter;
    }

    public void setFadILiter(double fadILiter) {
        if (fadILiter < maxFadStørrelse) {
            throw new IllegalArgumentException("Fad størrelse kan ikke være over " + maxFadStørrelse + " liter.");
        }
        this.fadILiter = fadILiter;

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
}
