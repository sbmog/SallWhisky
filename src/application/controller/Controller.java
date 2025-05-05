package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {

    public static Destillat createDestillat(String destillatID, LocalDate startDato, LocalDate slutDato, double literVand, double alkoholProcent, boolean røget, double væskemængde, MaltBatch maltBatch) {
        Destillat newDestillat = new Destillat(destillatID, startDato, slutDato, literVand, alkoholProcent, røget, væskemængde, maltBatch);
        Storage.addDestillat(newDestillat);
        return newDestillat;
    }

    public static Fad createFad(int fadID, double fadStørrelse, String materiale, String leverandør, int antalGangeBrugt, FadPlacering fadPlacering, FadType fadType, Påfyldning påfyldning) {
        Fad newFad = new Fad(fadID, fadStørrelse, materiale, leverandør, antalGangeBrugt, fadType, påfyldning);
        Storage.addFad(newFad);
        return newFad;
    }

    public static MaltBatch createMaltBatch(String batchNummer, LocalDate dato, double mængde, ArrayList<Malt> malt) {
        MaltBatch newMaltBatch = new MaltBatch(batchNummer, dato, mængde, malt);
        Storage.addMaltBatch(newMaltBatch);
        return newMaltBatch;
    }

    public static Påfyldning createPåfyldning(String initialerForMedarbejder, double antalLiterPåfyldt, LocalDate datoForPåfyldning, Fad fad, Destillat destillat) {
        Påfyldning newPåfyldning = new Påfyldning(initialerForMedarbejder, antalLiterPåfyldt, datoForPåfyldning, fad, destillat);
        Storage.addPåfyldning(newPåfyldning);
        return newPåfyldning;
    }

    public static Tapning createTapning(LocalDate tapningsDato, String initialerForMedarbejder, double antalLiterFraFad, Fad fad) {
        Tapning newTapning = new Tapning(tapningsDato, initialerForMedarbejder, antalLiterFraFad, fad);
        Storage.addTapning(newTapning);
        return newTapning;
    }

    public static Whisky createWhisky(double whiskyID, String navn, double alkoholProcent, boolean fortyndet, double vandMængde, ArrayList<Tapning> tapninger, WhiskyType whiskyType) {
        Whisky newWhisky = new Whisky(whiskyID, navn, alkoholProcent, fortyndet, vandMængde, tapninger, whiskyType);
        Storage.addWhisky(newWhisky);
        return newWhisky;
    }

    public static Lager createLager(String lagerID, String navn, String adresse, int maxAntalReoler) {
        Lager newLager = new Lager(lagerID, navn, adresse,maxAntalReoler);
        Storage.addLager(newLager);
        return newLager;
    }

    public static FadType createFadType(String navn) {
        FadType newFadType = new FadType(navn);
        Storage.addFadType(newFadType);
        return newFadType;

    }

    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }
}
