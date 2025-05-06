package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MaltBatch {
    private String batchNummer;
    private LocalDate dato;
    private double mængde;

    private ArrayList<Malt> malt;

    public MaltBatch(String batchNummer, LocalDate dato, double mængdeIKg, ArrayList<Malt> malt) {
        this.batchNummer = batchNummer;
        this.dato = dato;
        this.mængde = mængdeIKg;
        this.malt = malt;
    }

    public void createMalt(String kornsort, String markNavn, double maengde) {
        malt.add(new Malt(kornsort, markNavn, maengde));
    }

    public String getBatchNummer() {
        return batchNummer;
    }

    public void setBatchNummer(String batchNummer) {
        this.batchNummer = batchNummer;
    }

    public LocalDate getDato() {
        return dato;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    public double getMængde() {
        return mængde;
    }

    public void setMængde(double mængde) {
        this.mængde = mængde;
    }

    public ArrayList<Malt> getMalt() {
        return malt;
    }

    public void setMalt(ArrayList<Malt> malt) {
        this.malt = malt;
    }
}
