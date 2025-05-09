package gui.lagerAdm.inputPanes;

import application.controller.Controller;
import application.model.Fad;
import application.model.HyldePlads;
import gui.component.InputValidering;
import gui.component.LabeledButton;
import gui.component.LabeledComboBoxInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class FlytFadInputPane extends Stage {

    private Fad fadDerSkalFlyttes;

    private final LabeledComboBoxInput<HyldePlads> hyldePladsComboBox = new LabeledComboBoxInput<>("Vælg en ny hyldeplads");
    private final LabeledButton flytButton = new LabeledButton("Flyt fad" + fadDerSkalFlyttes.getFadID(), "Flyt");
    private final LabeledButton annullerButton = new LabeledButton("Annuller flytning af fad", "Annuller");

    public FlytFadInputPane(Fad fad) {
        this.fadDerSkalFlyttes = fad;
        this.setTitle("Flyt fad" + fad.getFadID());

        VBox root = new VBox(10);
        root.setPadding(new Insets(0, 5, 10, 10));
        root.setAlignment(Pos.CENTER);

        hyldePladsComboBox.addItems(Controller.getAlleFrieHyldePladser());
        HBox buttonBox = new HBox(10, annullerButton, flytButton);

        root.getChildren().addAll(hyldePladsComboBox, buttonBox);
        this.setScene(new Scene(root, 400, 150));

        flytButton.getButton().setOnAction(e -> {
            HyldePlads valgtHyldePlads = hyldePladsComboBox.getSelectedValue();
            if (validerOprettelse()) {
                Controller.flytFadTilNyHylde(fad, valgtHyldePlads);
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Flad flytter",
                        fadDerSkalFlyttes + " er blevet flyttet til " + valgtHyldePlads);
                this.close();
            }
        });

        annullerButton.getButton().setOnAction(e -> this.close());
    }

    private boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering.validateSelected(hyldePladsComboBox, "Der skal vælges en ny hyldeplads");
        return validering.isValid();
    }
}
