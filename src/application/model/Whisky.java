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

        // Tilføj overordnede oplysninger én gang
        historik += "Whisky type: " + this.getWhiskyType() + "\n";
        historik += "Alkoholprocent: " + this.getAlkoholProcent() + "\n";
        historik += "Antal flasker: " + this.getFlasker().size() + "\n";

        // Tilføj destillatoplysninger én gang
        Destillat destillat = tapninger.get(0).getFad().getPåfyldning().getDestillat();
        historik += "Destillat ID: " + destillat.getDestillatID() + "\n";
        historik += "Destillering startdato: " + destillat.getStartDato() + "\n";
        historik += "Destillering slutdato: " + destillat.getSlutDato() + "\n";
        historik += "MaltBatch: " + destillat.getMaltBatch().getBatchNummer() + "\n";

        // Tilføj maltdata én gang
        for (Malt malt : destillat.getMaltBatch().getMalt()) {
            historik += "- Malt: " + malt.toString() + "\n";
        }

        // Iterer over tapninger for at tilføje fade
        historik += "Fade: \n";
        for (Tapning tapning : tapninger) {
            Fad fad = tapning.getFad();
            historik += "- Fad: " + fad.toString() + "\n";
            historik += "  Påfyldning: " + fad.getPåfyldning().getDatoForPåfyldning() + "\n";
            historik += "  Tapning: " + tapning.getTapningsDato() + "\n";
        }

        // Tilføj afsluttende oplysninger én gang
        historik += "Røget: " + (destillat.isRøget() ? "Ja" : "Nej") + "\n";
        historik += "Fortyndet: " + this.isFortyndet() + "\n";
        historik += "Vandmængde: " + destillat.getLiterVand() + "\n";
        historik += "Væskemængde: " + destillat.getVæskemængde() + "\n";

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