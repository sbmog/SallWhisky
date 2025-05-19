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

        int[] tilladteFadStørrelser = {150, 200, 250, 300, 400, 500};


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

        int antalDestillater = 8;
        int antalTapningerSomIkkeErOprettet = 0;


        for (int destillatIndeks = 1; destillatIndeks <= antalDestillater; destillatIndeks++) {

            int randomYear = 2018 + (int) (Math.random() * 7); // 2018–2024
            int randomMonth = 1 + (int) (Math.random() * 12);
            int randomDay = 1 + (int) (Math.random() * 28);

            LocalDate destillatStartDato = LocalDate.of(randomYear, randomMonth, randomDay);

            int mængdeDestillat = 1000 + (int) (Math.random() * 1001); // 1000-2000 L

            MaltBatch mb = maltBatches.get((destillatIndeks - 1) % maltBatches.size());
            Destillat destillat = new Destillat("DS" + destillatIndeks,
                    destillatStartDato, destillatStartDato.plusDays(5),
                    500 + destillatIndeks * 10, 60.0, false,
                    mængdeDestillat, mb);
            Storage.addDestillat(destillat);

            int resterendeLiter = mængdeDestillat;
            int fadIndeks = 0;

            ArrayList<Tapning> tapninger = new ArrayList<>();

            Reol valgtReol = alleReoler.get(næsteReolIndeks++);
            List<HyldePlads> reolensHyldePladser = valgtReol.getHyldePladser();
            int hyldeTæller = 0;

//            Fad
            while (resterendeLiter > 0) {
                if (hyldeTæller >= reolensHyldePladser.size()) {
                    næsteReolIndeks++;
                    if (næsteReolIndeks >= alleReoler.size()) {
                        throw new IllegalArgumentException("Der er ikke flere reoler med ledige hyldepladser til alle fade.");
                    }
                    valgtReol = alleReoler.get(næsteReolIndeks);
                    reolensHyldePladser = valgtReol.getHyldePladser();
                    hyldeTæller = 0;
                }

                int størrelse = tilladteFadStørrelser[(destillatIndeks + fadIndeks) % tilladteFadStørrelser.length];
                int fyldMængde = Math.min(størrelse, resterendeLiter);

                String materiale = fadMaterialer[(destillatIndeks + fadIndeks) % fadMaterialer.length];
                String leverandør = fadLeverandører[(destillatIndeks + fadIndeks) % fadLeverandører.length];

                Fad fad = new Fad(størrelse, materiale, leverandør, fadTyper[(destillatIndeks + fadIndeks) % fadTyper.length]);
                Storage.addFad(fad);

                HyldePlads placering = null;
                for (; hyldeTæller < reolensHyldePladser.size(); hyldeTæller++) {
                    HyldePlads kandidat = reolensHyldePladser.get(hyldeTæller);
                    if (kandidat.isPladsFri()) {
                        placering = kandidat;
                        hyldeTæller++;
                        break;
                    }
                }

                if (placering == null) {
                    næsteReolIndeks++;
                    if (næsteReolIndeks >= alleReoler.size()) {
                        throw new IllegalArgumentException("Der er ikke flere reoler med ledige hyldepladser til alle fade.");
                    }
                    valgtReol = alleReoler.get(næsteReolIndeks);
                    reolensHyldePladser = valgtReol.getHyldePladser();
                    hyldeTæller = 0;
                    continue;
                }

                Påfyldning påfyldning = new Påfyldning("SN" + destillatIndeks + fadIndeks,
                        fyldMængde,
                        destillat.getSlutDato(),
                        fad, destillat, placering);
                Storage.addPåfyldning(påfyldning);


                if (erFadKlarTilTapning(fad)) {
                    if (antalTapningerSomIkkeErOprettet < 3) {
                        antalTapningerSomIkkeErOprettet++;
                    } else {
                        double påfyldt = påfyldning.getAntalLiterPåfyldt();
                        double angelShareProcent = Math.random() * 7.0;
                        double tilbageLiter = Math.round(påfyldt * (1 - angelShareProcent / 100.0) * 10.0) / 10.0;
                        Tapning tapning = new Tapning(
                                påfyldning.getDatoForPåfyldning().plusYears(3),
                                "MK" + destillatIndeks + fadIndeks,
                                tilbageLiter,
                                fad
                        );
                        tapning.createFortynding(10.0);
                        Storage.addTapning(tapning);
                        tapninger.add(tapning);
                    }
                }
                resterendeLiter -= fyldMængde;
                fadIndeks++;
            }
            if (!tapninger.isEmpty()) {
                double alkoholProcent = Math.round((40.0 + Math.random() * 20.0) * 10.00) / 10.00;

                Whisky whisky = new Whisky(
                        Storage.getWhiskyer().size() + 1,
                        whiskyNavne[(destillatIndeks - 1) % whiskyNavne.length],
                        alkoholProcent,
                        10.0 * destillatIndeks,
                        tapninger,
                        whiskyTyper[(destillatIndeks - 1) % whiskyTyper.length]
                );
                Storage.addWhisky(whisky);

                int samletAntalFlasker = 0;
                for (Tapning tapning : tapninger) {
                    double flaskeStørrelseCl = 70.0;
                    int antalFlasker = tapning.beregnAntalFlasker(flaskeStørrelseCl);
                    samletAntalFlasker += antalFlasker;
                }
                for (int flaskeIndeks = 0; flaskeIndeks < samletAntalFlasker; flaskeIndeks++) {
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

            int mængdeDestillat = 1000 + (int) (Math.random() * 1001);
            int destillatID = antalDestillater + indeks;

            Destillat klarDestillat = new Destillat(
                    "DS" + destillatID,
                    startDato,
                    slutDato,
                    150 + indeks * 10,
                    63.0,
                    false,
                    mængdeDestillat,
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