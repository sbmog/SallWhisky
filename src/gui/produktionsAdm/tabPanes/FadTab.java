package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Fad;
import application.model.Tapning;
import gui.component.AttributeDisplay;

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


    public FadTab() {
        super("Søg fad", "Fade");

        liste.getListView().getItems().setAll(Controller.getFade());

        attributVisning.getChildren().addAll(fadID, fadStørrelse, materiale, leverandør, fadType, antalGangeBrugt, nuværendeIndhold, fadPlacering, påfyldning, dagePåFad, dageTilTapning, estimeretAntalFlasker);


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
                    if (tapning == null) {
                        if (newValue.getPåfyldning() != null && newValue.getPåfyldning().getDestillat() != null) {
                            påfyldning.setValue(String.valueOf(newValue.getPåfyldning().getDestillat().getDestillatID()));
                        } else {
                            påfyldning.setValue("Ukendt destillat");
                        }
                        nuværendeIndhold.setValue(newValue.getNuværendeIndhold() + " Liter ");
                        dagePåFad.setValue(String.valueOf(newValue.BeregnLagringstid()));
                        int dageTil = newValue.beregnTidTilWhisky();
                        if (dageTil <= 0) {
                            dageTilTapning.setValue("Klar til tapning");
                        } else {
                            dageTilTapning.setValue(String.valueOf(newValue.beregnTidTilWhisky()));
                        }
                        estimeretAntalFlasker.getHeaderLabel().setText("Estimeret antal flasker (70 cl)");
                        estimeretAntalFlasker.setValue(String.valueOf(Controller.beregnEstimeretAntalFlasker(newValue, flaskeStørrelseCL)));
                    } else {
                        String erTappet = "Fadet er tappet";
                        nuværendeIndhold.setValue(erTappet);
                        påfyldning.setValue(erTappet);
                        dagePåFad.setValue(erTappet);
                        dageTilTapning.setValue(erTappet);
                        estimeretAntalFlasker.getHeaderLabel().setText("Aktuel antal Flasker (70 cl)");
                        estimeretAntalFlasker.setValue(String.valueOf(tapning.beregnAntalFlasker(flaskeStørrelseCL)));
                    }
                } else {
                    String ikkeBrugt = "Fadet har ikke været i brug endnu";
                    nuværendeIndhold.setValue(ikkeBrugt);
                    påfyldning.setValue(ikkeBrugt);
                    dagePåFad.setValue(ikkeBrugt);
                    dageTilTapning.setValue(ikkeBrugt);
                    estimeretAntalFlasker.setValue(ikkeBrugt);
                }

                if (newValue.getFadPlacering() != null)
                    fadPlacering.setValue(String.valueOf(newValue.getFadPlacering().getFullFadPlacering()));
                else fadPlacering.setValue("Fadet har ikke en plads på lageret");


                søgeFelt.getTextField().setOnAction(event -> søgning());
            }
        });
    }

    private void søgning() {
        String søgeTekst = søgeFelt.getInputValue().toLowerCase().trim();
        liste.getListView().getItems().setAll(Controller.søgFade(søgeTekst));
    }
}
