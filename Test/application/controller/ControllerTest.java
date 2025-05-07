package application.controller;
import application.controller.Controller;
import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private ArrayList<Malt> maltList;
    private MaltBatch maltBatch;
    private Destillat destillat;
    private FadType fadType;
    private Fad fad;
    private Påfyldning påfyldning;
    private ArrayList<Tapning> tapninger;

    @BeforeEach
    void setUp() {
        maltList = new ArrayList<>();
        maltBatch = Controller.createMaltBatch("MB001", LocalDate.of(2020, 1, 1), 300, maltList);
        maltBatch.createMalt("Byg", "Mark 1", 20.0);

        destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 50.0, 60.0, false, 200.0, maltBatch);

        fadType = Controller.createFadType("Sherry");
        fad = Controller.createFad( 200, "Eg", "FadAPS", 0, null, fadType);

        påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, destillat);
        fad.setPåfyldning(påfyldning);

        tapninger = new ArrayList<>();
        Tapning tapning = Controller.createTapning(LocalDate.of(2025,1,1),"SNIPE",30,fad);
        tapninger.add(tapning);
    }

    @Test
    void createDestillat() {
        assertNotNull(destillat);
        assertEquals("Destillat1", destillat.getDestillatID());
        assertEquals(60, destillat.getAlkoholProcent());
        assertEquals(1, maltBatch.getMalt().size());
        assertEquals("Byg", maltBatch.getMalt().get(0).getKornSort());
    }

    @Test
    void createDestillatForkert() {
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createDestillat("ForkertDestillat", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 2, 2), 50, 59.0, false, 200, maltBatch);
        });
    }

    @Test
    void createFad() {
        assertNotNull(fad);
        assertEquals(1, fad.getFadID());
        assertEquals(200, fad.getFadILiter());
        assertEquals("Eg", fad.getMateriale());
        assertEquals("FadAPS", fad.getLeverandør());
        assertEquals(1, fad.getAntalGangeBrugt());
    }

    @Test
    void createFadForkert() {
        // Test cases for invalid Fad creation
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(600.0, "Eg", "Spanien", 1, null, fadType);
        });
        assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception1.getMessage());

        // Test 2: Null leverandør
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(400.0, "Eg", null, 1, null, fadType);
        });
        assertEquals("Leverandør og/eller Materiale kan ikke være null eller tom.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(400.0, "Eg", "Spanien", -1, null, fadType);
        });
        assertEquals("Antal gange brugt kan ikke være negativ.", exception3.getMessage());


        Exception exception5 = assertThrows(NullPointerException.class, () -> {
            Controller.createFad(400.0, "Eg", "Spanien", 1, null, null);
        });
        assertEquals("FadType kan ikke være null.", exception5.getMessage());
    }

    @Test
    void createMaltBatch() {
        assertNotNull(maltBatch);
        assertEquals("MB001", maltBatch.getBatchNummer());
        assertEquals(LocalDate.of(2020, 1, 1), maltBatch.getDato());
        assertEquals(300, maltBatch.getMængde());
        assertEquals(1, maltBatch.getMalt().size());
    }

    @Test
    void createPåfyldning() {
        assertNotNull(påfyldning);
        assertEquals("SNIPE", påfyldning.getInitialerForMedarbejder());
        assertEquals(50, påfyldning.getAntalLiterPåfyldt());
        assertEquals(LocalDate.of(2020, 1, 4), påfyldning.getDatoForPåfyldning());
        assertEquals(fad, påfyldning.getFad());
        assertEquals(destillat, påfyldning.getDestillat());
    }

    @Test
    void createTapning() {
        assertNotNull(tapninger);
        assertEquals(1,tapninger.size());
        Tapning tapning = tapninger.get(0);
        assertEquals("SNIPE", tapning.getInitialerForMedarbejder());
        assertEquals(30.0,tapning.getAntalLiterFraFad());
        assertEquals(fad,tapning.getFad());

    }

    @Test
    void createWhisky() {
    }

    @Test
    void createLager() {

    }

    @Test
    void createFadType() {
        assertNotNull(fadType);
        assertEquals("Sherry", fadType.getNavn());

    }
}