package gui.lagerAdm.inputPanes;

import application.controller.Controller;
import gui.component.LabeledButton;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpretLagerInputPane extends Stage {
    private final LabeledTextInput lagerID = new LabeledTextInput("Indtast lager ID");
    private final LabeledTextInput lagerNavn = new LabeledTextInput(" indtast lager navn");
    private final LabeledTextInput lagerAdresse = new LabeledTextInput("Indtast lager adresse");
    private final LabeledTextInput maxAntalReoler = new LabeledTextInput("Indtast max antal reoler på lageret");

    private final LabeledButton opretButton = new LabeledButton("Opret lager", "Opret");
    private final LabeledButton annullerButton = new LabeledButton("Annuller oprettelse", "Annuller");

    public OpretLagerInputPane() {
        this.setTitle("Opret lager");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(10));
        pane.setVgap(2);
        pane.setHgap(2);

        Scene scene = new Scene(pane, 500, 660);
        this.setScene(scene);

        VBox info = new VBox(10);
        info.setAlignment(Pos.TOP_CENTER);
        info.setPadding(new Insets(10));
        pane.add(info, 0, 0);
        info.getChildren().addAll(lagerID, lagerNavn, lagerAdresse, maxAntalReoler);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BASELINE_CENTER);
        buttonBox.setPadding(new Insets(10));
        pane.add(buttonBox, 1, 0);
        buttonBox.getChildren().addAll(annullerButton, opretButton);

        opretButton.getButton().setOnAction(event -> {
            if (validerOprettelse()) {
                Controller.createLager(
                        lagerID.getInputValue(),
                        lagerNavn.getInputValue(),
                        lagerAdresse.getInputValue(),
                        Integer.parseInt(maxAntalReoler.getInputValue()));
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
