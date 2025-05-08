package application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MaltBatchTest {


    @Test
    void constructorTestForInvalideInput()  {
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(new Malt("byg", "Mark 1", 20.0));

        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                new MaltBatch(null, LocalDate.now(), 100.0, maltList));
        assertEquals("Batchnummer kan ikke være null eller tom.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                new MaltBatch("MB001", null, 100.0, maltList));
        assertEquals("Dato kan ikke være null.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                new MaltBatch("MB001", LocalDate.now(), -10.0, maltList));
        assertEquals("Mængde skal være et tal over 0.", exception3.getMessage());
}

    @Test
    void constructorForKorrektInitialisering() {
        ArrayList<Malt> maltList = new ArrayList<>();
        maltList.add(new Malt("byg", "Mark 1", 20.0));

        MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2023, 1, 1), 100.0, maltList);

        assertEquals("MB001", maltBatch.getBatchNummer());
        assertEquals(LocalDate.of(2023, 1, 1), maltBatch.getDato());
        assertEquals(100.0, maltBatch.getMængde());
        assertEquals(1, maltBatch.getMalt().size());
    }

    @Test
    void createMaltAddTilMaltList() {
            ArrayList<Malt> maltList = new ArrayList<>();
            MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2023, 1, 1), 100.0, maltList);

            maltBatch.createMalt("byg", "Mark 1", 20.0);

            assertEquals(1, maltBatch.getMalt().size());
            assertEquals("byg", maltBatch.getMalt().get(0).getKornSort());
            assertEquals("Mark 1", maltBatch.getMalt().get(0).getMarkNavn());
            assertEquals(20.0, maltBatch.getMalt().get(0).getMængde());
        }
    }
