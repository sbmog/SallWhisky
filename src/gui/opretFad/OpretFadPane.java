package gui.opretFad;

import application.controller.Controller;
import application.model.FadType;
import gui.component.InputValidering;
import gui.component.LabeledButton;
import gui.component.LabeledComboBoxInput;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class OpretFadPane extends Stage {

    private final LabeledTextInput literInput = new LabeledTextInput("Fadets størrelse i Liter");
    private final LabeledTextInput materialInput = new LabeledTextInput("Materiale");
    private final LabeledTextInput leverandørInput = new LabeledTextInput("Leverandør");

    private final LabeledComboBoxInput<FadType> fadtypeInput = new LabeledComboBoxInput<>("Fadtype:");

    private final LabeledButton opretFadTypeButton = new LabeledButton("Opret ny fadtype", "Opret");
    private final LabeledButton opretButton = new LabeledButton("Registrer fad", "Registrer");

    public OpretFadPane() {
        this.setTitle("Registrér nyt fad #" + Controller.getNæsteFadID());

        VBox root = new VBox(5);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 450);
        this.setScene(scene);
        this.show();

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        fadtypeInput.getComboBox().getItems().addAll(Controller.getFadTyper());

        root.getChildren().addAll(literInput, materialInput, leverandørInput, fadtypeInput, opretFadTypeButton, spacer, opretButton);

        opretFadTypeButton.getButton().setOnAction(e -> opretFadType());

        opretButton.getButton().setOnAction(e -> {
            if (validerOprettelse()) {
                double liter = Double.parseDouble(literInput.getInputValue());
                String materiale = materialInput.getInputValue();
                String leverandør = leverandørInput.getInputValue();
                FadType valgtFadType = fadtypeInput.getComboBox().getValue();
                Controller.createFad(liter, materiale, leverandør, 0, valgtFadType);
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Fadet er oprettet",
                        "Fad #" + Controller.getFade().size() + " er nu oprettet");

                this.close();
            }
        });
    }

    public boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering
                .validateNotEmpty(literInput, "Fadets størrelse i Liter må ikke være tomt.")
                .validateInteger(literInput, "Fadets størrelse i Liter skal være et heltal")
                .validateNotEmpty(materialInput, "Materiale må ikke være tomt.")
                .validateNotEmpty(leverandørInput, "Leverandør må ikke være tom.")
                .validateSelected(fadtypeInput, "Der skal vælges en fadtype.");
        return validering.isValid();
    }

    private void opretFadType() {
        OpretFadTypePane opretFadTypePane = new OpretFadTypePane();
        if (!opretFadTypePane.isShowing()) opretFadTypePane.showAndWait();
        fadtypeInput.getComboBox().getItems().setAll(Controller.getFadTyper());

        if (!fadtypeInput.getComboBox().getItems().isEmpty())
            fadtypeInput.getComboBox().getSelectionModel().selectLast();
    }
}
