package application.model;

import java.util.ArrayList;

public class Reol {
    private int reolID;

    private ArrayList<Hylde> hylder;
    private Lager lager;

    public Reol(Lager lager, int reolID) {
        this.lager = lager;
        this.reolID = reolID;
    }

    public void createHylde() {
        hylder.add(new Hylde(hylder.size() + 1, this));
    }

//    bør det være delete? i så fald, hvordan deleter vi. Må vi kalde storageklasse her? eller er den bare slettet ved at fjerne
    public void removeHylde(Hylde hylde){
        if (hylde.isPladsFri()){
            hylder.remove(hylde);
        }
    }

    public int getReolID() {
        return reolID;
    }

    public void setReolID(int reolID) {
        this.reolID = reolID;
    }

    public ArrayList<Hylde> getHylder() {
        return hylder;
    }

    public void setHylder(ArrayList<Hylde> hylder) {
        this.hylder = hylder;
    }

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }
}
