package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TapningTest {

    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;
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
                fad, // Brug fad her
                destillat,
                hyldePlads
        );

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

    @Test
    void beregnAngelShareIProcentKorrektUdregning() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 5, 1), "CD", 45.0, fad);

        double angelShare = Controller.beregnAngelShareIProcent(45.0, fad, LocalDate.of(2025, 5, 1));

        assertEquals(10.0, angelShare, 0.01);
    }

    @Test
    void beregnAngelShareUnder3ÅrThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.beregnAngelShareIProcent(45.0, fad, LocalDate.of(2022, 1, 1));
        });
    }

    @Test
    void beregnAngelShareUdenPåfyldningThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fad.setPåfyldning(null);
        });
        assertEquals("Påfyldning kan ikke være null.", exception.getMessage());
    }


    @Test
    void beregnAntalFlaskerKorrektUdenFortynding() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 5, 1), "CD", 45.0, fad);

        int antalFlasker = tapning.beregnAntalFlasker(50); // 50 cl

        assertEquals(90, antalFlasker);
    }
    @Test
    void beregnAntalFlaskerMedFortynding() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 1, 5), "CD", 50.0, fad);
        tapning.createFortynding(5.0);
        int result = tapning.beregnAntalFlasker(50.0);
        assertEquals(110, result);
    }

    @Test
    void beregnAntalFlaskerMedNulFlaskestørrelseThrowsException() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 1, 5), "CD", 50.0, fad);
        assertThrows(IllegalArgumentException.class, () -> tapning.beregnAntalFlasker(0));
    }

    @Test
    void beregnAntalFlaskerMedNegativFlaskestørrelseThrowsException() {
        Tapning tapning = new Tapning(LocalDate.of(2025, 1, 5), "CD", 50.0, fad);
        assertThrows(IllegalArgumentException.class, () -> tapning.beregnAntalFlasker(-25));
    }
}