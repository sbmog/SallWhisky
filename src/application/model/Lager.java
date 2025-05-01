package application.model;

import java.util.ArrayList;

public class Lager {
    private String lagerID;
    private String navn;
    private String adresse;

    private ArrayList<Reol> reoler;

    public Lager(String lagerID, String navn, String adresse) {
        this.lagerID = lagerID;
        this.navn = navn;
        this.adresse = adresse;
    }

    public void createReol() {
        reoler.add(new Reol(this, reoler.size() + 1));
    }

    public void removeReol(Reol reol) {
        if (reol.getHylder().isEmpty()) {
            reoler.remove(reol);
        }
    }

    public String getLagerID() {
        return lagerID;
    }

    public void setLagerID(String lagerID) {
        this.lagerID = lagerID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ArrayList<Reol> getReoler() {
        return reoler;
    }

    public void setReoler(ArrayList<Reol> reoler) {
        this.reoler = reoler;
    }
}
