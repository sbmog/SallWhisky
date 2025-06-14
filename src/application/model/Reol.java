package application.model;

import java.util.ArrayList;

public class Reol {
    private int reolID;

    private ArrayList<HyldePlads> hyldePladser;
    private Lager lager;

    protected Reol(Lager lager, int reolID) {
        if (reolID <= 0) {
            throw new IllegalArgumentException("ReolID skal være et tal over 0.");
        } else if (lager == null) {
            throw new IllegalArgumentException("Lager kan ikke være null.");
        }
        this.lager = lager;
        this.reolID = reolID;
        this.hyldePladser = new ArrayList<>();
    }

    public HyldePlads createHyldePlads() {
        HyldePlads hyldePlads = new HyldePlads(hyldePladser.size() + 1, this);
        hyldePladser.add(hyldePlads);
        return hyldePlads;
    }

    public void removeHyldePlads(HyldePlads hyldePlads) {
        if (hyldePlads.isPladsFri()) {
            hyldePladser.remove(hyldePlads);
        }
    }

    public int getReolID() {
        return reolID;
    }

    public ArrayList<HyldePlads> getHyldePladser() {
        return hyldePladser;
    }

    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager) {
        this.lager = lager;
    }

    public String toString() {
        return "Reol #" + reolID;
    }
}