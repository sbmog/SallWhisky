package application.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FortyndingTest {

    @Test
    void testFortyndingConstructor() {
        Fortynding fortynding = new Fortynding(5.0);
        assertEquals(5.0, fortynding.getVandmængde());
    }

    @Test
    void constructorThrowsExceptionVandMængdeOverNul() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Fortynding(0));
        assertEquals("Vandmængde skal være et tal over 0.", exception.getMessage());
    }
}
