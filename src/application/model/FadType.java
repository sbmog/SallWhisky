package application.model;

import java.util.ArrayList;

public class FadType {
    private String navn;

    private ArrayList<Fad> fade;

    public FadType(String navn) {
        this.navn = navn;
    }

    private void createFad(int fadID, double fadStørrelse, String materiale, String leverandør, int antalGangeBrugt, FadPlacering fadPlacering, Påfyldning påfyldning) {
        fade.add(new Fad(fadID, fadStørrelse, materiale, leverandør, antalGangeBrugt, this, påfyldning));
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ArrayList<Fad> getFade() {
        return fade;
    }

    public void setFade(ArrayList<Fad> fade) {
        this.fade = fade;
    }
}
