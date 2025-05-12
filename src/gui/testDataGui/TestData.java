package gui.testDataGui;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestData {

    public static void initTestData() {
        // Lager
        Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 100);
        Storage.addLager(lager);

        // Reol
        Reol reol = lager.createReol(); // Reol 1

        // Hyldeplads
        HyldePlads hyldePlads1 = reol.createHyldePlads(); // Hylde 1

        HyldePlads hyldePlads2 = reol.createHyldePlads(); // Hylde 2

        //maltbatch & malt
        MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2020, 1, 1), 300.0, new ArrayList<>());
        Storage.addMaltBatch(maltBatch);
        maltBatch.createMalt("Byg", "Mark 1", 20.0);

        // Destillat
        Destillat destillat = new Destillat("DS1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 100.0, 60.0, false, 50.0, maltBatch);
        Storage.addDestillat(destillat);

        // Fadtype
        FadType nyFadType = new FadType("Ny");
        FadType bourbonFadType = new FadType(" Ex-Bourbonfad");
        FadType olorosoFadType = new FadType("Ex-Oloroso Sherryfad");
        FadType pxSherryFadType = new FadType("Ex-Pedro Ximénez SherryFad");
        FadType laphroaigFadType = new FadType("Ex-Laphroaig Bourbonfad");
        FadType manzanillaFadType = new FadType("Ex-Manzanilla Sherryfad");
        Storage.addFadType(nyFadType);
        Storage.addFadType(bourbonFadType);
        Storage.addFadType(olorosoFadType);
        Storage.addFadType(pxSherryFadType);
        Storage.addFadType(laphroaigFadType);
        Storage.addFadType(manzanillaFadType);

        // Fad
        Fad fad1 = new Fad(500, "Amerikansk Eg", "FAD APS", 1, olorosoFadType);
        Storage.addFad(fad1);

        // Påfyldning
        Påfyldning påfyldning = new Påfyldning("SNIPER", 100, LocalDate.of(2020, 1, 1), fad1, destillat, hyldePlads1);
        fad1.setPåfyldning(påfyldning);
        Storage.addPåfyldning(påfyldning);

        // Tapning (efter 3 år)
        Tapning tapning = new Tapning(LocalDate.of(2023, 3, 1), "MK", 100, fad1);
        tapning.createFortynding(10.0);
        Storage.addTapning(tapning);

        // Whisky
        ArrayList<Tapning> tapninger = new ArrayList<>();
        tapninger.add(tapning);

        Whisky whisky = new Whisky(1, "Classic Oak", 46.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);
        Storage.addWhisky(whisky);

        // Flaske
        whisky.createFlaske(); // Flaske 1
        whisky.createFlaske(); // Flaske 2
    }
}
