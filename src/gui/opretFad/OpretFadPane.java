package gui.opretFad;

import application.controller.Controller;
import application.model.FadType;
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

public class OpretFadPane extends Stage {

    LabeledTextInput literInput = new LabeledTextInput("Fadets størrelse i liter");
    LabeledTextInput materialInput = new LabeledTextInput("Materiale");
    LabeledTextInput leverandørInput = new LabeledTextInput("Leverandør");
    LabeledTextInput antalGangeBrugtInput = new LabeledTextInput("Antal gange brugt");
    LabeledComboBoxInput<FadType> fadtypeInput = new LabeledComboBoxInput<>("Fadtype:");
    LabeledButton opretFadTypeButton = new LabeledButton("Opret ny fadtype", "Opret");

    LabeledButton opretButton = new LabeledButton("Registrer fad", "Registrer");

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

        root.getChildren().addAll(literInput, materialInput, leverandørInput, antalGangeBrugtInput, fadtypeInput, opretFadTypeButton, spacer, opretButton);

        opretFadTypeButton.getButton().setOnAction(e -> opretFadType());

        opretButton.getButton().setOnAction(e -> {
            try {
                double liter = Double.parseDouble(literInput.getInputValue());
                String materiale = materialInput.getInputValue();
                String leverandør = leverandørInput.getInputValue();
                int antalGangeBrugt = Integer.parseInt(antalGangeBrugtInput.getInputValue());
                FadType valgtFadType = fadtypeInput.getComboBox().getValue();

                Controller.createFad(liter, materiale, leverandør, antalGangeBrugt, valgtFadType);

                this.close();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl ved oprettelse");
                alert.setHeaderText("Kunne ikke oprette fad");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        });

    }

    private void opretFadType() {
        OpretFadTypePane opretFadTypePane = new OpretFadTypePane();
        if (!opretFadTypePane.isShowing()) opretFadTypePane.showAndWait();
        fadtypeInput.getComboBox().getItems().setAll(Controller.getFadTyper());

        if (!fadtypeInput.getComboBox().getItems().isEmpty())
            fadtypeInput.getComboBox().getSelectionModel().selectLast();
    }
}
