package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Tapning;
import application.model.Whisky;
import gui.component.AttributeDisplay;
import gui.component.LabeledButton;
import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import gui.produktionsAdm.WhiskyHistorikPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static gui.component.InputValidering.visDialog;

public class WhiskyTab extends BaseTab<Whisky> {
    //    AttributeDisplays
    private final AttributeDisplay whiskyID = new AttributeDisplay("Whisky ID", "");
    private final AttributeDisplay whiskyNavn = new AttributeDisplay("Navn", "");
    private final AttributeDisplay alkoholProcent = new AttributeDisplay("Alkohol procent", "");
    private final AttributeDisplay fortyndet = new AttributeDisplay("Fortyndet", "");
    private final AttributeDisplay vandmængde = new AttributeDisplay("Vandmængde fra fad", "");
    private final AttributeDisplay antalFlasker = new AttributeDisplay("Antal flasker", "");
    private final LabeledListViewInput<Tapning> tapninger = new LabeledListViewInput<>("Tapninger af fade");
    private final AttributeDisplay whiskyType = new AttributeDisplay("Whisky type", "");
    private final LabeledButton hentHistorik = new LabeledButton("Hent historik", "Historik");

    public WhiskyTab() {
        super("Søg whisky", "Whiskyer");

        liste.getListView().getItems().setAll(Controller.getWhiskyer());

        tapninger.getListView().setPrefHeight(150);
        attributVisning.getChildren().addAll(whiskyID, whiskyNavn, whiskyType, alkoholProcent, fortyndet, vandmængde, antalFlasker, tapninger, hentHistorik);

        liste.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

        søgeFelt.getTextField().setOnAction(event -> søgning());

        hentHistorik.getButton().setOnAction(event -> hentHistorik());
    }

    private void søgning() {
        String søgeTekst = søgeFelt.getInputValue().toLowerCase().trim();
        liste.getListView().getItems().setAll(Controller.søgWhisky(søgeTekst));
    }

    private void hentHistorik() {
        Whisky selectedWhisky = liste.getListView().getSelectionModel().getSelectedItem();
        if (selectedWhisky != null) {
            WhiskyHistorikPane historikPane = new WhiskyHistorikPane(selectedWhisky);
            historikPane.showAndWait();
        } else {
            visDialog(Alert.AlertType.WARNING, "Ingen whisky valgt", "Vælg en whisky fra listen for at se historikken.");
        }
    }
}

