package application.controller;

import application.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controller {

    public static Destillat createDestillat(String destillatID, LocalDate startDato, LocalDate slutDato, double literVand, double alkoholProcent, boolean røget, double mæskningsMængde, MaltBatch maltBatch) {
        Destillat newDestillat = new Destillat(destillatID, startDato, slutDato, literVand, alkoholProcent, røget, mæskningsMængde, maltBatch);
        Storage.addDestillat(newDestillat);
        return newDestillat;
    }

    public static Fad createFad(double fadStørrelse, String materiale, String leverandør, int antalGangeBrugt, FadType fadType) {
        Fad newFad = new Fad(fadStørrelse, materiale, leverandør, fadType);
        Storage.addFad(newFad);
        return newFad;
    }

    public static MaltBatch createMaltBatch(String batchNummer, LocalDate dato, double mængde, ArrayList<Malt> malt) {
        MaltBatch newMaltBatch = new MaltBatch(batchNummer, dato, mængde, malt);
        Storage.addMaltBatch(newMaltBatch);
        return newMaltBatch;
    }

    public static Påfyldning createPåfyldning(String initialerForMedarbejder, double antalLiterPåfyldt, LocalDate datoForPåfyldning, Fad fad, Destillat destillat, HyldePlads hyldePlads) {
        Påfyldning newPåfyldning = new Påfyldning(initialerForMedarbejder, antalLiterPåfyldt, datoForPåfyldning, fad, destillat, hyldePlads);
        fad.setPåfyldning(newPåfyldning);
        Storage.addPåfyldning(newPåfyldning);
        return newPåfyldning;
    }

    public static Tapning createTapning(LocalDate tapningsDato, String initialerForMedarbejder, double antalLiterFraFad, Fad fad) {
        Tapning newTapning = new Tapning(tapningsDato, initialerForMedarbejder, antalLiterFraFad, fad);
        Storage.addTapning(newTapning);
        return newTapning;
    }

    public static Whisky createWhisky(double whiskyID, String navn, double alkoholProcent, boolean fortyndet, double vandMængde, ArrayList<Tapning> tapninger, WhiskyType whiskyType) {
        Whisky newWhisky = new Whisky(whiskyID, navn, alkoholProcent, vandMængde, tapninger, whiskyType);
        Storage.addWhisky(newWhisky);
        return newWhisky;
    }

    public static Lager createLager(String lagerID, String navn, String adresse, int maxAntalReoler) {
        Lager newLager = new Lager(lagerID, navn, adresse, maxAntalReoler);
        Storage.addLager(newLager);
        return newLager;
    }

    public static FadType createFadType(String navn) {
        FadType newFadType = new FadType(navn);
        Storage.addFadType(newFadType);
        return newFadType;

    }

    public static double beregnAngelShareIProcent(double antalLiterFraFad, Fad fad, LocalDate tapningsDato) {
        if (fad.getPåfyldning() == null) {
            throw new IllegalArgumentException("Fadet har ingen påfyldning – angel share kan ikke beregnes.");
        }

        LocalDate startDato = fad.getPåfyldning().getDatoForPåfyldning();

        if (tapningsDato.isBefore(startDato.plusYears(3))) {
            throw new IllegalArgumentException("Destillatet kan ikke tappes før den har lagret i 3 år.");
        }
        double påfyldt = fad.getPåfyldning().getAntalLiterPåfyldt();
        double angelShare = (påfyldt - antalLiterFraFad) / påfyldt * 100;

        return angelShare;
    }

    public static int beregnFlaskeStørrelse(Whisky whisky) {
        double samletLiter = 0.0;
        int antalFlasker = whisky.getFlasker().size();

        if (antalFlasker == 0) return 0;

        for (Tapning tapning : whisky.getTapninger()) {
            samletLiter += tapning.getAntalLiterFraFad() + tapning.getTotalFortydnigMængde();
        }
        double flaskeStørrelse = samletLiter / antalFlasker * 100;
        int afrundet = (int) Math.round(flaskeStørrelse);
        return afrundet;
    }

    public static void omhældDestillat(
            double antalLiter,
            Fad fraFad,
            Fad tilFad,
            Destillat destillat,
            HyldePlads tilHyldePlads
    ) {
        if (fraFad == null || tilFad == null)
            throw new IllegalArgumentException("Begge fade skal være angivet.");

        if (fraFad == tilFad)
            throw new IllegalArgumentException("Kan ikke omhælde til samme fad.");

        if (antalLiter <= 0)
            throw new IllegalArgumentException("Mængde skal være større end 0.");

        if (antalLiter > fraFad.getNuværendeIndhold())
            throw new IllegalArgumentException("Ikke nok væske i kildefad.");

        if (tilFad.getNuværendeIndhold() + antalLiter > tilFad.getFadILiter())
            throw new IllegalArgumentException("Modtagerfadet bliver overfyldt.");

        if (destillat == null)
            throw new IllegalArgumentException("Destillat skal angives.");

        // Opdater mængden i kildefad
        fraFad.opdaterNuværendeInhold(antalLiter);

        // Opdater mængden i modtagerfadet
        tilFad.opdaterNuværendeInhold(-antalLiter);

        tilFad.tilføjDestillat(destillat);

        // Find hyldeplads til modtagerfad
        HyldePlads anvendtHyldePlads = tilFad.getFadPlacering() != null
                ? tilFad.getFadPlacering().getHyldePlads()
                : tilHyldePlads;

        if (anvendtHyldePlads == null) {
            throw new IllegalArgumentException("Hyldeplads skal angives for tomt fad.");
        }

        if (fraFad.getNuværendeIndhold() == 0) {
            fraFad.fjernFraHyldeHvisTom();
        }
    }


    public static ArrayList<Fad> getFade() {
        return Storage.getFade();
    }

    public static ArrayList<Whisky> getWhiskyer() {
        return Storage.getWhiskyer();
    }

    public static ArrayList<Destillat> getDestillater() {
        return Storage.getDestillater();
    }

    public static ArrayList<Lager> getLagre() {
        return Storage.getLagre();
    }

    public static void removeLager(Lager lager) {
        Storage.removeLager(lager);
    }

    public static ArrayList<MaltBatch> getAlleMalte() {
        return Storage.getMaltBatches();
    }

    public static List<HyldePlads> getAlleFrieHyldePladser() {
        List<HyldePlads> friePladser = new ArrayList<>();
        for (Lager lager : Storage.getLagre()) {
            for (Reol reol : lager.getReoler()) {
                for (HyldePlads hyldePlads : reol.getHyldePladser()) {
                    if (hyldePlads.isPladsFri())
                        friePladser.add(hyldePlads);
                }
            }
        }
        return friePladser;
    }


    public static void flytFadTilNyHylde(Fad fad, HyldePlads nyHyldePlads) {
        if (!nyHyldePlads.isPladsFri()) {
            throw new IllegalArgumentException("Den nye hyldePlads er allerede optaget.");
        }

        FadPlacering nuværendePlacering = fad.getFadPlacering();
        if (nuværendePlacering != null) {
            nuværendePlacering.getHyldePlads().setPladsFri();
        }

        fad.placerPåHylde(nyHyldePlads, LocalDate.now());
    }

    public static ArrayList<FadType> getFadTyper() {
        return Storage.getFadTyper();
    }

    public static int getNæsteFadID() {
        return Storage.getFade().size() + 1;
    }

    public static ArrayList<MaltBatch> getMaltBatch() {
        return Storage.getMaltBatches();
    }

    public static ArrayList<Fad> getLedigeFade() {
        ArrayList<Fad> ledigeFade = new ArrayList<>();
        for (Fad fad : getFade()) {
            if (fad.getFadPlacering() == null) {
                ledigeFade.add(fad);
            }
        }
        return ledigeFade;
    }

    public static List<Fad> søgFade(String søgeTekst) {
        return getFade().stream()
                .filter(fad ->
                        String.valueOf(fad.getFadID()).contains(søgeTekst) ||
                                fad.getMateriale().toLowerCase().contains(søgeTekst) ||
                                fad.getLeverandør().toLowerCase().contains(søgeTekst) ||
                                (fad.getFadType() != null && fad.getFadType().toString().toLowerCase().contains(søgeTekst))
                )
                .toList();
    }

    public static List<Destillat> søgDestillat(String søgeTekst) {
        return getDestillater().stream()
                .filter(destillat ->
                        destillat.getDestillatID().toLowerCase().contains(søgeTekst) ||
                                String.valueOf(destillat.getMaltBatch()).toLowerCase().contains(søgeTekst)
                )
                .toList();
    }

    public static List<MaltBatch> søgMaltBatch(String søgeTekst) {
        return getMaltBatch().stream()
                .filter(batch ->
                        batch.getBatchNummer().toLowerCase().contains(søgeTekst) ||
                                batch.getMalt().stream().anyMatch(malt ->
                                        malt.getKornSort().toLowerCase().contains(søgeTekst) ||
                                                malt.getMarkNavn().toLowerCase().contains(søgeTekst)
                                )
                )
                .toList();
    }

    public static List<Whisky> søgWhisky(String søgeTekst) {
        return getWhiskyer().stream()
                .filter(whisky ->
                        String.valueOf(whisky.getWhiskyID()).contains(søgeTekst) ||
                                whisky.getNavn().toLowerCase().contains(søgeTekst) ||
                                whisky.getWhiskyType().toString().toLowerCase().contains(søgeTekst))
                .toList();
    }

    public static ArrayList<Destillat> getDestillaterMedResterendeIndhold() {
        ArrayList<Destillat> resultat = new ArrayList<>();

        for (Destillat destillat : getDestillater()) {
            double totalPåfyldt = 0.0;

            for (Påfyldning påfyldning : getPåfyldninger()) {
                if (påfyldning.getDestillat().equals(destillat)) {
                    totalPåfyldt += påfyldning.getAntalLiterPåfyldt();
                }
            }

            if (totalPåfyldt < destillat.getVæskemængde()) {
                resultat.add(destillat);
            }
        }
        return resultat;
    }

    public static ArrayList<Påfyldning> getPåfyldninger() {
        return Storage.getPåfyldninger();
    }

    public static ArrayList<Fad> getFadeMedPåfyldning(Destillat destillat) {
        ArrayList<Fad> fade = new ArrayList<>();
        for (Påfyldning påfyldning : getPåfyldninger()) {
            if (påfyldning.getDestillat().equals(destillat)) {
                fade.add(påfyldning.getFad());
            }
        }
        return fade;
    }

    public static ArrayList<Fad> getFadeMedPåfyldning() {
        ArrayList<Fad> fadeMedPåfyldning = new ArrayList<>();
        for (Fad fad : getFade()) {
            if (fad.getPåfyldning() != null && fad.getNuværendeIndhold() > 0) {
                fadeMedPåfyldning.add(fad);
            }
        }
        return fadeMedPåfyldning;
    }


    public static double getAntalLiterTilbagePåDestillat(Destillat destillat) {
        double totalPåfyldt = 0.0;
        for (Påfyldning påfyldning : getPåfyldninger()) {
            if (påfyldning.getDestillat().equals(destillat)) {
                totalPåfyldt += påfyldning.getAntalLiterPåfyldt();
            }
        }
        return destillat.getVæskemængde() - totalPåfyldt;
    }

    public static int beregnEstimeretAntalFlasker(Fad fad, double flaskeStørrelseCL) {
        if (flaskeStørrelseCL <= 0) {
            throw new IllegalArgumentException("Flaskestørrelse skal være større end 0.");
        }
        double totalVæskeILiter = fad.getPåfyldning().getAntalLiterPåfyldt();

        return (int) ((totalVæskeILiter * 100) / flaskeStørrelseCL);
    }

    public static List<Fad> getFadeDerErKlarTilAtBliveTappet() {
        ArrayList<Fad> fadeKlar = new ArrayList<>();
        for (Fad fad : getFade()) {
            if (erFadKlarTilTapning(fad)) {
                fadeKlar.add(fad);
            }
        }
        return fadeKlar;
    }

    private static boolean erFadKlarTilTapning(Fad fad) {
        Påfyldning påfyldning = fad.getPåfyldning();
        return påfyldning != null &&
                påfyldning.getDatoForPåfyldning().plusYears(3).isBefore(LocalDate.now());
    }

    public static ArrayList<Fad> getFadeMedPlads() {
        ArrayList<Fad> fadeMedPlads = new ArrayList<>();
        for (Fad fad : getFade()) {
            if (fad.getFadILiter() - fad.getNuværendeIndhold() != 0) {
                fadeMedPlads.add(fad);
            }
        }
        return fadeMedPlads;
    }
}
