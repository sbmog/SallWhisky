package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WhiskyTest {

    private ArrayList<Malt> maltListe;
    private MaltBatch maltBatch;
    private Destillat destillat;
    private FadType fadType;
    private Fad fad;
    private Påfyldning påfyldning;
    private Tapning tapning;
    private ArrayList<Tapning> tapninger;
    private Whisky whisky;
    private Lager lager;
    private Reol reol;
    private HyldePlads hyldePlads;

    @BeforeEach
    void resetFadIdCounter() {
        Fad.resetIDCounter();
    }

    @BeforeEach
    void setUp() {
        lager = new Lager("Lager1", "Adressevej 1", "By", 100);
        reol = new Reol(lager, 1);
        reol.createHyldePlads();
        hyldePlads = reol.getHyldePladser().getFirst();
        maltListe = new ArrayList<>();
        maltBatch = new MaltBatch("MB001", LocalDate.of(2017, 12, 1), 300.0, maltListe);
        destillat = new Destillat("D123", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), 50.0, 61.0, true, 200.0, maltBatch);
        fadType = new FadType("Bourbon");
        fad = new Fad(300.0, "Eg", "Leverandør A", fadType);
        påfyldning = new Påfyldning("AB", 100.0, LocalDate.of(2019, 1, 1), fad, destillat, hyldePlads);
        fad.setPåfyldning(påfyldning);
        tapning = new Tapning(LocalDate.of(2022, 1, 2), "CD", 80.0, fad);
        tapninger = new ArrayList<>();
        tapninger.add(tapning);
        whisky = new Whisky(1, "TestWhisky", 45.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);
    }


    @Test
    public void constructorInitialisereKorrekt() {
        assertEquals(1, whisky.getWhiskyID());
        assertEquals("TestWhisky", whisky.getNavn());
        assertEquals(45.0, whisky.getAlkoholProcent());
        assertEquals(10.0, whisky.getVandMængde());
        assertEquals(tapninger, whisky.getTapninger());
        assertEquals(WhiskyType.SINGLE_MALT, whisky.getWhiskyType());
        assertNotNull(whisky.getFlasker());
        assertTrue(whisky.getFlasker().isEmpty());
    }

    @Test
    void constructorThrowsExceptionInvalidInput() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(0, "TestWhisky", 45.0, 10.0, tapninger, WhiskyType.SINGLE_MALT));
        assertEquals("WhiskyID skal være et tal over 0.", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, null, 45.0, 10.0, tapninger, WhiskyType.SINGLE_MALT));
        assertEquals("Navn kan ikke være null eller tom.", exception2.getMessage());

        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, "TestWhisky", 39.0, 10.0, tapninger, WhiskyType.SINGLE_MALT));
        assertEquals("Alkoholprocent skal være over 40 procent", exception3.getMessage());

        Exception exception4 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, "TestWhisky", 45.0, -1.0, tapninger, WhiskyType.SINGLE_MALT));
        assertEquals("Vandmængde skal være et tal over 0.", exception4.getMessage());

        Exception exception5 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, "TestWhisky", 45.0, 10.0, null, WhiskyType.SINGLE_MALT));
        assertEquals("Tapninger kan ikke være null eller tom.", exception5.getMessage());

        Exception exception6 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, "TestWhisky", 45.0, 10.0, new ArrayList<>(), WhiskyType.SINGLE_MALT));
        assertEquals("Tapninger kan ikke være null eller tom.", exception6.getMessage());

        Exception exception7 = assertThrows(IllegalArgumentException.class, () ->
                new Whisky(1, "TestWhisky", 45.0, 10.0, tapninger, null));
        assertEquals("WhiskyType kan ikke være null.", exception7.getMessage());
    }

    @Test
    public void testGetHistorik() {
        String historik = whisky.getHistorik();

        assertTrue(historik.contains("Historik for whisky: TestWhisky"));
        assertTrue(historik.contains("Whisky type: Single Malt"));
        assertTrue(historik.contains("Alkoholprocent: 45.0"));
        assertTrue(historik.contains("Antal flasker: 0"));
        assertTrue(historik.contains("Destillat ID: D123"));
        assertTrue(historik.contains("Destillering startdato: 2018-01-01"));
        assertTrue(historik.contains("Destillering slutdato: 2018-02-01"));
        assertTrue(historik.contains("Påfyldning: 2019-01-01"));
        assertTrue(historik.contains("Tapning: 2022-01-02"));
        assertTrue(historik.contains("MaltBatch: MB001"));
        assertTrue(historik.contains("Røget: Ja"));
        assertTrue(historik.contains("Fortyndet: false"));
        assertTrue(historik.contains("Vandmængde: 50.0"));
        assertTrue(historik.contains("Væskemængde: 200.0"));
    }

    @Test
    void testWhiskyAlkoholprocentUnder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Whisky(1, "Whisky A", 39.0, 10.0, new ArrayList<>(), WhiskyType.SINGLE_MALT);
        });
    }

    @Test
    void testWhiskyAlkoholprocent40() {
        Whisky whisky = new Whisky(1, "Whisky A", 40.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(40.0, result);
    }

    @Test
    void testWhiskyAlkoholprocentOver() {
        Whisky whisky = new Whisky(2, "Whisky B", 41.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(41.0, result);
    }

    @Test
    void createFlaske() {
        whisky.createFlaske();
        double whiskyIDresult = whisky.getWhiskyID();

        assertEquals(1, whisky.getFlasker().size());
        assertEquals(1, whisky.getFlasker().get(0).getFlaskeID());
        assertEquals(1, whiskyIDresult);
    }

    @Test
    void isFortyndet() {
        assertFalse(whisky.isFortyndet(), "Whisky skal ikke være markeret som fortyndet");

        tapning.createFortynding(10);
        whisky.updateFortyndetStatus();

        assertTrue(whisky.isFortyndet(), "Whisky skal markeres som fortyndet");
    }
}