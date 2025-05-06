package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Destillat;
import gui.component.AttributeDisplay;
import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DestillatTab extends GridPane {
    //    Venstre
    private final LabeledTextInput søgDestillat = new LabeledTextInput("Søg destillat");
    private final LabeledListViewInput<Destillat> destillatListView = new LabeledListViewInput<>("Destillater");

    //    Højre
    private final AttributeDisplay destillatID = new AttributeDisplay("Destillat ID", "");
    private final AttributeDisplay startDato = new AttributeDisplay("Startdato", "");
    private final AttributeDisplay slutDato = new AttributeDisplay("Slutdato", "");
    private final AttributeDisplay literVand = new AttributeDisplay("Liter vand i destillatet", "");
    private final AttributeDisplay alkoholProcent = new AttributeDisplay("Alkohol procent", "");
    private final AttributeDisplay røget = new AttributeDisplay("Røget", "");
    private final AttributeDisplay væskeMængde = new AttributeDisplay("Liter af væske i alt", "");
    private final AttributeDisplay maltBatch = new AttributeDisplay("Malt batch", "");

    public DestillatTab() {
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);

        destillatListView.getListView().getItems().setAll(Controller.getDestillater());
        destillatListView.getListView().setMinWidth(300);
        destillatListView.getListView().setPrefHeight(500);

        VBox venstreBox = new VBox(5);
        venstreBox.setPadding(new Insets(0, 5, 10, 10));
        venstreBox.getChildren().addAll(søgDestillat, destillatListView);
        this.add(venstreBox, 0, 0);

        VBox højreBox = new VBox(5);
        højreBox.setPadding(new Insets(0, 5, 10, 10));
        højreBox.getChildren().addAll(destillatID, startDato, slutDato, literVand, væskeMængde, alkoholProcent, røget, maltBatch);
        this.add(højreBox, 1, 0);

        destillatListView.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                destillatID.setValue(newValue.getDestillatID());

                DateTimeFormatter longDateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
                startDato.setValue(newValue.getStartDato().format(longDateFormat));
                slutDato.setValue(newValue.getStartDato().format(longDateFormat));

                literVand.setValue(String.valueOf(newValue.getLiterVand()));
                væskeMængde.setValue(String.valueOf(newValue.getVæskemængde()));
                alkoholProcent.setValue(String.valueOf(newValue.getAlkoholProcent()));

                if (newValue.isRøget()) røget.setValue("Ja");
                else røget.setValue("Nej");

                maltBatch.setValue(String.valueOf(newValue.getMaltBatch()));
            }
        });
        søgDestillat.getTextField().setOnAction(event -> søgning());
    }

    //TODO tilføj søgningsmetode til controller
    private void søgning() {
    }
}