package application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {
    @Test
    void testDestillatAlkoholprocent60() {
        Malt malt = new Malt("byg", "Mark 1", 20.0);
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(malt);
        MaltBatch maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 60.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(60.0, result);
    }

    @Test
    void testDestillatAlkoholprocent62() {
        Malt malt = new Malt("byg", "Mark 1", 20.0);
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(malt);
        MaltBatch maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 62.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(62.0, result);
    }

    @Test
    void testDestillatAlkoholprocent63() {
        Malt malt = new Malt("byg", "Mark 1", 20.0);
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(malt);
        MaltBatch maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 63.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(63.0, result);
    }

    @Test
    void testDestillatAlkoholprocentUnder() {
        Malt malt = new Malt("byg", "Mark 1", 20.0);
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(malt);
        MaltBatch maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);

        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 59.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatAlkoholprocentOver() {
        Malt malt = new Malt("byg", "Mark 1", 20.0);
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(malt);
        MaltBatch maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);

        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 64.0, false, 50.0, maltBatch);
        });
    }
}