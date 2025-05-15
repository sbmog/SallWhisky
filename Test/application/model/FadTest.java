package application.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {

    private Destillat destillat;
    private Fad fad;
    private Lager lager;
    private Reol reol;
    private int hyldePladsCounter;

    @BeforeEach
    void setUp() {
        hyldePladsCounter = 0;

        MaltBatch maltBatch = new MaltBatch("B1", LocalDate.of(2020, 1, 1), 40.0, new ArrayList<>());

        lager = new Lager("Lager1", "Baghaven", "Baghavevej 1", 10);
        reol = new Reol(lager, 1);

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.beregnLagringstid();
        });
        assertEquals("Startdato kan ikke være i fremtiden.", exception.getMessage());
    }

    @Test
    void beregnTidTilWhiskyStartDatoInFutureException() {
    new Påfyldning("SNIPE", 50.0, LocalDate.now().plusDays(1), fad, destillat, createUniqueHyldePlads());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.beregnTidTilWhisky();
        });
        assertEquals("Startdato kan ikke være i fremtiden.", exception.getMessage());
    }

    @Test
    void setPåfyldningMaxUsageException() {
        fad.setAntalGangeBrugt(fad.getMaksAntalGangeBrugt());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
        new Påfyldning("SNIPE", 50.0, LocalDate.now(), fad, destillat, createUniqueHyldePlads());
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