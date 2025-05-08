package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FadTypeTest {

    @Test
    void constructorThrowsExceptionWhenNavnIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new FadType(null));
        assertEquals("Navn kan ikke være null eller tom.", exception.getMessage());
    }

    @Test
    void constructorThrowsExceptionWhenNavnErTom() {
        Exception exception = assertThrows(IllegalArgumentException.class,() -> new FadType(""));
        assertEquals("Navn kan ikke være null eller tom.", exception.getMessage());
    }

}