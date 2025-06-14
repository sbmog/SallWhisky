package application.model;

import java.util.ArrayList;

public class Lager {
    private String lagerID;
    private String navn;
    private String adresse;
    private int maxAntalReoler;

    private ArrayList<Reol> reoler;

    public Lager(String lagerID, String navn, String adresse, int maxAntalReoler) {
        if (lagerID == null || lagerID.isEmpty()) {
            throw new IllegalArgumentException("LagerID kan ikke være null eller tom.");
        } else if (navn == null || navn.isEmpty()) {
            throw new IllegalArgumentException("Navn kan ikke være null eller tom.");
        } else if (adresse == null || adresse.isEmpty()) {
            throw new IllegalArgumentException("Adresse kan ikke være null eller tom.");
        } else if (maxAntalReoler <= 0) {
            throw new IllegalArgumentException("Maksimalt antal reoler skal være et tal over 0.");
        }
        this.lagerID = lagerID;
        this.navn = navn;
        this.adresse = adresse;
        this.reoler = new ArrayList<>();
        this.maxAntalReoler = maxAntalReoler;
    }

    public Reol createReol() {
        if (reoler.size() >= maxAntalReoler) {
            throw new IllegalArgumentException("Lageret har nået det maksimale antal reoler.");
        }
        Reol reol = new Reol(this, reoler.size() + 1);
        reoler.add(reol);
        return reol;
    }

    public void removeReol(Reol reol) {
        if (reol.getHyldePladser().isEmpty()) {
            reoler.remove(reol);
        }
    }

    public String getLagerID() {
        return lagerID;
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

    public ArrayList<Reol> getReoler() {
        return reoler;
    }

    public int getTotalAntalFadePåLager() {
        int total = 0;
        for (Reol reol : reoler) {
            for (HyldePlads hyldePlads : reol.getHyldePladser()) {
                if (!hyldePlads.isPladsFri()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getMaxAntalReoler() {
        return maxAntalReoler;
    }

    public String toString() {
        return "Lager: " + navn;
    }
}