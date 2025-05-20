package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MaltBatch {
    private String batchNummer;
    private LocalDate dato;
    private double mængde;

    private ArrayList<Malt> malt;

    public MaltBatch(String batchNummer, LocalDate dato, double mængdeIKg, ArrayList<Malt> malt) {
        if (batchNummer == null || batchNummer.isEmpty()) {
            throw new IllegalArgumentException("Batchnummer kan ikke være null eller tom.");
        } else if (dato == null) {
            throw new IllegalArgumentException("Dato kan ikke være null.");
        } else if (mængdeIKg <= 0) {
            throw new IllegalArgumentException("Mængde skal være et tal over 0.");
        }

        if (malt != null && !malt.isEmpty()) {
            double totalMaltMængde = 0;
            for (int i = 0; i < malt.size(); i++) {
                totalMaltMængde += malt.get(i).getMængde();
            }
            if (mængdeIKg < totalMaltMængde) {
                throw new IllegalArgumentException("Maltbatches mængde må ikke være mindre en summen af malt.");
            }
        }
        this.batchNummer = batchNummer;
        this.dato = dato;
        this.mængde = mængdeIKg;
        this.malt = malt;
    }

    public Malt createMalt(String kornsort, String markNavn, double mængde) {
        Malt maltObjekt = new Malt(kornsort, markNavn, mængde);
        malt.add(maltObjekt);
        this.mængde += mængde;
        return maltObjekt;
    }

    public String getBatchNummer() {
        return batchNummer;
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

    public ArrayList<Malt> getMalt() {
        return malt;
    }

    public void setMalt(ArrayList<Malt> malt) {
        this.malt = malt;
    }

    public String toString() {
        return "Maltbatch: #" + batchNummer;
    }
}