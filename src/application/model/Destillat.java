package application.model;

import java.time.LocalDate;

public class Destillat {
    private String destillatID;
    private LocalDate startDato;
    private LocalDate slutDato;
    private double literVand;
    private double alkoholProcent;
    private boolean røget;
    private double væskemængde;
    private MaltBatch maltBatch;

    public Destillat(String destillatID, LocalDate startDato, LocalDate slutDato, double literVand, double alkoholProcent, boolean røget, double væskemængde, MaltBatch maltBatch) {
        this.destillatID = destillatID;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.literVand = literVand;
        this.alkoholProcent = alkoholProcent;
        this.røget = røget;
        this.væskemængde = væskemængde;
        this.maltBatch = maltBatch;
    }

    public String getDestillatID() {
        return destillatID;
    }

    public void setDestillatID(String destillatID) {
        this.destillatID = destillatID;
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
