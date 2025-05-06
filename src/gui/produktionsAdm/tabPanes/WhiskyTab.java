package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Tapning;
import application.model.Whisky;
import gui.component.AttributeDisplay;
import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WhiskyTab extends GridPane {
    //    Venstre
    private final LabeledTextInput søgWhisky = new LabeledTextInput("Søg whisky");
    private final LabeledListViewInput<Whisky> whiskyListview = new LabeledListViewInput<>("Whiskyer");

    //    Højre
    private final AttributeDisplay whiskyID = new AttributeDisplay("Whisky ID", "");
    private final AttributeDisplay whiskyNavn = new AttributeDisplay("Navn", "");
    private final AttributeDisplay alkoholProcent = new AttributeDisplay("Alkohol procent", "");
    private final AttributeDisplay fortyndet = new AttributeDisplay("Fortyndet", "");
    private final AttributeDisplay vandmængde = new AttributeDisplay("Vandmængde fra fad", "");
    private final AttributeDisplay antalFlasker = new AttributeDisplay("Antal flasker", "");
    private final LabeledListViewInput<Tapning> tapninger = new LabeledListViewInput<>("Tapninger af fade");
    private final AttributeDisplay whiskyType = new AttributeDisplay("Whisky type", "");

    public WhiskyTab() {
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);

        whiskyListview.getListView().getItems().setAll(Controller.getWhiskyer());
        whiskyListview.getListView().setMinWidth(300);
        whiskyListview.getListView().setPrefHeight(500);

        VBox venstreBox = new VBox(5);
        venstreBox.setPadding(new Insets(0, 5, 10, 10));
        venstreBox.getChildren().addAll(søgWhisky, whiskyListview);
        this.add(venstreBox, 0, 0);

        VBox højreBox = new VBox(5);
        højreBox.setPadding(new Insets(0, 5, 10, 10));
        højreBox.getChildren().addAll(whiskyID, whiskyNavn, whiskyType, alkoholProcent, fortyndet, vandmængde, antalFlasker, tapninger);
        this.add(højreBox, 1, 0);

        whiskyListview.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                whiskyID.setValue(String.valueOf(newValue.getWhiskyID()));
                whiskyNavn.setValue(newValue.getNavn());
                whiskyType.setValue(String.valueOf(newValue.getWhiskyType()));
                alkoholProcent.setValue(String.valueOf(newValue.getAlkoholProcent()));

                if (newValue.isFortyndet()) fortyndet.setValue("Ja");
                else fortyndet.setValue("Nej");

                vandmængde.setValue(String.valueOf(newValue.getVandMængde()));
                antalFlasker.setValue(String.valueOf(newValue.getFlasker().size()));

                tapninger.getListView().getItems().clear();
                tapninger.getListView().getItems().setAll(newValue.getTapninger());
            }
        });

        søgWhisky.getTextField().setOnAction(event -> søgning());
    }

    //TODO tilføj søgningsmetode til controller
    private void søgning() {
    }
}

