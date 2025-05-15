package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReolTest {

    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;
    private Tapning tapning;
    private Lager lager;
    private Reol reol;
    private HyldePlads hyldePlads;

    @BeforeEach
    void setUp() {
        MaltBatch maltBatch = new MaltBatch("B1", LocalDate.of(2020, 1, 1), 40.0, new ArrayList<>());

        lager = new Lager("Lager1",
                "Baghaven",
                "Baghavevej 1",
                10);

        reol = new Reol(lager, 1);

        hyldePlads = new HyldePlads(1, reol);

        destillat = new Destillat("NJ1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 2),
                70.0,
                60.0,
                false,
                50.0,
                maltBatch);

        fad = new Fad(
                50.0,
                "Eg",
                "Spanien",

                new FadType("Sherry"));

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad,
                destillat,
                hyldePlads);

        fad.setPåfyldning(påfyldning);

        tapning = new Tapning(LocalDate.of(2023, 1, 5),
                "NJ",
                50.0,
                fad);
        fad.setTapning(tapning);
    }

    @Test
    void constructorThrowsExceptionIkkeGyldigInformation() {
        Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Reol(null, 1));
        assertEquals("Lager kan ikke være null.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Reol(lager, 0));
        assertEquals("ReolID skal være et tal over 0.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Reol(lager, -1));
        assertEquals("ReolID skal være et tal over 0.", exception3.getMessage());
    }

    @Test
    void constructorInitialisereKorrekt() {
        Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);
        Reol reol = new Reol(lager, 1);

        assertEquals(1, reol.getReolID());
        assertEquals(lager, reol.getLager());
        assertNotNull(reol.getHyldePladser());
        assertTrue(reol.getHyldePladser().isEmpty());
    }


    @Test
    void createHyldePlads() {
            Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);
            Reol reol = lager.createReol();

            HyldePlads hyldePlads = reol.createHyldePlads();

            assertNotNull(hyldePlads);
            assertEquals(1, hyldePlads.getHyldePladsID());
            assertEquals(1, reol.getHyldePladser().size());
            assertTrue(reol.getHyldePladser().contains(hyldePlads));
        }


    @Test
    void removeHyldePlads() {
        Lager lager = new Lager("Lager 1", "Central lager","Lagervej 1",10);
        Reol reol = lager.createReol();

        HyldePlads hyldePlads1 = reol.createHyldePlads();
        HyldePlads hyldePlads2 = reol.createHyldePlads();
        FadPlacering fadPlacering = new FadPlacering(LocalDate.of(2025, 5,5), fad, hyldePlads2);

        // hyldePlads 1 er fri.
        hyldePlads1.setPladsFri();

        reol.removeHyldePlads(hyldePlads1);
        assertEquals(1, reol.getHyldePladser().size());
        assertFalse(reol.getHyldePladser().contains(hyldePlads1));

        // HyldePlads2 optaget
        reol.removeHyldePlads(hyldePlads2);
        assertEquals(1, reol.getHyldePladser().size());
        assertTrue(reol.getHyldePladser().contains(hyldePlads2));
    }

    @Test
    void hyldePladsIDUnderOrEqualToZeroThrowsException() {
        Reol reol = new Reol(new Lager("Lager 1", "Central lager", "Lagervej 1", 10), 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HyldePlads(0, reol);
        });
        assertEquals("HyldePladsID skal være et tal over 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new HyldePlads(-1, reol);
        });
        assertEquals("HyldePladsID skal være et tal over 0.", exception.getMessage());
    }

    @Test
    void reolNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new HyldePlads(1, null);
        });
        assertEquals("Reol kan ikke være null.", exception.getMessage());
    }
}