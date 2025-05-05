package application.model;

import java.util.ArrayList;

public class Whisky {
    private double whiskyID;
    private String navn;
    private double alkoholProcent;
    private boolean fortyndet;
    private double vandMængde;

    private ArrayList<Flaske> flasker;
    private ArrayList<Tapning> tapninger;
    private WhiskyType whiskyType;

    public Whisky(double whiskyID, String navn, double alkoholProcent, boolean fortyndet, double vandMængde, ArrayList<Tapning> tapninger, WhiskyType whiskyType) {
        if (alkoholProcent < 40.0){
            throw new IllegalArgumentException("Alkoholprocent skal være over 40 procent");
        }
        this.whiskyID = whiskyID;
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


    public String getHistorik() {
        String historik = "Historik for whisky: " + navn + "\n";

    for (Tapning tapning : tapninger) {
        Fad fad = tapning.getFad();
        Påfyldning påfyldning = fad.getPåfyldning();
        Destillat destillat = påfyldning.getDestillat();

        historik += "tapning: " + tapning.getTapningsDato() + "\n";
        historik += "Fad ID: " + fad.getFadID() + "\n";
        historik += "Påfyldning: " + påfyldning.getDatoForPåfyldning() + "\n";
        historik += "Destillat ID: " + destillat.getDestillatID() + "\n";
        historik += "Startdato: " + destillat.getStartDato() + "\n";
        historik += "Slutdato: " + destillat.getSlutDato() + "\n";
        historik += "Alkoholprocent: " + destillat.getAlkoholProcent() + "\n";
        historik += "Røget: " + destillat.isRøget() + "\n";
        historik += "Vandmængde: " + destillat.getLiterVand() + "\n";
        historik += "Væskemængde: " + destillat.getVæskemængde() + "\n";
        historik += "-----------------------------------\n";

    }
    return historik;
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

    public double getWhiskyID() {
        return whiskyID;
    }

    public void setWhiskyID(double whiskyID) {
        this.whiskyID = whiskyID;
    }
}
