package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PåfyldningTest {

    private Fad fad;
    private Destillat destillat;
    private Malt malt;
    private ArrayList<Malt> maltList;
    private MaltBatch maltBatch;
    private Lager lager;
    private Reol reol;
    private HyldePlads hyldePlads;


    @BeforeEach
    void setUp() {
        lager = new Lager("Lager1", "Baghaven", "Baghavevej 1", 10);
        reol = new Reol(lager, 1);
        hyldePlads = new HyldePlads(1, reol);

        malt = new Malt("byg", "Mark 1", 20.0);
        maltList = new ArrayList<>();
        maltList.add(malt);
        maltBatch = new MaltBatch("1", LocalDate.of(2025, 5, 1), 50.0, maltList);
        fad = new Fad(100.0, "Eg", "Spanien", new FadType("Sherry"));
        destillat = new Destillat("D1", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), 50.0, 60.0, false, 40.0, maltBatch);
    }

    @Test
    void antalLiterPåfyldtUnderEllerLigNulThrowsException() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Påfyldning("ABC", 0.0, LocalDate.now(), fad, destillat, hyldePlads);
        });
        assertEquals("Antal liter påfyldt skal være større end 0.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Påfyldning("ABC", -10.0, LocalDate.now(), fad, destillat, hyldePlads);
        });
        assertEquals("Antal liter påfyldt skal være større end 0.", exception2.getMessage());
    }

    @Test
    void initialerForMedarbejderNullOrEmptyThrowsException() {
        Exception exception1 = assertThrows(NullPointerException.class, () -> {
            new Påfyldning(null, 10.0, LocalDate.now(), fad, destillat, hyldePlads);
        });
        assertEquals("Initialer for medarbejder kan ikke være null eller tom.", exception1.getMessage());

        Exception exception2 = assertThrows(NullPointerException.class, () -> {
            new Påfyldning("", 10.0, LocalDate.now(), fad, destillat, hyldePlads);
        });
        assertEquals("Initialer for medarbejder kan ikke være null eller tom.", exception2.getMessage());
    }

    @Test
    void datoForPåfyldningNullThrowsException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Påfyldning("ABC", 10.0, null, fad, destillat, hyldePlads);
        });
        assertEquals("Dato for påfyldning kan ikke være null.", exception.getMessage());
    }


    @Test
    void destillatNullThrowsException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Påfyldning("ABC", 10.0, LocalDate.now(), fad, null, hyldePlads);
        });
        assertEquals("Destillat kan ikke være null.", exception.getMessage());
    }
}