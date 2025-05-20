package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Fad;
import application.model.Tapning;
import application.model.Whisky;
import gui.component.AttributeDisplay;
import storage.Storage;

import java.util.Objects;

public class FadTab extends BaseTab<Fad> {
    //    AttributeDisplays
    private final AttributeDisplay fadID = new AttributeDisplay("Fad ID", "");
    private final AttributeDisplay fadStørrelse = new AttributeDisplay("Fad størrelse", "");
    private final AttributeDisplay materiale = new AttributeDisplay("Materiale", "");
    private final AttributeDisplay leverandør = new AttributeDisplay("Leverandør", "");
    private final AttributeDisplay antalGangeBrugt = new AttributeDisplay("Antal gange brugt", "");
    private final AttributeDisplay nuværendeIndhold = new AttributeDisplay("Nuværende indhold", "");
    private final AttributeDisplay fadPlacering = new AttributeDisplay("Fad placering", "");
    private final AttributeDisplay fadType = new AttributeDisplay("Fad type", "");
    private final AttributeDisplay påfyldning = new AttributeDisplay("Påfyldt med destillat", "");
    private final AttributeDisplay dagePåFad = new AttributeDisplay("Antal dage på fad", "");
    private final AttributeDisplay dageTilTapning = new AttributeDisplay("Antal dage til tapning", "");
    private final AttributeDisplay estimeretAntalFlasker = new AttributeDisplay("Estimeret antal flasker (70 cl)", "");
    private final AttributeDisplay destillater = new AttributeDisplay("Destillater", "");

    public FadTab() {
        super("Søg fad", "Fade");

        liste.getListView().getItems().setAll(Controller.getFade());

        attributVisning.getChildren().addAll(fadID, fadStørrelse, materiale, leverandør, fadType, antalGangeBrugt, nuværendeIndhold, fadPlacering, destillater, dagePåFad, dageTilTapning, estimeretAntalFlasker);


        liste.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fadID.setValue(String.valueOf(newValue.getFadID()));
                fadStørrelse.setValue(String.valueOf(newValue.getFadILiter()));
                materiale.setValue(newValue.getMateriale());
                leverandør.setValue(newValue.getLeverandør());
                fadType.setValue(String.valueOf(newValue.getFadType()));
                antalGangeBrugt.setValue(String.valueOf(newValue.getAntalGangeBrugt()));

                if (newValue.getAntalGangeBrugt() != 0) {
                    Tapning tapning = newValue.getTapning();
                    double flaskeStørrelseCL = 70.0;

                    boolean erTappet = (tapning != null) || (newValue.getNuværendeIndhold() <= 0);

                    if (erTappet) {
                        String tappetTekst = "Fadet er tappet";
                        nuværendeIndhold.setValue(tappetTekst);
                        påfyldning.setValue(tappetTekst);
                        destillater.setValue(tappetTekst);
                        dagePåFad.setValue(tappetTekst);
                        dageTilTapning.setValue(tappetTekst);

                        Whisky whisky = getWhiskyForTapning(tapning);
                        if (whisky!=null) {
                            estimeretAntalFlasker.getHeaderLabel().setText("Aktuel antal Flasker (" + Controller.beregnFlaskeStørrelse(whisky) + ")");
                            estimeretAntalFlasker.setValue(String.valueOf(tapning.beregnAntalFlasker(flaskeStørrelseCL)));
                        }else {
                            estimeretAntalFlasker.setValue("Indhold omhældt til andre fade");
                        }
                    } else {
                        // Ikke tappet, viser normale informationer
                        if (newValue.getPåfyldning() != null && newValue.getPåfyldning().getDestillat() != null) {
                            påfyldning.setValue("ID " + newValue.getPåfyldning().getDestillat().getDestillatID());
                        } else {
                            påfyldning.setValue("Ukendt destillat");
                        }

                        // Vis alle destillat-ID'er
                        String destillatTekst = "";
                        for (int i = 0; i < newValue.getDestillater().size(); i++) {
                            if (i > 0) destillatTekst += ", ";
                            destillatTekst += "ID " + newValue.getDestillater().get(i).getDestillatID();
                        }
                        destillater.setValue(destillatTekst.isEmpty() ? "Ingen destillater" : destillatTekst);

                        nuværendeIndhold.setValue(newValue.getNuværendeIndhold() + " Liter");
                        dagePåFad.setValue(String.valueOf(newValue.beregnLagringstid()));
                        int dageTil = newValue.beregnTidTilWhisky();
                        if (dageTil <= 0) {
                            dageTilTapning.setValue("Klar til tapning");
                        } else {
                            dageTilTapning.setValue(String.valueOf(dageTil));
                        }

                        estimeretAntalFlasker.getHeaderLabel().setText("Estimeret antal flasker (70 cl)");

                        estimeretAntalFlasker.setValue(String.valueOf(Controller.beregnEstimeretAntalFlasker(newValue, flaskeStørrelseCL)));
                    }
                } else {
                    // Fadet er aldrig brugt
                    String ikkeBrugt = "Fadet har ikke været i brug endnu";
                    nuværendeIndhold.setValue(ikkeBrugt);
                    påfyldning.setValue(ikkeBrugt);
                    destillater.setValue(ikkeBrugt);
                    dagePåFad.setValue(ikkeBrugt);
                    dageTilTapning.setValue(ikkeBrugt);
                    estimeretAntalFlasker.setValue(ikkeBrugt);
                }

                if (newValue.getFadPlacering() != null)
                    fadPlacering.setValue(newValue.getFadPlacering().getFullFadPlacering());
                else
                    fadPlacering.setValue("Fadet har ikke en plads på lageret");

                søgeFelt.getTextField().setOnAction(event -> søgning());
            }
        });
    }

    private Whisky getWhiskyForTapning(Tapning tapning) {
        for (Whisky whisky : Storage.getWhiskyer()) {
            for (Tapning tapningFraWhsiky : whisky.getTapninger()) {
                if (Objects.equals(tapning, tapningFraWhsiky))
                    return whisky;
            }
        }
        return null;
    }

    private void søgning() {
        String søgeTekst = søgeFelt.getInputValue().toLowerCase().trim();
        liste.getListView().getItems().setAll(Controller.søgFade(søgeTekst));
    }
}