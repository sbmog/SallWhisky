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
        lager.createReol(); // Reol 1
        Reol reol = lager.getReoler().get(0);
        Storage.addReol(reol);


        // Hyldeplads
        HyldePlads hyldePlads1 = reol.createHyldePlads(); // Hylde 1
        Storage.addHylde(hyldePlads1);

        HyldePlads hyldePlads2 = reol.createHyldePlads(); // Hylde 2
        Storage.addHylde(hyldePlads2);

        //maltbatch & malt
        MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2020, 1, 1), 300.0, new ArrayList<>());
        Storage.addMaltBatch(maltBatch);
        maltBatch.createMalt("Byg", "Mark 1", 20.0);


        // Destillat
        Destillat destillat = new Destillat("SNIPER", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 100.0, 60.0, false, 50.0, new MaltBatch("SNIPER", LocalDate.of(2020, 1, 1), 100.0, new ArrayList<>()));
        Storage.addDestillat(destillat);

        // Fad
        Fad fad1 = new Fad(500, "Eg","FAD APS",1,new FadType("Sherry"));
        Storage.addFad(fad1);

        // Påfyldning
        Påfyldning påfyldning = new Påfyldning("SNIPER",100, LocalDate.of(2020, 1, 1), fad1, destillat);
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
