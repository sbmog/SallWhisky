package application.model;

import application.controller.Controller;
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
                new FadType("Sherry"),
                null); // Midlertidigt sæt påfyldning til null

        påfyldning = new Påfyldning("SNIPE",
                50.0,
                LocalDate.of(2020, 1, 4),
                fad, // Brug fad her
                destillat);

        fad.setPåfyldning(påfyldning); // Tildel påfyldningen til fadet

        tapning = new Tapning(LocalDate.of(2023, 1, 5),
                "NJ",
                50.0,
                fad);
        fad.setTapning(tapning);
    }

    @Test
    void placerPåHylde() {
        Lager lager = Controller.createLager("1", "Lager1", "Adressevej 1", 50);
        lager.createReol();
        Reol reol = lager.getReoler().getFirst();

        reol.createHyldePlads();
        HyldePlads hyldePlads = reol.getHyldePladser().get(0);

        assertTrue(hyldePlads.isPladsFri());

        fad.placerPåHylde(hyldePlads, LocalDate.of(2025, 5, 5));

        assertFalse(hyldePlads.isPladsFri());
        assertEquals(hyldePlads, fad.getFadPlacering().getHyldePlads());
    }

    @Test
    void beregnAntalFlasker() {
        int antalFlasker = fad.beregnAntalFlasker(0.7);
        assertEquals(71, antalFlasker); // 50.0 / 0.7 ≈ 71 flasker
    }

    @Test
    void beregnLagringstid() {
        long dage = fad.BeregnLagringstid(); // baseret på påfyldningsdatoen
        assertTrue(dage > 0);
    }

    @Test
    void beregnTidTilWhisky() {
        long dageTilWhisky = fad.beregnTidTilWhisky();
        assertTrue(dageTilWhisky >= 0);
    }

    @Test
    void tilføjPåfyldning() {
        Destillat nytDestillat = new Destillat("NJ2",
                LocalDate.of(2021, 5, 1),
                LocalDate.of(2021, 5, 2),
                65.0,
                61.0,
                true,
                40.0,
                new MaltBatch("B2", LocalDate.of(2021, 4, 1), 30.0, new ArrayList<>()));

        Påfyldning nyPåfyldning = new Påfyldning("SNIPE",
                40.0,
                LocalDate.of(2021, 5, 3),
                fad,
                nytDestillat);

        fad.setPåfyldning(nyPåfyldning);

        assertEquals(nyPåfyldning, fad.getPåfyldning());
    }
}
