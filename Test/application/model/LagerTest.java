package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LagerTest {
    Lager lager;

    @BeforeEach
    void setUp() {
        lager = new Lager("L001", "Lager1", "Lagervej 1", 2);
    }

    @Test
    void constructorThrowsExceptionForFejlInput() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Lager(null, "Name", "Address", 1));
        assertEquals("LagerID kan ikke være null eller tom.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Lager("L001", null, "Address", 1));
        assertEquals("Navn kan ikke være null eller tom.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new Lager("L001", "Name", null, 1));
        assertEquals("Adresse kan ikke være null eller tom.", exception3.getMessage());

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new Lager("L001", "Name", "Address", 0));
        assertEquals("Maksimalt antal reoler skal være et tal over 0.", exception4.getMessage());
    }

    @Test
    void constructorInitialisereKorrekt() {
        Lager lager = new Lager("L002", "Sekundær lager", "Lagervej 2", 50);
        assertEquals("L002", lager.getLagerID());
        assertEquals("Sekundær lager", lager.getNavn());
        assertEquals("Lagervej 2", lager.getAdresse());
        assertEquals(50, lager.getMaxAntalReoler());
        assertTrue(lager.getReoler().isEmpty());
    }

    @Test
    void createReol() {
        Reol reol1 = lager.createReol();
        assertEquals(1, lager.getReoler().size());
        assertEquals(reol1, lager.getReoler().getFirst());

        Reol reol2 = lager.createReol();
        assertEquals(2, lager.getReoler().size());
        assertEquals(reol2, lager.getReoler().get(1));
    }

    @Test
    void createReolThrowsExceptionNårReolerErMax() {
        lager.createReol();
        lager.createReol();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lager.createReol());
        assertEquals("Lageret har nået det maksimale antal reoler.", exception.getMessage());
    }

    @Test
    void removeReolHvisIngenHyldePlads() {
        Reol reol = lager.createReol();
        assertEquals(1, lager.getReoler().size());

        lager.removeReol(reol);
        assertTrue(lager.getReoler().isEmpty());
    }

    @Test
    void removeReolDoesNotRemoveReolIfNotEmpty() {
        Reol reol = lager.createReol();
        reol.createHyldePlads(); // Tilføjer en hylde for at have et objekt på reolen.

        lager.removeReol(reol);
        assertEquals(1, lager.getReoler().size());

    }

}