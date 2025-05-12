package gui.opretPåfyldning;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.HyldePlads;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

import static gui.component.InputValidering.visDialog;

public class OpretPåfyldningPane extends Stage {

    private final LabeledTextInput initialerInput = new LabeledTextInput("Initialer for den ansvarlige");
    private final LabeledTextInput antalLiterInput = new LabeledTextInput("Antal Liter påfyldt");

    private final TextInputWithListViewInput<Destillat> destillater = new TextInputWithListViewInput<>("Vælg destillatet der fyldes på", "Søg destillat");

    private final TextInputWithListViewInput<Fad> fade = new TextInputWithListViewInput<>("Vælg ledige fade der skal fyldes på", "Søg fad");

    private final TextInputWithListViewInput<HyldePlads> hyldePladser = new TextInputWithListViewInput<>("Vælg en fri hylde at placere fadet på", "Søg Hylde plads");

    private final LabeledButton opretButton = new LabeledButton("Registrer påfyldning", "Registrer");

    public OpretPåfyldningPane() {
        this.setTitle("Påfyldning af fad");

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox root = new VBox(5, initialerInput, destillater, fade, antalLiterInput, hyldePladser, spacer, opretButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 600);
        this.setScene(scene);
        this.show();

        destillater.getListView().getItems().setAll(Controller.getDestillater());
        fade.getListView().getItems().setAll(Controller.getLedigeFade());
        hyldePladser.getListView().getItems().setAll(Controller.getAlleFrieHyldePladser());

        destillater.getTextField().setOnAction(e -> søgDestillat());
        fade.getTextField().setOnAction(e -> søgFad());
        hyldePladser.getTextField().setOnAction(e -> søgHyldePlads());

        opretButton.getButton().setOnAction(e -> håndterOpretButton());
    }

    private void håndterOpretButton() {
        if (validerOprettelse()) {
            String initialer = initialerInput.getInputValue();
            double antalLiter = Double.parseDouble(antalLiterInput.getInputValue());
            Fad fad = fade.getListView().getSelectionModel().getSelectedItem();
            Destillat destillat = destillater.getListView().getSelectionModel().getSelectedItem();
            HyldePlads hyldePlads = hyldePladser.getListView().getSelectionModel().getSelectedItem();

            Controller.createPåfyldning(initialer, antalLiter, LocalDate.now(), fad, destillat, hyldePlads);
            visDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Påfyldning registreret",
                    "Påfyldning registreret på " + fad + " fra " + destillat);
            this.close();
        }
    }

    private boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering
                .validateNotEmpty(initialerInput, "Initialer for den ansvarlige må ikke være tomt.")
                .validateNotEmpty(antalLiterInput, "Antal Liter påfyldt må ikke være tomt.")
                .validateInteger(antalLiterInput, "Antal Liter påfyldt skal være et heltal")
                .validateListViewSelection(destillater.getListView(), "Der skal være valgt et destillat")
                .validateListViewSelection(fade.getListView(), "Der skal være valgt et fad");
        return validering.isValid();
    }

    private void søgFad() {
//        TODO
    }

    private void søgDestillat() {
//        TODO
    }

    private void søgHyldePlads() {
        // TODO
    }
}
