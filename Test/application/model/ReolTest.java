package application.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReolTest {

    @Test
    void constructorThrowsExceptionIkkeGyldigInformation() {
        Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Reol(null, 1));
        assertEquals("Lager kan ikke være null.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Reol(lager, 0));
        assertEquals("ReolID skal være et tal over 0.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Reol(lager, -1));
        assertEquals("ReolID skal være et tal over 0.", exception3.getMessage());
    }

    @Test
    void constructorInitialisereKorrekt() {
        Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);
        Reol reol = new Reol(lager, 1);

        assertEquals(1, reol.getReolID());
        assertEquals(lager, reol.getLager());
        assertNotNull(reol.getHyldePladser());
        assertTrue(reol.getHyldePladser().isEmpty());
    }


    @Test
    void createHyldePlads() {
            Lager lager = new Lager("Lager 1", "Central lager", "Lagervej 1", 10);
            Reol reol = lager.createReol();

            HyldePlads hyldePlads = reol.createHyldePlads();

            assertNotNull(hyldePlads);
            assertEquals(1, hyldePlads.getHyldePladsID());
            assertEquals(1, reol.getHyldePladser().size());
            assertTrue(reol.getHyldePladser().contains(hyldePlads));
        }


    @Test
    void removeHyldePlads() {
        Lager lager = new Lager("Lager 1", "Central lager","Lagervej 1",10);
        Reol reol = lager.createReol();

        HyldePlads hyldePlads1 = reol.createHyldePlads();
        HyldePlads hyldePlads2 = reol.createHyldePlads();

        // hyldePlads 1 er fri.
        hyldePlads1.setPladsFri(true);

        reol.removeHyldePlads(hyldePlads1);
        assertEquals(1, reol.getHyldePladser().size());
        assertFalse(reol.getHyldePladser().contains(hyldePlads1));

        // HyldePlads2 optaget
        reol.removeHyldePlads(hyldePlads2);
        assertEquals(1, reol.getHyldePladser().size());
        assertTrue(reol.getHyldePladser().contains(hyldePlads2));
    }
}