package gui.testDataGui;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestData {

    public static void initTestData() {
        // Lager
        Lager hovedlager = new Lager("Hovedlager", "Central lager", "Lagervej 1", 100);
        Lager baghavelager = new Lager("Baghavelager", "Ekstra lager", "Baghavevej 2", 50);
        Storage.addLager(hovedlager);
        Storage.addLager(baghavelager);

        // Reoler og hyldepladser
        ArrayList<Reol> alleReoler = new ArrayList<>();
        for (int reolIndeks = 0; reolIndeks < 20; reolIndeks++) {
            alleReoler.add(hovedlager.createReol());
        }
        for (int reolIndeks = 0; reolIndeks < 6; reolIndeks++) {
            alleReoler.add(baghavelager.createReol());
        }
        ArrayList<Reol> ledigeReoler = new ArrayList<>(alleReoler);
        Collections.shuffle(ledigeReoler);

        for (Reol reol : alleReoler) {
            for (int hyldeIndeks = 0; hyldeIndeks < 9; hyldeIndeks++) {
                reol.createHyldePlads();
            }
        }
        int næsteReolIndeks = 0;

        // Fadtyper
        FadType[] fadTyper = {
                new FadType("Ny"),
                new FadType("Ex-Bourbonfad"),
                new FadType("Ex-Oloroso Sherryfad"),
                new FadType("Ex-PX Sherryfad"),
                new FadType("Ex-Laphroaig Bourbonfad"),
                new FadType("Ex-Manzanilla Sherryfad")
        };
        for (FadType ft : fadTyper) {
            Storage.addFadType(ft);
        }

        int[] tilladteFadStørrelser = {100, 125, 150, 200, 250};


        // Maltbatches og malt
        ArrayList<MaltBatch> maltBatches = new ArrayList<>();
        for (int indeks = 1; indeks <= 6; indeks++) {
            MaltBatch mb = new MaltBatch("MB00" + indeks, LocalDate.of(2020, indeks, 1), 200 + indeks * 50, new ArrayList<>());
            mb.createMalt("Byg", "Mark " + indeks, 20.0 * indeks);
            Storage.addMaltBatch(mb);
            maltBatches.add(mb);
        }

        // Liste over whisky-navne
        String[] whiskyNavne = {
                "MULD 1.1 – Single Malt Whisky",
                "TØRV 2.1 – Peated Single Malt Whisky",
                "GLØD 3.1 – Single Malt Whisky",
        };

        WhiskyType[] whiskyTyper = {
                WhiskyType.SINGLE_MALT,
                WhiskyType.PEATED_SINGLE_MALT,
                WhiskyType.SINGLE_CASK,
                WhiskyType.GRAIN,
                WhiskyType.BLENDED,
                WhiskyType.SINGLE_MALT
        };

        String[] fadMaterialer = {
                "Amerikansk Eg",
                "Fransk Eg",
                "Europæisk Eg",
                "Japansk Eg",
                "Kastanjetræ",
                "Akacie"
        };

        String[] fadLeverandører = {
                "FAD APS",
                "Tøndeimport ApS",
                "Cooper & Co",
                "Nordic Barrels",
                "Oak Brothers",
                "ScandiFad"
        };

        int antalDestillater = 10;
        int antalTapningerSomIkkeErOprettet = 0;

        // Destillater og tilknyttede fade, påfyldninger, tapninger og flasker
        for (int destillatIndeks = 1; destillatIndeks <= antalDestillater; destillatIndeks++) {

            int randomYear = 2018 + (int) (Math.random() * 7); // 2018–2024
            int randomMonth = 1 + (int) (Math.random() * 12);
            int randomDay = 1 + (int) (Math.random() * 28);

            LocalDate destillatStartDato = LocalDate.of(randomYear, randomMonth, randomDay);

            // Brug næste ledige reol
            Reol valgtReol = alleReoler.get(næsteReolIndeks++);
            List<HyldePlads> reolensHyldePladser = valgtReol.getHyldePladser();
            int hyldeTæller = 0;

            MaltBatch mb = maltBatches.get((destillatIndeks - 1) % maltBatches.size());
            Destillat destillat = new Destillat("DS" + destillatIndeks,
                    destillatStartDato, destillatStartDato.plusDays(5),
                    500 + destillatIndeks * 10, 60.0, false,
                    50 + destillatIndeks * 5, mb);
            Storage.addDestillat(destillat);

            int antalFade = 3 + (destillatIndeks % 4); // 3-6 fade

            ArrayList<Tapning> tapninger = new ArrayList<>();

//            Fad
            for (int fadIndeks = 1; fadIndeks <= antalFade; fadIndeks++) {
                int størrelse = tilladteFadStørrelser[(destillatIndeks + fadIndeks) % tilladteFadStørrelser.length];
                String materiale = fadMaterialer[(destillatIndeks + fadIndeks) % fadMaterialer.length];
                String leverandør = fadLeverandører[(destillatIndeks + fadIndeks) % fadLeverandører.length];

                Fad fad = new Fad(størrelse, materiale, leverandør, fadTyper[(destillatIndeks + fadIndeks) % fadTyper.length]);
                Storage.addFad(fad);

                // fadplacering
                if (hyldeTæller >= reolensHyldePladser.size()) {
                    throw new IllegalStateException("Reolen har ikke nok hyldepladser til alle fade.");
                }
                HyldePlads placering = reolensHyldePladser.get(hyldeTæller++);

                // påfyldning
                Påfyldning påfyldning = new Påfyldning("SN" + destillatIndeks + fadIndeks,
                        størrelse - 50,
                        destillat.getSlutDato(),
                        fad, destillat, placering);
                fad.setPåfyldning(påfyldning);
                Storage.addPåfyldning(påfyldning);

                // tapning
                if (erFadKlarTilTapning(fad)) {
                    if (antalTapningerSomIkkeErOprettet < 3) {
                        antalTapningerSomIkkeErOprettet++;
                    } else {
                        Tapning tapning = new Tapning(
                                påfyldning.getDatoForPåfyldning().plusYears(3),
                                "MK" + destillatIndeks + fadIndeks,
                                påfyldning.getAntalLiterPåfyldt(),
                                fad
                        );
                        tapning.createFortynding(10.0);
                        Storage.addTapning(tapning);
                        tapninger.add(tapning);
//
//                    fad.opdaterTilTomNårTappet(påfyldning.getAntalLiterPåfyldt());
//                    fad.fjernFraHyldeNårTappet();
                    }
                }
            }
            if (!tapninger.isEmpty()) {
                double alkoholProcent = Math.round((40.0 + Math.random() * 20.0) * 10.00) / 10.00;

                Whisky whisky = new Whisky(
                        destillatIndeks,
                        whiskyNavne[(destillatIndeks - 1) % whiskyNavne.length],
                        alkoholProcent,
                        10.0 * destillatIndeks,
                        tapninger,
                        whiskyTyper[(destillatIndeks - 1) % whiskyTyper.length]
                );
                Storage.addWhisky(whisky);

                // 10 flasker per whisky
                for (int flaskeIndeks = 0; flaskeIndeks < 10; flaskeIndeks++) {
                    whisky.createFlaske();
                }
            }

        }
//        10 tomme fade
        for (int i = 1; i <= 10; i++) {
            int størrelse = tilladteFadStørrelser[i % tilladteFadStørrelser.length];

            String materiale = fadMaterialer[i % fadMaterialer.length];
            String leverandør = fadLeverandører[i % fadLeverandører.length];

            Fad tomtFad = new Fad(størrelse, materiale, leverandør, fadTyper[i % fadTyper.length]);
            Storage.addFad(tomtFad);
        }

//         To destillater klar til påfyldning
        for (int indeks = 1; indeks <= 2; indeks++) {
            MaltBatch mb = maltBatches.get(indeks % maltBatches.size());
            LocalDate startDato = LocalDate.now().minusDays(5 + indeks);
            LocalDate slutDato = LocalDate.now().minusDays(indeks);
            int destillatID = antalDestillater + indeks;

            Destillat klarDestillat = new Destillat(
                    "DS" + destillatID,
                    startDato,
                    slutDato,
                    150 + indeks * 10,
                    63.0,
                    false,
                    70 + indeks * 5,
                    mb
            );
            Storage.addDestillat(klarDestillat);
        }
    }

    private static boolean erFadKlarTilTapning(Fad fad) {
        Påfyldning påfyldning = fad.getPåfyldning();
        return påfyldning != null &&
                påfyldning.getDatoForPåfyldning().plusYears(3).isBefore(LocalDate.now());
    }
}