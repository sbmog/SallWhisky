package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {

    private Malt malt;
    private ArrayList<Malt> maltList;
    private MaltBatch maltBatch;

    @BeforeEach
    void setUp() {
        malt = new Malt("byg", "Mark 1", 20.0);
        maltList = new ArrayList<>();
        maltList.add(malt);
        maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);
    }

    @Test
    void testDestillatAlkoholprocent60() {
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 60.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(60.0, result);
    }

    @Test
    void testDestillatAlkoholprocent62() {
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 62.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(62.0, result);
    }

    @Test
    void testDestillatAlkoholprocent63() {
        Destillat destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 63.0, false, 50.0, maltBatch);

        double result = destillat.getAlkoholProcent();
        assertEquals(63.0, result);
    }

    @Test
    void testDestillatAlkoholprocentUnder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 59.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatAlkoholprocentOver() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 64.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatStartDatoAfterSlutDato() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 6), LocalDate.of(2025, 5, 5), 100.0, 60.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatLiterVandNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), -100.0, 60.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatLiterVand0() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 0.0, 60.0, false, 50.0, maltBatch);
        });
    }

    @Test
    void testDestillatMaltBatchNull() {
        assertThrows(NullPointerException.class, () -> {
            new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 60.0, false, 50.0, null);
        });
    }
}