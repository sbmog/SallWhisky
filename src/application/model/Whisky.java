package application.model;

import java.util.ArrayList;

public class Whisky {
    private double whskyID;
    private String navn;
    private double alkoholProcent;
    private boolean fortyndet;
    private double vandMængde;

    private ArrayList<Flaske> flasker;
    private ArrayList<Tapning> tapninger;
    private WhiskyType whiskyType;

    public Whisky(double whskyID, String navn, double alkoholProcent, boolean fortyndet, double vandMængde, ArrayList<Tapning> tapninger, WhiskyType whiskyType) {
        this.whskyID = whskyID;
        this.navn = navn;
        this.alkoholProcent = alkoholProcent;
        this.fortyndet = fortyndet;
        this.vandMængde = vandMængde;
        this.tapninger = tapninger;
        this.whiskyType = whiskyType;
    }

    public void createFlaske() {
        flasker.add(new Flaske(flasker.size() + 1, this));
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public boolean isFortyndet() {
        return fortyndet;
    }

    public void setFortyndet(boolean fortyndet) {
        this.fortyndet = fortyndet;
    }

    public double getVandMængde() {
        return vandMængde;
    }

    public void setVandMængde(double vandMængde) {
        this.vandMængde = vandMængde;
    }

    public ArrayList<Flaske> getFlasker() {
        return flasker;
    }

    public void setFlasker(ArrayList<Flaske> flasker) {
        this.flasker = flasker;
    }

    public ArrayList<Tapning> getTapninger() {
        return tapninger;
    }

    public void setTapninger(ArrayList<Tapning> tapninger) {
        this.tapninger = tapninger;
    }

    public WhiskyType getWhiskyType() {
        return whiskyType;
    }

    public void setWhiskyType(WhiskyType whiskyType) {
        this.whiskyType = whiskyType;
    }

    public double getWhskyID() {
        return whskyID;
    }

    public void setWhskyID(double whskyID) {
        this.whskyID = whskyID;
    }
}
