package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Fad;
import gui.component.AttributeDisplay;
import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FadTab extends GridPane {
//    Venstre
    private final LabeledTextInput søgFad = new LabeledTextInput("Søg fad");
    private final LabeledListViewInput<Fad> fadeListview = new LabeledListViewInput<>("Fade");

//    Højre
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
    private final AttributeDisplay estimeretAntalFlasker = new AttributeDisplay("Estimeret antal flasker af indhold", "");


    public FadTab() {
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);

        fadeListview.getListView().getItems().setAll(Controller.getFade());
        fadeListview.getListView().setMinWidth(300);
        fadeListview.getListView().setPrefHeight(500);

        VBox venstreBox = new VBox(5);
        venstreBox.setPadding(new Insets(0, 5, 10, 10));
        venstreBox.getChildren().addAll(søgFad, fadeListview);
        this.add(venstreBox, 0, 0);

        VBox højreBox = new VBox(5);
        højreBox.setPadding(new Insets(0, 5, 10, 10));
        højreBox.getChildren().addAll(fadID, fadStørrelse, materiale, leverandør, fadType, antalGangeBrugt, nuværendeIndhold, fadPlacering, påfyldning, dagePåFad, dageTilTapning, estimeretAntalFlasker);
        this.add(højreBox, 1, 0);

        fadeListview.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fadID.setValue(String.valueOf(newValue.getFadID()));
                fadStørrelse.setValue(String.valueOf(newValue.getFadILiter()));
                materiale.setValue(newValue.getMateriale());
                leverandør.setValue(newValue.getLeverandør());
                fadType.setValue(String.valueOf(newValue.getFadType()));
                antalGangeBrugt.setValue(String.valueOf(newValue.getAntalGangeBrugt()));

                if (newValue.getAntalGangeBrugt() != 0) {
                    nuværendeIndhold.setValue(newValue.getNuværendeIndhold() + " Liter");
                    påfyldning.setValue(String.valueOf(newValue.getPåfyldning().getDestillat().getDestillatID()));
                    dagePåFad.setValue(String.valueOf(newValue.BeregnLagringstid()));
                    dageTilTapning.setValue(String.valueOf(newValue.beregnTidTilWhisky()));
// TODO               estimeretAntalFlasker.setValue(newValue.beregnAntalFlasker(?));
                } else {
                    String ikkeBrugt = "Fadet har ikke været i brug endnu";
                    nuværendeIndhold.setValue(ikkeBrugt);
                    påfyldning.setValue(ikkeBrugt);
                    dagePåFad.setValue(ikkeBrugt);
                    dageTilTapning.setValue(ikkeBrugt);
                }

                if (newValue.getFadPlacering() != null)
                    fadPlacering.setValue(String.valueOf(newValue.getFadPlacering()));
                else fadPlacering.setValue("Fadet har ikke en plads på lageret");


            }
        });

        søgFad.getTextField().setOnAction(event -> søgning());
    }

    //TODO tilføj søgningsmetode til controller
    private void søgning() {
    }
}
