package gui.opretFad;

import application.controller.Controller;
import gui.component.LabeledButton;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class OpretFadTypePane extends Stage {
    private final LabeledTextInput navnInput = new LabeledTextInput("Indtast navn");

    public OpretFadTypePane() {
        this.setTitle("Registrér ny fadtype");

        VBox root = new VBox(5);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 200, 150);
        this.setScene(scene);

        LabeledButton opretButton = new LabeledButton("Opret ny fadtype", "Opret");

        root.getChildren().addAll(navnInput, opretButton);

        opretButton.getButton().setOnAction(e -> opretFadType());
        navnInput.getTextField().setOnAction(e -> opretFadType());
    }

    private void opretFadType() {
        try {
            Controller.createFadType(navnInput.getInputValue());
            visDialog(Alert.AlertType.CONFIRMATION,
                    "Fadtype oprettet",
                    Controller.getFadTyper().getLast()+" er nu oprettet");
            this.close();
        } catch (Exception exception) {
            visDialog(
                    Alert.AlertType.ERROR,
                    "Fejl ved oprettelse",
                    "Kunne ikke oprette ny fadtype" + exception.getMessage());
        }
    }
}
