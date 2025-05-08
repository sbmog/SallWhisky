package application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlaskeTest {
    private Destillat destillat;
    private Påfyldning påfyldning;
    private Fad fad;


    @Test
    void testFlaskeConstructor() {
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
                new FadType("Sherry")); // Midlertidigt sæt påfyldning til null

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad, // Brug fad her
                destillat);

        fad.setPåfyldning(påfyldning); // Tildel påfyldningen til fadet

        ArrayList<Tapning> tapninger = new ArrayList<>();
        tapninger.add(new Tapning(LocalDate.of(2025, 5, 7), "Test", 10.0, fad));
        Whisky whisky = new Whisky(1, "Test Whisky", 43.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        Flaske flaske = new Flaske(1, whisky);

        assertEquals(1, flaske.getFlaskeID());
        assertEquals(whisky, flaske.getWhisky());
    }
}