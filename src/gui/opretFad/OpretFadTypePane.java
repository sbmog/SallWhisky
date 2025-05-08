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

public class OpretFadTypePane extends Stage {
    public OpretFadTypePane() {
        this.setTitle("Registrér ny fadtype");

        VBox root = new VBox(5);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 200, 150);
        this.setScene(scene);

        LabeledTextInput navnInput = new LabeledTextInput("Indtast navn");
        LabeledButton opretButton = new LabeledButton("Opret ny fadtype", "Opret");

        root.getChildren().addAll(navnInput, opretButton);

        opretButton.getButton().setOnAction(e -> {
            try {
                Controller.createFadType(navnInput.getInputValue());
                this.close();
            } catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fejl ved oprettelse");
                alert.setHeaderText("Kunne ikke oprette ny fadtype");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        });
    }
}
