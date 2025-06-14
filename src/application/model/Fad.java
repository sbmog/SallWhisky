package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Fad {
    private int fadID;
    private static int idCounter = 0;
    private double fadILiter;
    private String materiale;
    private String leverandør;
    private int antalGangeBrugt;
    private static final int maksAntalGangeBrugt = 3;
    private double nuværendeIndhold;

    private Tapning tapning;
    private static final double maxFadStørrelse = 500;
    private FadPlacering fadPlacering;
    private FadType fadType;
    private Påfyldning påfyldning;
    private ArrayList<Destillat> destillater = new ArrayList<>();

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
        this.nuværendeIndhold = 0;
    }

    public ArrayList<Destillat> getDestillater() {
        return destillater;
    }

    public void tilføjDestillat(Destillat destillat) {
        if (destillat == null) {
            throw new IllegalArgumentException("Destillatet kan ikke være null.");
        }
        destillater.add(destillat);
    }

    public Tapning getTapning() {
        return tapning;
    }

    public void setTapning(Tapning tapning) {
        this.nuværendeIndhold = 0;
        this.tapning = tapning;
    }

    public void placerPåHylde(HyldePlads hyldePlads, LocalDate dato) {
        if (hyldePlads.isPladsFri()) {
            this.fadPlacering = new FadPlacering(dato, this, hyldePlads);
        } else throw new IllegalStateException("Hyldepladsen er allerede optaget.");
    }

    public int beregnLagringstid() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        //startDato behøver ikke nullpointer, da den allerede findes i påfyldning
        LocalDate nu = LocalDate.now();
        if (startDato.isAfter(nu)) {
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        return (int) ChronoUnit.DAYS.between(startDato, nu);
    }

    public int beregnTidTilWhisky() {
        LocalDate startDato = påfyldning.getDatoForPåfyldning();
        //startDato behøver ikke nullpointer, da den allerede findes i påfyldning
        LocalDate whiskyDato = startDato.plusYears(3);
        if (startDato.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Startdato kan ikke være i fremtiden.");
        }
        if (LocalDate.now().isBefore(whiskyDato)) {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), whiskyDato);
        } else {
            return 0;
        }
    }

    public void fjernFraHyldeHvisTom() {
        if (fadPlacering != null) {
            fadPlacering.getHyldePlads().setPladsFri();
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

        this.påfyldning = nyPåfyldning;
        this.nuværendeIndhold = nyPåfyldning.getAntalLiterPåfyldt();
        if (!nyPåfyldning.getFad().equals(this)) {
            nyPåfyldning.setFad(this);
            this.tilføjDestillat(nyPåfyldning.getDestillat());
        }
        antalGangeBrugt++;
    }

    public int getMaksAntalGangeBrugt() {
        return maksAntalGangeBrugt;
    }

    public double getNuværendeIndhold() {
        return nuværendeIndhold;
    }

    public void setNuværendeIndhold(double nuværendeIndhold) {
        this.nuværendeIndhold = nuværendeIndhold;
    }

    public int getFadID() {
        return fadID;
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

    public String getLeverandør() {
        return leverandør;
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

    public Påfyldning getPåfyldning() {
        return påfyldning;
    }

    public String toString() {
        return "Fad: #" + fadID + ", " + fadType + ", " + materiale;
    }

    public static void resetIDCounter() {
        idCounter = 0;
    }
}