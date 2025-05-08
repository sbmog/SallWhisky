package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import  org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TapningTest {

    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;

    @BeforeEach
    void setUp() {
        MaltBatch maltBatch = new MaltBatch("B1", LocalDate.of(2020, 1, 1), 40.0, new ArrayList<>());

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
                1,
                new FadType("Sherry"));

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad, // Brug fad her
                destillat);

        fad.setPåfyldning(påfyldning);

    }

    @Test
    void opretTapningKorrekt() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 5, 1), "CD", 50, fad);

        assertEquals(fad, tapning.getFad());
    }

    @Test
    void opretTapningForkert() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2020, 1, 1), "CD", 50, fad);
        });
    }
    @Test
    void tapningsDatoNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(null, "CD", 50, fad);
        });
        assertEquals("Tapningsdato kan ikke være null.", exception.getMessage());
    }

    @Test
    void initialerForMedarbejderNullOrEmptyThrowsException() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2025, 5, 1), null, 50, fad);
        });
        assertEquals("Initialer for medarbejder kan ikke være null eller tom.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2025, 5, 1), "", 50, fad);
        });
        assertEquals("Initialer for medarbejder kan ikke være null eller tom.", exception2.getMessage());
    }

    @Test
    void antalLiterFraFadUnderEllerLigNulThrowsException() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2025, 5, 1), "CD", 0, fad);
        });
        assertEquals("Antal liter fra fad skal være større end 0.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2025, 5, 1), "CD", -10, fad);
        });
        assertEquals("Antal liter fra fad skal være større end 0.", exception2.getMessage());
    }

    @Test
    void tapningsDatoBeforePåfyldningsDatoThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2019, 12, 31), "CD", 50, fad);
        });
        assertEquals("Tapningsdato kan ikke være før påfyldningsdato.", exception.getMessage());
    }

    @Test
    void tapningsDatoBeforeThreeYearsAfterPåfyldningsDatoThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Tapning(LocalDate.of(2022, 1, 3), "CD", 50, fad);
        });
        assertEquals("Destillatet kan ikke tappes før den har lagret i 3 år.", exception.getMessage());
    }
}