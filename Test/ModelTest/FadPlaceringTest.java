package ModelTest;

import application.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadPlaceringTest {

    @Test
    void getFullFadPlacering() {
        Lager lager = new Lager("1", "Lager", "SALL");
        Reol reol = new Reol(lager, 1);
        Hylde hylde = new Hylde(5,reol);
        FadType fadType = new FadType("TestFadType");
        Fad fad = new Fad(101,200.0,"TestFad", "TestFad", 2, fad, 1);
        FadPlacering fadPlacering = new FadPlacering(LocalDate.now(),fad,hylde);

        String result = fadPlacering.getFullFadPlacering();

        assertEquals("Lager - 1 - 5", result);
    }
}