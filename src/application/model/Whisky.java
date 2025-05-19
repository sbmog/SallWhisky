package application.model;

import application.controller.Controller;

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

    public Whisky(double whiskyID, String navn, double alkoholProcent, double vandMængde, ArrayList<Tapning> tapninger, WhiskyType whiskyType) {
        if (alkoholProcent < 40.0) {
            throw new IllegalArgumentException("Alkoholprocent skal være over 40 procent");
        } else if (navn == null || navn.isEmpty()) {
            throw new IllegalArgumentException("Navn kan ikke være null eller tom.");
        } else if (vandMængde < 0) {
            throw new IllegalArgumentException("Vandmængde skal være et tal over 0.");
        } else if (tapninger == null || tapninger.isEmpty()) {
            throw new IllegalArgumentException("Tapninger kan ikke være null eller tom.");
        } else if (whiskyType == null) {
            throw new IllegalArgumentException("WhiskyType kan ikke være null.");
        } else if (whiskyID <= 0) {
            throw new IllegalArgumentException("WhiskyID skal være et tal over 0.");
        }

        this.whiskyID = whiskyID;
        this.navn = navn;
        this.alkoholProcent = alkoholProcent;
        this.vandMængde = vandMængde;
        this.tapninger = tapninger;
        this.whiskyType = whiskyType;
        this.flasker = new ArrayList<>();
        updateFortyndetStatus();
    }

    public void updateFortyndetStatus() {
        for (Tapning tapning : tapninger) {
            if (!tapning.getFortyndinger().isEmpty()) {
                this.fortyndet = true;
                return;
            } else {
                this.fortyndet = false;
            }
        }
    }

    public void createFlaske() {
        flasker.add(new Flaske(flasker.size() + 1, this));
    }


    public String getHistorik() {
        String historik = "Historik for whisky: " + navn + "\n";

        // Overordnede oplysninger
        historik += "Whisky type: " + this.getWhiskyType() + "\n";
        historik += "Alkoholprocent: " + this.getAlkoholProcent() + "\n";
        historik += "Flaskestørrelse i liter: " + Controller.beregnFlaskeStørrelse(this) + "\n";
        historik += "Antal flasker: " + this.getFlasker().size() + "\n";

        // Saml unikke destillater
        ArrayList<Destillat> unikkeDestillater = new ArrayList<>();
        for (Tapning tapning : tapninger) {
            Fad fad = tapning.getFad();
            for (Destillat destillat : fad.getDestillater()) {
                boolean findesAllerede = false;
                for (Destillat d : unikkeDestillater) {
                    if (d.getDestillatID() == destillat.getDestillatID()) {
                        findesAllerede = true;
                        break;
                    }
                }
                if (!findesAllerede) {
                    unikkeDestillater.add(destillat);
                }
            }
        }

        // Vis alle destillat-ID'er
        historik += "Destillat ID'er: ";
        for (int i = 0; i < unikkeDestillater.size(); i++) {
            historik += unikkeDestillater.get(i).getDestillatID();
            if (i < unikkeDestillater.size() - 1) {
                historik += ", ";
            }
        }
        historik += "\n";

        // Vis detaljer for det første destillat
        if (!unikkeDestillater.isEmpty()) {
            Destillat første = unikkeDestillater.get(0);
            historik += "Destillering startdato: " + første.getStartDato() + "\n";
            historik += "Destillering slutdato: " + første.getSlutDato() + "\n";
            historik += "MaltBatch: " + første.getMaltBatch().getBatchNummer() + "\n";

            for (Malt malt : første.getMaltBatch().getMalt()) {
                historik += "- Malt: " + malt.toString() + "\n";
            }

            historik += "Røget: " + (første.isRøget() ? "Ja" : "Nej") + "\n";
            historik += "Vandmængde: " + første.getLiterVand() + "\n";
            historik += "Væskemængde: " + første.getVæskemængde() + "\n";
        }

        // Fade og tapninger
        historik += "Fade: \n";
        for (Tapning tapning : tapninger) {
            Fad fad = tapning.getFad();
            historik += "- Fad: " + fad.toString() + "\n";
            historik += "  Påfyldning: " + fad.getPåfyldning().getDatoForPåfyldning() + "\n";
            historik += "  Tapning: " + tapning.getTapningsDato() + "\n";
        }

        // Fortyndet
        historik += "Fortyndet: " + (this.isFortyndet() ? "Ja" : "Nej") + "\n";

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

    public void addTapning(Tapning tapning) {
        this.tapninger.add(tapning);
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

    public String toString() {
        return "Whisky: " + navn + " med ID: " + whiskyID;
    }
}