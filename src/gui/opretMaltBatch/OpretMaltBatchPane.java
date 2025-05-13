package gui.opretMaltBatch;

import application.controller.Controller;
import application.model.Malt;
import application.model.MaltBatch;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

import static gui.component.InputValidering.visDialog;

public class OpretMaltBatchPane extends Stage {
    LabeledTextInput batchNummer = new LabeledTextInput("Indtast batchnummer");
    LabeledDateInput dato = new LabeledDateInput("Indtast dato");
    LabeledTextInput mængde = new LabeledTextInput("Total vægt i kg");
    LabeledComboBoxInput<Malt> maltInput = new LabeledComboBoxInput<>("Malt");
    LabeledButton opretMaltButton = new LabeledButton("Opret malt", "opret");
    LabeledButton opretMaltBatchButton = new LabeledButton("Opret maltbatch", "Opret");

    private final ArrayList<Malt> malts = new ArrayList<>();

    public OpretMaltBatchPane() {
        this.setTitle("Registrér ny maltbatch");

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 450);
        this.setScene(scene);
        this.show();

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        root.getChildren().addAll(batchNummer, dato, mængde, opretMaltButton, spacer, opretMaltBatchButton);

        mængde.setDisable(true);

        opretMaltButton.getButton().setOnAction(e -> opretMalt());

        opretMaltBatchButton.getButton().setOnAction(e -> {
            if (validerOprettelse()) {
                try {
                    String batchNummerInput = batchNummer.getInputValue();
                    LocalDate datoInput = dato.getInputValue();
                    double maengdeInput = Double.parseDouble(mængde.getInputValue());

                    // Brug malts-listen til at oprette en ny MaltBatch
                    Controller.createMaltBatch(batchNummerInput, datoInput, maengdeInput, malts);

                    visDialog(Alert.AlertType.CONFIRMATION,
                            "Maltbatch oprettet",
                            "Maltbatch med batchnummer " + batchNummerInput + " er nu oprettet.");
                    this.close();
                } catch (Exception exception) {
                    visDialog(
                            Alert.AlertType.ERROR,
                            "Fejl ved oprettelse",
                            "Kunne ikke oprette ny maltbatch: " + exception.getMessage());
                }
            }
        });
    }

    private boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering.validateNotEmpty(batchNummer, "Batchnummer må ikke være tomt")
                .validateNotEmpty(mængde, "Mængde må ikke være tomt")
                .validateInteger(mængde, "Mængde skal være et heltal");
        return validering.isValid();
    }

    private void opretMalt() {
        MaltBatch tempMaltBatch = new MaltBatch("temp", LocalDate.of(2020,1,1), 10, new ArrayList<>());
        OpretMaltPane opretMaltPane = new OpretMaltPane(tempMaltBatch);
        opretMaltPane.showAndWait();

        Malt nyMalt = opretMaltPane.getOprettetMalt();
        if (nyMalt != null) {
            malts.add(nyMalt);
            maltInput.getComboBox().getItems().add(nyMalt);
            maltInput.getComboBox().getSelectionModel().selectLast(); // Vælg den nyeste Malt

          int totalVægt = 0;
            for (int i = 0; i < malts.size(); i++) {
                totalVægt += malts.get(i).getMængde();
            }
            mængde.setInputValue(String.valueOf(totalVægt));

            visDialog(Alert.AlertType.CONFIRMATION,
                    "Malt oprettet",
                    nyMalt + " er nu oprettet og tilføjet til listen.");
        }
    }
}