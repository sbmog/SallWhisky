package application.model;

import java.time.LocalDate;

public class Destilat {
    private String destilatID;
    private LocalDate startDato;
    private LocalDate slutDato;
    private double literVand;
    private double alkoholProcent;
    private boolean røget;
    private double væskemængde;

    private MaltBatch maltBatch;

    public Destilat(String destilatID, LocalDate startDato, LocalDate slutDato, double literVand, double alkoholProcent, boolean røget, double væskemængde, MaltBatch maltBatch) {
        this.destilatID = destilatID;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.literVand = literVand;
        this.alkoholProcent = alkoholProcent;
        this.røget = røget;
        this.væskemængde = væskemængde;
        this.maltBatch = maltBatch;
    }

    public String getDestilatID() {
        return destilatID;
    }

    public void setDestilatID(String destilatID) {
        this.destilatID = destilatID;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public void setStartDato(LocalDate startDato) {
        this.startDato = startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public double getLiterVand() {
        return literVand;
    }

    public void setLiterVand(double literVand) {
        this.literVand = literVand;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public void setAlkoholProcent(double alkoholProcent) {
        this.alkoholProcent = alkoholProcent;
    }

    public boolean isRøget() {
        return røget;
    }

    public void setRøget(boolean røget) {
        this.røget = røget;
    }

    public double getVæskemængde() {
        return væskemængde;
    }

    public void setVæskemængde(double væskemængde) {
        this.væskemængde = væskemængde;
    }

    public MaltBatch getMaltBatch() {
        return maltBatch;
    }

    public void setMaltBatch(MaltBatch maltBatch) {
        this.maltBatch = maltBatch;
    }
}
