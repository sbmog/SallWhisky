package ModelTest;

import application.controller.Controller;
import application.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadPlaceringTest {

    @Test
    void getFuldFadPlacering() {

            Lager lager = Controller.createLager("1", "Lager1", "Adressevej 1");
            lager.createReol();
            Reol reol = lager.getReoler().get(0);


            HyldePlads hyldePlads = new HyldePlads(3, reol);  // Opret hyldeplads med ID 3
            reol.createHyldePlads(); // Tilføj hyldepladsen til reolen, hvis nødvendigt
            FadType fadType = Controller.createFadType("Blended");
            Fad fad = Controller.createFad(100, 200.0, "Eg", "Leverandør A", 1, null, fadType, null);
            FadPlacering fadPlacering = new FadPlacering(LocalDate.now(), fad, hyldePlads);
            fad.setFadPlacering(fadPlacering);

            String result = fadPlacering.getFullFadPlacering();


            assertEquals("Lager1 - 1 - 3", result);
        }
    }
