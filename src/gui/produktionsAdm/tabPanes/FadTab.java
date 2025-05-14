package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import application.model.Tapning;
import gui.component.AttributeDisplay;
import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.text.TableView;

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
                    nuværendeIndhold.setValue(newValue.getNuværendeIndhold() + " Liter ");
                    if(newValue.getPåfyldning() != null && newValue.getPåfyldning().getDestillat() != null) {
                        påfyldning.setValue(String.valueOf(newValue.getPåfyldning().getDestillat().getDestillatID()));
                    } else {
                        påfyldning.setValue("Ukendt destillat");
                    }
                    Tapning tapning = newValue.getTapning();
                    double flaskeStørrelseCL = 70.0;
                    if(tapning != null) {
                        int antalFlasker = tapning.beregnAntalFlasker(flaskeStørrelseCL);
                        estimeretAntalFlasker.setValue(String.valueOf(antalFlasker));
                    } else {
                        estimeretAntalFlasker.setValue("Ingen Tapning");
                    }

                    dagePåFad.setValue(String.valueOf(newValue.BeregnLagringstid()));
                    dageTilTapning.setValue(String.valueOf(newValue.beregnTidTilWhisky()));

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
