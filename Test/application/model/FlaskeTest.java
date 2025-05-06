package application.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlaskeTest {

    @Test
    void testFlaskeConstructor() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(1, "Test Whisky", 43.0, false, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        Flaske flaske = new Flaske(1, whisky);

        assertEquals(1, flaske.getFlaskeID());
        assertEquals(whisky, flaske.getWhisky());
    }
}