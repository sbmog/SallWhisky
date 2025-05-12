package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlaskeTest {
    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;
    private Whisky whisky;

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
                new FadType("Sherry"));

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad,
                destillat);

        fad.setPåfyldning(påfyldning);

        ArrayList<Tapning> tapninger = new ArrayList<>();
        tapninger.add(new Tapning(LocalDate.of(2025, 5, 7), "Test", 10.0, fad));
        whisky = new Whisky(1, "Test Whisky", 43.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);
    }

    @Test
    void testFlaskeConstructor() {
        Flaske flaske = new Flaske(1, whisky);

        assertEquals(1, flaske.getFlaskeID());
        assertEquals(whisky, flaske.getWhisky());
    }

    @Test
    void flaskeIDUnderOrEqualToZeroThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flaske(0, whisky);
        });
        assertEquals("FlaskeID skal være et tal over 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flaske(-1, whisky);
        });
        assertEquals("FlaskeID skal være et tal over 0.", exception.getMessage());
    }

    @Test
    void whiskyNullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flaske(1, null);
        });
        assertEquals("Whisky kan ikke være null.", exception.getMessage());
    }
}