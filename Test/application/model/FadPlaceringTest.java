package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadPlaceringTest {
    private Lager lager;
    private Reol reol;
    private HyldePlads hyldePlads;
    private FadType fadType;
    private Fad fad;


    @BeforeEach
    void setUp() {
        lager = Controller.createLager("1", "Lager1", "Adressevej 1", 50);
        lager.createReol();
        reol = lager.getReoler().getFirst();
        hyldePlads = new HyldePlads(3, reol);
        fadType = Controller.createFadType("Blended");
        fad = Controller.createFad(500, "Eg", "Leverandør A", 1, null, fadType);
    }

    @Test
    void constructorThrowsExceptionNårLagerErNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FadPlacering(null, fad, hyldePlads);
        });
        assertEquals("Dato kan ikke være null.",exception.getMessage());
    }

    @Test
    void constructorThrowsExceptionNårFadErNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FadPlacering(LocalDate.now(), null, hyldePlads);
        });
        assertEquals("Fad kan ikke være null.",exception.getMessage());
    }
    @Test
    void constructorThrowsExceptionNårHyldePladsErNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FadPlacering(LocalDate.now(), fad, null);
        });
        assertEquals("HyldePlads kan ikke være null.",exception.getMessage());
    }

    @Test
    void constructorThrowsExceptionNårHyldePladsIkkeErFri() {
        HyldePlads hyldePlads = new HyldePlads(1, reol);
        hyldePlads.setPladsFri(false);
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new FadPlacering(LocalDate.now(), fad, hyldePlads);
        });
        assertEquals("Hyldepladsen er allerede optaget.",exception.getMessage());
    }

    @Test
    void getFuldFadPlacering() {
        FadPlacering fadPlacering = new FadPlacering(LocalDate.now(), fad, hyldePlads);
        fad.setFadPlacering(fadPlacering);

        String result = fadPlacering.getFullFadPlacering();
        assertEquals("Lager1 - 1 - 3", result);

    }

}
