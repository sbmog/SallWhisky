package application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WhiskyTest {

    @Test
    void getHistorik() {
        MaltBatch maltBatch = new MaltBatch("Batch001", LocalDate.of(2023, 5, 1), 200, new ArrayList<>());
        Destillat destillat = new Destillat("D001", LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 30), 10, 45.0, true, 100, maltBatch);

        // Opret et fad og påfyldning
        Fad fad = new Fad(1, 200, "Eg", "Leverandør A", 1, new FadType("Sherry"), null);
        Påfyldning påfyldning = new Påfyldning("AB", 100, LocalDate.of(2023, 7, 1), fad, destillat);

        // Tildel påfyldningen til fadet
        fad.setPåfyldning(påfyldning);

        // Opret tapning
        Tapning tapning = new Tapning(LocalDate.of(2025, 5, 1), "CD", 50, fad);
        ArrayList<Tapning> tapninger = new ArrayList<>();
        tapninger.add(tapning);

        // Opret whisky
        Whisky whisky = new Whisky(101, "Highland Special", 43.0, false, 0, tapninger, WhiskyType.SINGLE_MALT);

        // Test metoden
        System.out.println(whisky.getHistorik());
    }
}