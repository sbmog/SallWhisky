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

        fad = new Fad(1,
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

}