package application.model;

public class Fad {
    private int fadID;
    private double fadStørrelse;
    private String matriale;
    private String leverandør;
    private  int antalGangeBrugt;

    private FadPlacering fadPlacering;
    private FadType fadType;
    private Påfyldning påfyldning;

    public Fad(int fadID, double fadStørrelse, String matriale, String leverandør, int antalGangeBrugt, FadPlacering fadPlacering, FadType fadType, Påfyldning påfyldning) {
        this.fadID = fadID;
        this.fadStørrelse = fadStørrelse;
        this.matriale = matriale;
        this.leverandør = leverandør;
        this.antalGangeBrugt = antalGangeBrugt;
        this.fadPlacering = fadPlacering;
        this.fadType = fadType;
        this.påfyldning = påfyldning;
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

    public String getMatriale() {
        return matriale;
    }

    public void setMatriale(String matriale) {
        this.matriale = matriale;
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
