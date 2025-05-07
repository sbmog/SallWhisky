package application.controller;

import application.model.*;
import org.junit.jupiter.api.Test;

import javax.naming.ldap.Control;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {



    @Test
    void createDestillat() {
    //Tester oprettelsen af destilliat, med korrekt alkoholprocent.

        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB001", LocalDate.of(2020,1,1),300,maltList);
        maltbatch.createMalt("Byg","Mark 1",20.0);


        Destillat destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020,1,1), LocalDate.of(2020,1,2), 50.0, 60.0, false, 200.0, maltbatch);

        assertNotNull(destillat);
        assertEquals("Destillat1", destillat.getDestillatID());
        assertEquals(60,destillat.getAlkoholProcent());
        assertEquals(1,maltbatch.getMalt().size());
        assertEquals("Byg",maltbatch.getMalt().get(0).getKornSort());

    }

    @Test
    void createDestillatForkert() {
        ////Tester oprettelsen af destilliat, med forkert alkoholprocent.
        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB002",LocalDate.of(2020,1,1),20,maltList);

    assertThrows(IllegalArgumentException.class,() ->{
        Controller.createDestillat("ForkertDestiliat",LocalDate.of(2020,2,1), LocalDate.of(2020,2,2),50,59.0,false,200,maltbatch);
    });
    }

    @Test
    void createFadKorrekt() {
        Fad fad = Controller.createFad(1,200,"Eg","FadAPS",1, null,null,null);

        assertNotNull(fad);
        assertEquals(1,fad.getFadID());
        assertEquals(200,fad.getFadILiter());
        assertEquals("Eg",fad.getMateriale());
        assertEquals("FadAPS",fad.getLeverandør());
        assertEquals(1,fad.getAntalGangeBrugt());
    }

//    @Test
//    void createFadForkert() {
//            FadType fadType = Controller.createFadType("Sherry");
//
//            // Test 1: Fad liter > 500
//            Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
//                Fad fad = new Fad(1, 600.0, "Eg", "Spanien", 1, fadType, null);  // valid fad for Påfyldning
//                Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, null);
//                Controller.createFad(1, 600.0, "Eg", "Spanien", 1, null, fadType, påfyldning);
//            });
//            assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception1.getMessage());
//
//            // Test 2: Null leverandør
//            Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
//                Fad fad = new Fad(1, 400.0, "Eg", null, 0, fadType, null);
//                Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, null);
//                Controller.createFad(2, 400.0, "Eg", null, 1, null, fadType, påfyldning);
//            });
//            assertEquals("Leverandør og Materiale kan ikke være null eller tom.", exception2.getMessage());
//
//            // Test 3: Negative antalGangeBrugt
//            Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
//                Fad fad = new Fad(1, 400.0, "Eg", "Spanien", 0, fadType, null);
//                Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, null);
//                Controller.createFad(3, 400.0, "Eg", "Spanien", -1, null, fadType, påfyldning);
//            });
//            assertEquals("Antal gange brugt kan ikke være negativ.", exception3.getMessage());
//
//            // Test 4: Fad ID <= 0
//            Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
//                Fad fad = new Fad(1, 400.0, "Eg", "Spanien", 0, fadType, null);
//                Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, null);
//                Controller.createFad(0, 400.0, "Eg", "Spanien", 1, null, fadType, påfyldning);
//            });
//            assertEquals("Fad ID kan ikke være negativ eller 0.", exception4.getMessage());
//
//            // Test 5: Null fadType
//            Exception exception5 = assertThrows(IllegalArgumentException.class, () -> {
//                Fad fad = new Fad(1, 400.0, "Eg", "Spanien", 0, fadType, null);
//                Påfyldning påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, null);
//                Controller.createFad(4, 400.0, "Eg", "Spanien", 1, null, null, påfyldning);
//            });
//            assertEquals("FadType og Påfyldning kan ikke være null.", exception5.getMessage());
//
//            // Test 6: Null fad in Påfyldning
//            Exception exception6 = assertThrows(IllegalArgumentException.class, () -> {
//                Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), null, null);
//            });
//            assertEquals("Fad kan ikke være null.", exception6.getMessage());
//        }

    @Test
    void createMaltBatch() {

        ArrayList<Malt> maltList = new ArrayList<>();

       MaltBatch maltBatch = Controller.createMaltBatch("MB001",LocalDate.of(2020,1,1),300,maltList);

       assertNotNull(maltBatch);
       assertEquals("MB001", maltBatch.getBatchNummer());
       assertEquals(LocalDate.of(2020,1,1), maltBatch.getDato());
       assertEquals(300,maltBatch.getMængde());
       assertEquals(1,maltBatch.getMalt().size());

    }

    @Test
    void createPåfyldning() {
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