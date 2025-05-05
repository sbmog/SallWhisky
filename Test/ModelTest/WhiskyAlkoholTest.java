package ModelTest;

import application.model.Whisky;
import application.model.Tapning;
import application.model.WhiskyType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WhiskyAlkoholTest {

    @Test
    void testWhiskyAlkoholprocentUnder() {
        ArrayList<Tapning> tapninger = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Whisky(1, "Whisky A", 39.0, false, 10.0, tapninger, WhiskyType.SINGLE_MALT);
        });
    }

    @Test
    void testWhiskyAlkoholprocent40() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(1, "Whisky A", 40.0, false, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(40.0, result);
    }

    @Test
    void testWhiskyAlkoholprocentOver() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(2, "Whisky B", 41.0, false, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(41.0, result);
    }
}
