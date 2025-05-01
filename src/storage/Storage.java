package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {
    private static ArrayList<Destillat> destillater;
    private static ArrayList<Fad> fade;
    private static ArrayList<Påfyldning> påfyldninger;
    private static ArrayList<FadType> fadTyper;
    private static ArrayList<MaltBatch> maltBatches;
    private static ArrayList<Malt> malter;
    private static ArrayList<Tapning> tapninger;
    private static ArrayList<Whisky> whiskyer;

    private static ArrayList<Lager> lagre;
    private static ArrayList<FadPlacering> fadPlaceringer;
    private static ArrayList<Hylde> hylder;
    private static ArrayList<Reol> reoler;


    //Destillat
    public static void addDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }

    public static void removeDestillat(Destillat destillat){
        if (destillater.contains(destillat)){
            destillater.remove(destillat);
        }

    }

    public ArrayList<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    //Fad
    public static void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }

    public static void removeFad(Fad fad){
        if (fade.contains(fad)){
            fade.remove(fad);
        }
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    //Påfyldning
    public static void addPåfyldning(Påfyldning påfyldning) {
        if (!påfyldninger.contains(påfyldning)) {
            påfyldninger.add(påfyldning);
        }
    }

    public static void removePåfyldning(Påfyldning påfyldning){
        if (påfyldninger.contains(påfyldning)){
            påfyldninger.remove(påfyldning);
        }
    }

    public ArrayList<Påfyldning> getPåfyldninger() {
        return new ArrayList<>(påfyldninger);
    }

    //FadType
    public static void addFadType(FadType fadType) {
        if (!fadTyper.contains(fadType)) {
            fadTyper.add(fadType);
        }
    }

    public static void removeFadType(FadType fadType){
        if (fadTyper.contains(fadType)){
            fadTyper.remove(fadType);
        }
    }

    public ArrayList<FadType> getFadTyper() {
        return new ArrayList<>(fadTyper);
    }

    //MaltBatch
    public static void addMaltBatch(MaltBatch maltBatch){
        if (!maltBatches.contains(maltBatch)){
            maltBatches.add(maltBatch);
        }
    }

    public static void removeMaltBatch(MaltBatch maltBatch){
        if (maltBatches.contains(maltBatch)){
            maltBatches.remove(maltBatch);
        }
    }

    public ArrayList<MaltBatch> getMaltBatches() {
        return new ArrayList<>(maltBatches);
    }

    //Malt - OBS
    public static void addMalt(Malt malt){
        if (!malter.contains(malt)){
            malter.add(malt);
        }
    }

    public static void removeMalt(Malt malt){
        if (malter.contains(malt)){
            malter.remove(malt);
        }
    }

    public ArrayList<Malt> getMalter() {
        return new ArrayList<>(malter);
    }

    //Tapning
    public static void addTapning(Tapning tapning){
        if (!tapninger.contains(tapning)){
            tapninger.add(tapning);
        }
    }

    public static void removeTapning(Tapning tapning){
        if (tapninger.contains(tapning)){
            tapninger.remove(tapning);
        }
    }

    public ArrayList<Tapning> getTapninger() {
        return new ArrayList<>(tapninger);
    }

    //Whiksy
    public static void addWhisky(Whisky whisky){
        if (!whiskyer.contains(whisky)){
            whiskyer.add(whisky);
        }
    }

    public static void removeWhisky(Whisky whisky){
        if (whiskyer.contains(whisky)){
            whiskyer.remove(whisky);
        }
    }

    public ArrayList<Whisky> getWhiskyer() {
        return new ArrayList<>(whiskyer);
    }

    //Lager
    public static void addLager(Lager lager){
        if (!lagre.contains(lager)){
            lagre.add(lager);
        }
    }

    public static void removeLager(Lager lager){
        if (lagre.contains(lager)){
            lagre.remove(lager);
        }
    }
    public ArrayList<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    //FadPlacering
    public static void addFadPlacering(FadPlacering fadPlacering){
        if (!fadPlaceringer.contains(fadPlacering)){
            fadPlaceringer.add(fadPlacering);
        }
    }

    public static void removeFadPlacering(FadPlacering fadPlacering){
        if (fadPlaceringer.contains(fadPlacering)){
            fadPlaceringer.remove(fadPlacering);
        }
    }

    public ArrayList<FadPlacering> getFadPlaceringer() {
        return new ArrayList<>(fadPlaceringer);
    }

    //Hylde
    public static void addHylde(HyldePlads hylde){

        if (!hylder.contains(hylde)){
            hylder.add(hylde);
        }
    }

    public static void removeHylde(HyldePlads hylde){


        if (hylder.contains(hylde)){
            hylder.remove(hylde);
        }
    }

    public ArrayList<HyldePlads> getHylder() {
 
        return new ArrayList<>(hylder);
    }

    //Reol
    public static void addReol(Reol reol){
        if (!reoler.contains(reol)){
            reoler.add(reol);
        }
    }

    public static void removeReol(Reol reol){
        if (reoler.contains(reol)){
            reoler.remove(reol);
        }
    }

    public ArrayList<Reol> getReoler() {
        return new ArrayList<>(reoler);
    }
}
