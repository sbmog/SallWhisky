package application.controller;

import application.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {



    @Test
    void createDestillat() {
        //Tester oprettelsen af destilliat, med korrekt alkoholprocent.

        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB001", LocalDate.of(2020, 1, 1), 300, maltList);
        maltbatch.createMalt("Byg", "Mark 1", 20.0);


        Destillat destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 50.0, 60.0, false, 200.0, maltbatch);

        assertNotNull(destillat);
        assertEquals("Destillat1", destillat.getDestillatID());
        assertEquals(60, destillat.getAlkoholProcent());
        assertEquals(1, maltbatch.getMalt().size());
        assertEquals("Byg", maltbatch.getMalt().get(0).getKornSort());

    }

    @Test
    void createDestillatForkert() {
        ////Tester oprettelsen af destilliat, med forkert alkoholprocent.
        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB002", LocalDate.of(2020, 1, 1), 20, maltList);

        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createDestillat("ForkertDestiliat", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 2, 2), 50, 59.0, false, 200, maltbatch);
        });
    }

    @Test
    void createFad() {
        Fad fad = Controller.createFad(1, 200, "Eg", "FadAPS", 1, null, new FadType("Sherry"));

        assertNotNull(fad);
        assertEquals(1,fad.getFadID());
        assertEquals(200,fad.getFadILiter());
        assertEquals("Eg",fad.getMateriale());
        assertEquals("FadAPS",fad.getLeverandør());
        assertEquals(1,fad.getAntalGangeBrugt());
    }

    @Test
    void createFadForkert() {
        FadType fadType = Controller.createFadType("Sherry");

        // Test 1: Fad liter > 500
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(1, 600.0, "Eg", "Spanien", 1, null, fadType);
        });
        assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception1.getMessage());

        // Test 2: Null leverandør
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(2, 400.0, "Eg", null, 1, null, fadType);
        });
        assertEquals("Leverandør og/eller Materiale kan ikke være null eller tom.", exception2.getMessage());

        // Test 3: Negative antalGangeBrugt
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(3, 400.0, "Eg", "Spanien", -1, null, fadType);
        });
        assertEquals("Antal gange brugt kan ikke være negativ.", exception3.getMessage());

        // Test 4: Fad ID <= 0
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(0, 400.0, "Eg", "Spanien", 1, null, fadType);
        });
        assertEquals("Fad ID kan ikke være negativ eller 0.", exception4.getMessage());

        // Test 5: Null fadType
        Exception exception5 = assertThrows(NullPointerException.class, () -> {
            Controller.createFad(4, 400.0, "Eg", "Spanien", 1, null, null);
        });
        assertEquals("FadType og/eller Påfyldning kan ikke være null.", exception5.getMessage());
    }

    @Test
    void createMaltBatch() {

        ArrayList<Malt> maltList = new ArrayList<>();
        Malt malt = new Malt("Byg", "Mark 1", 20.0);
        maltList.add(malt);
        MaltBatch maltBatch = Controller.createMaltBatch("MB001", LocalDate.of(2020, 1, 1), 300, maltList);
        assertNotNull(maltBatch);
        assertEquals("MB001", maltBatch.getBatchNummer());
        assertEquals(LocalDate.of(2020, 1, 1), maltBatch.getDato());
        assertEquals(300, maltBatch.getMængde());
        assertEquals(1, maltBatch.getMalt().size());

    }

    @Test
    void createPåfyldning() {
        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltBatch = Controller.createMaltBatch("MB001", LocalDate.of(2020, 1, 1), 300, maltList);
        maltBatch.createMalt("Byg", "Mark 1", 20.0);

        Destillat destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 50.0, 60.0, false, 200.0, maltBatch);
        FadType fadType = Controller.createFadType("Sherry");
        Fad fad = Controller.createFad(1, 200, "Eg", "FadAPS", 1, null, fadType);

        Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, destillat);
        fad.setPåfyldning(påfyldning);

        assertNotNull(påfyldning);
        assertEquals("SNIPE", påfyldning.getInitialerForMedarbejder());
        assertEquals(50, påfyldning.getAntalLiterPåfyldt());
        assertEquals(LocalDate.of(2020, 1, 4), påfyldning.getDatoForPåfyldning());
        assertEquals(fad, påfyldning.getFad());
        assertEquals(destillat, påfyldning.getDestillat());
    }

    @Test
    void createTapning() {
    }

    @Test
    void createWhisky() {
    }

    @Test
    void createLager() {
    }

    @Test
    void createFadType() {
    }
}