package storage;

import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {
    private Destillat destillat;
    private Malt malt;
    private MaltBatch maltBatch;
    private FadType fadType;
    private Fad fad;
    private Påfyldning påfyldning;
    private Tapning tapning;
    private Whisky whisky;
    private Lager lager;
    private HyldePlads hyldePlads;
    private Reol reol;
    private Flaske flaske;


    @BeforeEach
    void setUp() {
        lager = new Lager("1", "Lager1", "Adressevej 1", 50);
        lager.createReol();
        reol = lager.getReoler().getFirst();
        reol.createHyldePlads();
        hyldePlads = reol.getHyldePladser().getFirst();
        ArrayList<Malt> maltList = new ArrayList<>();
        maltBatch = new MaltBatch("N1", LocalDate.of(2025, 5, 1), 100.0, maltList);
        maltBatch.createMalt("byg", "Mark 1", 20.0);
        malt = maltBatch.getMalt().getFirst();
        destillat = new Destillat("1", LocalDate.of(2025, 5, 4), LocalDate.of(2025, 5, 5), 100.0, 60.0, false, 50.0, maltBatch);
        fadType = new FadType("Sherry");
        fad = new Fad(50.0, "Eg", "Spanien", fadType);
        påfyldning = new Påfyldning("SNIPE", 50.0, LocalDate.of(2025, 5, 4), fad, destillat, hyldePlads);
        fad.setPåfyldning(påfyldning);
        tapning = new Tapning(LocalDate.of(2028, 5, 5), "SNIPE", 50.0, fad);
        ArrayList<Tapning> tapninger = new ArrayList<Tapning>();
        tapninger.add(tapning);
        whisky = new Whisky(1, "Test Whisky", 43.0, 10.0, tapninger, WhiskyType.SINGLE_MALT);

        whisky.createFlaske();
        flaske = whisky.getFlasker().getFirst();
    }

    @Test
    void addDestillat() {
        Storage.addDestillat(destillat);
        assertTrue(Storage.getDestillater().contains(destillat));
    }

    @Test
    void removeDestillat() {
        Storage.addDestillat(destillat);
        Storage.removeDestillat(destillat);
        assertTrue(!Storage.getDestillater().contains(destillat));
    }

    @Test
    void addFad() {
        Storage.addFad(fad);
        assertTrue(Storage.getFade().contains(fad));
    }

    @Test
    void removeFad() {
        Storage.addFad(fad);
        Storage.removeFad(fad);
        assertTrue(!Storage.getFade().contains(fad));
    }

    @Test
    void addPåfyldning() {
        Storage.addPåfyldning(påfyldning);
        assertTrue(Storage.getPåfyldninger().contains(påfyldning));
    }

    @Test
    void removePåfyldning() {
        Storage.addPåfyldning(påfyldning);
        Storage.removePåfyldning(påfyldning);
        assertTrue(!Storage.getPåfyldninger().contains(påfyldning));
    }

    @Test
    void addFadType() {
        Storage.addFadType(fadType);
        assertTrue(Storage.getFadTyper().contains(fadType));
    }

    @Test
    void removeFadType() {
        Storage.addFadType(fadType);
        Storage.removeFadType(fadType);
        assertTrue(!Storage.getFadTyper().contains(fadType));
    }

    @Test
    void addMaltBatch() {
        Storage.addMaltBatch(maltBatch);
        assertTrue(Storage.getMaltBatches().contains(maltBatch));
    }

    @Test
    void removeMaltBatch() {
        Storage.addMaltBatch(maltBatch);
        Storage.removeMaltBatch(maltBatch);
        assertTrue(!Storage.getMaltBatches().contains(maltBatch));
    }

    @Test
    void addMalt() {
        Storage.addMalt(malt);
        assertTrue(Storage.getMalter().contains(malt));
    }

    @Test
    void removeMalt() {
        Storage.addMalt(malt);
        Storage.removeMalt(malt);
        assertTrue(!Storage.getMalter().contains(malt));
    }

    @Test
    void addTapning() {
        Storage.addTapning(tapning);
        assertTrue(Storage.getTapninger().contains(tapning));
    }

    @Test
    void removeTapning() {
        Storage.addTapning(tapning);
        Storage.removeTapning(tapning);
        assertTrue(!Storage.getTapninger().contains(tapning));
    }

    @Test
    void addWhisky() {
        Storage.addWhisky(whisky);
        assertTrue(Storage.getWhiskyer().contains(whisky));
    }

    @Test
    void removeWhisky() {
        Storage.addWhisky(whisky);
        Storage.removeWhisky(whisky);
        assertTrue(!Storage.getWhiskyer().contains(whisky));
    }

    @Test
    void addLager() {
        Storage.addLager(lager);
        assertTrue(Storage.getLagre().contains(lager));
    }

    @Test
    void removeLager() {
        Storage.addLager(lager);
        Storage.removeLager(lager);
        assertTrue(!Storage.getLagre().contains(lager));
    }
}