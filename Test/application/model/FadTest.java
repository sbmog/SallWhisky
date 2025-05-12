package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {

    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;
    private Tapning tapning;
    private Lager lager;
    private Reol reol;
    private int hyldePladsCounter;

    @BeforeEach
    void setUp() {
        MaltBatch maltBatch = new MaltBatch("B1", LocalDate.of(2020, 1, 1), 40.0, new ArrayList<>());

        lager = new Lager("Lager1",
                "Baghaven",
                "Baghavevej 1",
                10);

        reol = new Reol(lager, 1);

        // Brug en tæller til at sikre unikke HyldePlads-objekter
        hyldePladsCounter = 0;

        destillat = new Destillat("NJ1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 2),
                70.0,
                60.0,
                false,
                50.0,
                maltBatch);

        fad = new Fad(
                500.0,
                "Eg",
                "Spanien",
                new FadType("Sherry"));

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad,
                destillat,
                createUniqueHyldePlads());

        fad.setPåfyldning(påfyldning);

        tapning = new Tapning(LocalDate.of(2023, 1, 5),
                "NJ",
                50.0,
                fad);
        fad.setTapning(tapning);
    }

    private HyldePlads createUniqueHyldePlads() {
        return new HyldePlads(++hyldePladsCounter, reol);
    }

    @Test
    void fadStørrelseOverMaxException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Fad(600.0, "Eg", "Spanien", new FadType("Sherry"));
        });
        assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception.getMessage());
    }

    @Test
    void leverandørEllerMaterialeNullOrEmptyException() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Fad(200.0, null, "Spanien", new FadType("Sherry"));
        });
        assertEquals("Leverandør og/eller Materiale kan ikke være null eller tom.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Fad(200.0, "Eg", "", new FadType("Sherry"));
        });
        assertEquals("Leverandør og/eller Materiale kan ikke være null eller tom.", exception2.getMessage());
    }

    @Test
    void fadTypeNullException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Fad(200.0, "Eg", "Spanien", null);
        });
        assertEquals("FadType kan ikke være null.", exception.getMessage());
    }

    @Test
    void beregnLagringstidStartDatoInFutureThrowsException() {
        Påfyldning futurePåfyldning = new Påfyldning("SNIPE", 50.0, LocalDate.now().plusDays(1), fad, destillat, createUniqueHyldePlads());
        fad.setPåfyldning(futurePåfyldning);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.BeregnLagringstid();
        });
        assertEquals("Startdato kan ikke være i fremtiden.", exception.getMessage());
    }

    @Test
    void beregnTidTilWhiskyStartDatoInFutureException() {
        Påfyldning futurePåfyldning = new Påfyldning("SNIPE", 50.0, LocalDate.now().plusDays(1), fad, destillat, createUniqueHyldePlads());
        fad.setPåfyldning(futurePåfyldning);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.beregnTidTilWhisky();
        });
        assertEquals("Startdato kan ikke være i fremtiden.", exception.getMessage());
    }

    @Test
    void setPåfyldningMaxUsageException() {
        fad.setAntalGangeBrugt(fad.getMaksAntalGangeBrugt());
        Påfyldning newPåfyldning = new Påfyldning("SNIPE", 50.0, LocalDate.now(), fad, destillat, createUniqueHyldePlads());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            fad.setPåfyldning(newPåfyldning);
        });
        assertEquals("Fadet kan ikke bruges mere end " + fad.getMaksAntalGangeBrugt() + " gange.", exception.getMessage());
    }

    @Test
    void setFadILiterOverMaxException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.setFadILiter(600.0);
        });
        assertEquals("Fad størrelse kan ikke være over 500.0 liter.", exception.getMessage());
    }
}