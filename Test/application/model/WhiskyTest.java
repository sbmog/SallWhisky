package application.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WhiskyTest {

    @Test
    public void testGetHistorik() {

        ArrayList<Malt> maltListe = new ArrayList<>();
        MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2017, 12, 1), 300.0, maltListe);
        Destillat destillat = new Destillat("D123", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), 50.0, 61.0, true, 200.0, maltBatch);
        FadType fadType = new FadType("Bourbon");
        Fad fad = new Fad(1, 300.0, "Eg", "Leverandør A", 0, fadType);
        Påfyldning påfyldning = new Påfyldning("AB", 100.0, LocalDate.of(2019, 1, 1), fad, destillat);
        fad.setPåfyldning(påfyldning);

        Tapning tapning = new Tapning(LocalDate.of(2022, 1, 2), "CD", 80.0, fad);


        ArrayList<Tapning> tapninger = new ArrayList<>();
        tapninger.add(tapning);

        Whisky whisky = new Whisky(1, "TestWhisky", 45.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);


        String historik = whisky.getHistorik();


        assertTrue(historik.contains("Historik for whisky: TestWhisky"));
        assertTrue(historik.contains("tapning: 2022-01-02"));
        assertTrue(historik.contains("Fad ID: 1"));
        assertTrue(historik.contains("Destillat ID: D123"));
        assertTrue(historik.contains("Røget: true"));
    }

    @Test
    void testWhiskyAlkoholprocentUnder() {
        ArrayList<Tapning> tapninger = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            new Whisky(1, "Whisky A", 39.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);
        });
    }

    @Test
    void testWhiskyAlkoholprocent40() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(1, "Whisky A", 40.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(40.0, result);
    }

    @Test
    void testWhiskyAlkoholprocentOver() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(2, "Whisky B", 41.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        double result = whisky.getAlkoholProcent();
        assertEquals(41.0, result);
    }

    @Test
    void createFlaske() {
        ArrayList<Tapning> tapninger = new ArrayList<>();
        Whisky whisky = new Whisky(1, "Whisky A", 60, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        whisky.createFlaske();
        double whiskyIDresult = whisky.getWhiskyID();

        assertEquals(1, whisky.getFlasker().size());
        assertEquals(1, whisky.getFlasker().get(0).getFlaskeID());
        assertEquals(1,whiskyIDresult);
    }

    @Test
    void isFortyndet() {

            ArrayList<Malt> maltListe = new ArrayList<>();
            MaltBatch maltBatch = new MaltBatch("MB001", LocalDate.of(2017, 12, 1), 300.0, maltListe);
            Destillat destillat = new Destillat("D123", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 2, 1), 50.0, 61.0, true, 200.0, maltBatch);

            FadType fadType = new FadType("Bourbon");
            Fad fad = new Fad(1, 300.0, "Eg", "Leverandør A", 0, fadType);
            Påfyldning påfyldning = new Påfyldning("AB", 100.0, LocalDate.of(2019, 1, 1), fad, destillat);
            fad.setPåfyldning(påfyldning);

            Tapning tapning = new Tapning(LocalDate.of(2022, 1, 2), "CD", 80.0, fad);

            ArrayList<Tapning> tapninger = new ArrayList<>();
            tapninger.add(tapning);
            Whisky whisky = new Whisky(1, "TestWhisky", 45.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

            assertFalse(whisky.isFortyndet(), "Whisky skal ikke være markeret som fortyndet");

            tapning.createFortynding(10);
            whisky.updateFortyndetStatus();

            assertTrue(whisky.isFortyndet(), "Whisky skal markeres som fortyndet");
        }
    }
