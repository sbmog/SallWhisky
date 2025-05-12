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
    private Tapning tapning;
    private Whisky whisky;
    private Lager lager;
    private ArrayList<Tapning> tapninger;

    @BeforeEach
    void resetFadIdCounter() {
        Fad.resetIDCounter();
    }
    @BeforeEach
    void setUp() {
        maltList = new ArrayList<>();
        maltBatch = Controller.createMaltBatch("MB001", LocalDate.of(2020, 1, 1), 300, maltList);
        maltBatch.createMalt("Byg", "Mark 1", 20.0);

        destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), 50.0, 60.0, false, 200.0, maltBatch);

        fadType = Controller.createFadType("Sherry");
        fad = Controller.createFad( 200, "Eg", "FadAPS", 0, fadType);

        påfyldning = Controller.createPåfyldning("SNIPE", 50.0, LocalDate.of(2020, 1, 4), fad, destillat);
        fad.setPåfyldning(påfyldning);

        tapninger = new ArrayList<>();
        Tapning tapning = Controller.createTapning(LocalDate.of(2025, 1, 1), "SNIPE", 30, fad);
        this.tapning = tapning;
        tapninger.add(tapning);

        whisky = Controller.createWhisky(1, "TestWhisky", 45.0, false, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        lager = Controller.createLager("Lager1", "Baghave", "Baghavevej 1", 10);
        lager.createReol();
    }

        @Test
        void createDestillat () {
            assertNotNull(destillat);
            assertEquals("Destillat1", destillat.getDestillatID());
            assertEquals(60, destillat.getAlkoholProcent());
            assertEquals(1, maltBatch.getMalt().size());
            assertEquals("Byg", maltBatch.getMalt().get(0).getKornSort());
        }

        @Test
        void createDestillatForkert () {
            assertThrows(IllegalArgumentException.class, () -> {
                Controller.createDestillat("ForkertDestillat", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 2, 2), 50, 59.0, false, 200, maltBatch);
            });
        }

        @Test
        void createFad () {
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
            Controller.createFad(600.0, "Eg", "Spanien", 1, fadType);
        });
        assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception1.getMessage());

        // Test 2: Null leverandør
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(400.0, "Eg", null, 1, fadType);
        });
        assertEquals("Leverandør og/eller Materiale kan ikke være null eller tom.", exception2.getMessage());

        // Test 3: Null fadtype
        Exception exception5 = assertThrows(NullPointerException.class, () -> {
            Controller.createFad(400.0, "Eg", "Spanien", 1, null);
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
        assertNotNull(whisky);
        assertEquals(1, whisky.getWhiskyID());
        assertEquals("TestWhisky", whisky.getNavn());
        assertEquals(45.0, whisky.getAlkoholProcent());
        assertEquals(10.0, whisky.getVandMængde());
        assertEquals(WhiskyType.SINGLE_MALT, whisky.getWhiskyType());
        assertEquals(1, whisky.getTapninger().size());
        assertEquals(tapning, whisky.getTapninger().get(0));
    }

    @Test
    void createLager() {
        assertNotNull(lager);
        assertEquals("Lager1", lager.getLagerID());
        assertEquals("Baghave", lager.getNavn());
        assertEquals("Baghavevej 1", lager.getAdresse());
        assertEquals(10, lager.getMaxAntalReoler());
    }

    @Test
    void createFadType() {
        assertNotNull(fadType);
        assertEquals("Sherry", fadType.getNavn());
    }
}