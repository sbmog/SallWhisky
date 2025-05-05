package storage;

import application.model.*;

import java.util.ArrayList;

public class Storage {
    private static ArrayList<Destillat> destillater = new ArrayList<>();
    private static ArrayList<Fad> fade = new ArrayList<>();
    private static ArrayList<Påfyldning> påfyldninger = new ArrayList<>();
    private static ArrayList<FadType> fadTyper = new ArrayList<>();
    private static ArrayList<MaltBatch> maltBatches = new ArrayList<>();
    private static ArrayList<Malt> malter = new ArrayList<>();
    private static ArrayList<Tapning> tapninger = new ArrayList<>();
    private static ArrayList<Whisky> whiskyer = new ArrayList<>();

    private static ArrayList<Lager> lagre = new ArrayList<>();
    private static ArrayList<HyldePlads> hyldePladser = new ArrayList<>();
    private static ArrayList<Reol> reoler = new ArrayList<>();


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

    public static ArrayList<Destillat> getDestillater() {
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

    public static ArrayList<Fad> getFade() {
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

    public static ArrayList<Påfyldning> getPåfyldninger() {
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

    public static ArrayList<FadType> getFadTyper() {
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

    public static ArrayList<MaltBatch> getMaltBatches() {
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

    public static ArrayList<Malt> getMalter() {
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

    public static ArrayList<Tapning> getTapninger() {
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

    public static ArrayList<Whisky> getWhiskyer() {
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
    public static ArrayList<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }


    //Hylde
    public static void addHylde(HyldePlads hylde){

        if (!hyldePladser.contains(hylde)){
            hyldePladser.add(hylde);
        }
    }

    public static void removeHylde(HyldePlads hylde){


        if (hyldePladser.contains(hylde)){
            hyldePladser.remove(hylde);
        }
    }

    public static ArrayList<HyldePlads> getHylder() {
 
        return new ArrayList<>(hyldePladser);
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

    public static ArrayList<Reol> getReoler() {
        return new ArrayList<>(reoler);
    }
}
