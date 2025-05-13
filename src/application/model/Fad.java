package application.model;

import application.controller.Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Fad {
    private int fadID;
    private static int idCounter = 0;
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
    private double antalLiterPåFyldt;


    public Fad(double fadILiter, String materiale, String leverandør, FadType fadType) {
        if (fadILiter > maxFadStørrelse) {
            throw new IllegalArgumentException("Fad størrelse kan ikke være over " + maxFadStørrelse + " liter.");
        } else if (leverandør == null || leverandør.isEmpty() || materiale == null || materiale.isEmpty()) {
            throw new IllegalArgumentException("Leverandør og/eller Materiale kan ikke være null eller tom.");
        } else if (fadType == null) {
            throw new NullPointerException("FadType kan ikke være null.");
        }

        this.fadID = ++idCounter;
        this.fadILiter = fadILiter;
        this.materiale = materiale;
        this.leverandør = leverandør;
        this.antalGangeBrugt = 0;
        this.fadType = fadType;
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
        if (tapning == null) {
            throw new IllegalStateException("Der er endnu ikke foretaget en tapning på dette fad");
        }
        if ( flaskeStørrelse <= 0) {
            throw new IllegalArgumentException("Flaske størrelse skal være større end 0.");
        }
        return (int) (tapning.getAntalLiterFraFad() / flaskeStørrelse); //Konvertere fra liter til CL
    }

    public int BeregnLagringstid() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        //startDato behøver ikke nullpointer, da den allerede findes i påfyldning
        LocalDate nu = LocalDate.now();
        if (startDato.isAfter(nu)){
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        return (int) ChronoUnit.DAYS.between(startDato,nu);
    }

    public int beregnTidTilWhisky() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        //startDato behøver ikke nullpointer, da den allerede findes i påfyldning
        LocalDate whiskyDato = startDato.plusYears(3);
        if (startDato.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        if(LocalDate.now().isBefore(whiskyDato)) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), whiskyDato);
        } else {
            return 0;
        }
    }
    public void fjernFraHyldeHvisTom() {
        if (fadPlacering != null) {
            fadPlacering.getHyldePlads().setPladsFri(true);
            fadPlacering = null;
        }
    }

    public void opdaterNuværendeInhold(double antalLiterFraFad) {
        if (antalLiterFraFad > nuværendeIndhold) {
            throw new IllegalArgumentException("Der er ikke nok indhold i fadet til at foretage tapningen.");
        }
        this.nuværendeIndhold -= antalLiterFraFad;
    }



    public void setPåfyldning(Påfyldning nyPåfyldning) {
        if (antalGangeBrugt >= maksAntalGangeBrugt) {
            throw new IllegalStateException("Fadet kan ikke bruges mere end " + maksAntalGangeBrugt + " gange.");
        }
        if (nyPåfyldning == null) {
            throw new IllegalArgumentException("Påfyldning kan ikke være null.");
        }
        if (nuværendeIndhold + nyPåfyldning.getAntalLiterPåfyldt() > fadILiter) {
            throw new IllegalArgumentException("Påfyldning overstiger fadets kapacitet.");
        }
        
        this.påfyldning = nyPåfyldning;
        this.nuværendeIndhold += nyPåfyldning.getAntalLiterPåfyldt();
        this.antalLiterPåFyldt += nyPåfyldning.getAntalLiterPåfyldt();
        nyPåfyldning.setFad(this);
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
        if (fadILiter > maxFadStørrelse) {
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

    public String toString() {
        return "Fad: #" + fadID;
    }

    public static void resetIDCounter() {
        idCounter = 0;
    }
}
