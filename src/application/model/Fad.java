package application.model;

import java.time.LocalDate;

public class Fad {
    private int fadID;
    private double fadStørrelse;
    private String materiale;
    private String leverandør;
    private  int antalGangeBrugt;

    private FadPlacering fadPlacering;
    private FadType fadType;
    private Påfyldning påfyldning;

    public Fad(int fadID, double fadStørrelse, String materiale, String leverandør, int antalGangeBrugt, FadType fadType, Påfyldning påfyldning) {
        this.fadID = fadID;
        this.fadStørrelse = fadStørrelse;
        this.materiale = materiale;
        this.leverandør = leverandør;
        this.antalGangeBrugt = antalGangeBrugt;
        this.fadType = fadType;
        this.påfyldning = påfyldning;
    }

    public void placerPå (HyldePlads hyldePlads, LocalDate dato) {
        this.fadPlacering = new FadPlacering(dato, this, hyldePlads);
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
