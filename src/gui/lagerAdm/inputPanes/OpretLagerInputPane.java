package gui.lagerAdm.inputPanes;

import application.controller.Controller;
import gui.component.InputValidering;
import gui.component.LabeledButton;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class OpretLagerInputPane extends Stage {
    private final LabeledTextInput lagerID = new LabeledTextInput("Indtast lager ID");
    private final LabeledTextInput lagerNavn = new LabeledTextInput(" indtast lager navn");
    private final LabeledTextInput lagerAdresse = new LabeledTextInput("Indtast lager adresse");
    private final LabeledTextInput maxAntalReoler = new LabeledTextInput("Indtast max antal reoler på lageret");

    private final LabeledButton opretButton = new LabeledButton("Opret lager", "Opret");
    private final LabeledButton annullerButton = new LabeledButton("Annuller oprettelse", "Annuller");

    public OpretLagerInputPane() {
        this.setTitle("Opret lager");

        VBox root = new VBox();
        root.setPadding(new Insets(0, 5, 10, 10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 430, 350);
        this.setScene(scene);

        VBox info = new VBox(10);
        info.setAlignment(Pos.TOP_CENTER);
        info.getChildren().addAll(lagerID, lagerNavn, lagerAdresse, maxAntalReoler);

        VBox.setVgrow(info, Priority.ALWAYS);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        buttonBox.getChildren().addAll(annullerButton, opretButton);

        root.getChildren().addAll(info, buttonBox);

        opretButton.getButton().setOnAction(event -> {
            if (validerOprettelse()) {
                Controller.createLager(
                        lagerID.getInputValue(),
                        lagerNavn.getInputValue(),
                        lagerAdresse.getInputValue(),
                        Integer.parseInt(maxAntalReoler.getInputValue()));
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Lager oprettet",
                        lagerNavn + " er nu oprettet");
                this.close();
            }
        });

        annullerButton.getButton().setOnAction(event -> this.close());

    }

    public boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering
                .validateNotEmpty(lagerID, "Lager ID må ikke være tomt.")
                .validateNotEmpty(lagerNavn, "Lagernavn må ikke være tomt.")
                .validateNotEmpty(lagerAdresse, "Adresse må ikke være tom.")
                .validateNotEmpty(maxAntalReoler, "Max antal reoler skal udfyldes.")
                .validateInteger(maxAntalReoler, "Max antal reoler skal være et heltal.");
        return validering.isValid();
    }
}
