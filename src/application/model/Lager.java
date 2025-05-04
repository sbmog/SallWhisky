package application.model;

import java.util.ArrayList;

public class Lager {
    private String lagerID;
    private String navn;
    private String adresse;
    private int maxAntalReoler;

    private ArrayList<Reol> reoler;

    public Lager(String lagerID, String navn, String adresse, int maxAnantalReoler) {

        this.lagerID = lagerID;
        this.navn = navn;
        this.adresse = adresse;
        this.reoler = new ArrayList<>();
        this.maxAntalReoler = maxAnantalReoler;
    }

    public void createReol() {
        if (reoler.size() >= maxAntalReoler) {
            System.out.println("Lageret har nået det maksimale antal reoler.");
        }
        reoler.add(new Reol(this, reoler.size() + 1));
    }

    public void removeReol(Reol reol) {
        if (reol.getHyldePladser().isEmpty()) {
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
