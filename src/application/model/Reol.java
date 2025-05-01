package application.model;

import java.util.ArrayList;

public class Reol {
    private int reolID;

    private ArrayList<HyldePlads> hyldePladser;
    private Lager lager;

    public Reol(Lager lager, int reolID) {
        this.lager = lager;
        this.reolID = reolID;
    }

    public void createHyldePlads() {
        hyldePladser.add(new HyldePlads(hyldePladser.size() + 1, this));
    }

//    bør det være delete? i så fald, hvordan deleter vi. Må vi kalde storageklasse her? eller er den bare slettet ved at fjerne
    public void removeHyldePlads(HyldePlads hyldePlads){
        if (hyldePlads.isPladsFri()){
            hyldePladser.remove(hyldePlads);
        }
    }

    public int getReolID() {
        return reolID;
    }

    public void setReolID(int reolID) {
        this.reolID = reolID;
    }

    public ArrayList<HyldePlads> getHyldePladser() {
        return hyldePladser;
    }

    public void setHyldePladser(ArrayList<HyldePlads> hyldePladser) {
        this.hyldePladser = hyldePladser;
    }

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }
}
