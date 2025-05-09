package gui.opretTapning;

import application.controller.Controller;
import application.model.Fad;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OpretTapningPane extends Stage {
    private final HeaderLabel opretTapning = new HeaderLabel("Opret tapning");
    private final LabeledDateInput tapningsDatoInput = new LabeledDateInput("Indsæt tapningsdato");
    private final LabeledTextInput initialerForMedarbejderInput = new LabeledTextInput("Indtast initialer for medarbejder");
    private final LabeledTextInput antalLiterFraFadInput = new LabeledTextInput("Indtast antal liter fra fad");
    private final LabeledComboBoxInput fad = new LabeledComboBoxInput("Vælg fad");
    private final LabeledCheckBoxInput fortyndningCheckBox = new LabeledCheckBoxInput("Tilføj fortyndning", "Ja");
    private final LabeledTextInput fortyndningInput = new LabeledTextInput("Indtast fortyndning i liter");
    private final LabeledButton opretDestillatButton = new LabeledButton("Opret tapning", "Opret");


    public OpretTapningPane() {
        this.setTitle("Tapning af fad");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(10));
        pane.setVgap(2);
        pane.setHgap(2);

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(tapningsDatoInput, initialerForMedarbejderInput, antalLiterFraFadInput, fad,fortyndningCheckBox,fortyndningInput, spacer, opretDestillatButton);


        Scene scene = new Scene(vbox, 300, 400);
        this.setScene(scene);
        this.show();

        fad.addItems(Controller.getFade());

        VBox.setVgrow(vbox, Priority.ALWAYS);

        opretDestillatButton.getButton().setOnAction(event -> HåndterOpretDestillat());

    }

    private void HåndterOpretDestillat() {
        try {
            LocalDate tapningsDato = tapningsDatoInput.getInputValue();
            String initialerForMedarbejder = initialerForMedarbejderInput.getInputValue();
            int antalLiterFraFad = Integer.parseInt(antalLiterFraFadInput.getInputValue());
            Fad fad = (Fad) this.fad.getComboBox().getValue();

            Controller.createTapning(tapningsDato, initialerForMedarbejder, antalLiterFraFad, fad);
            this.close();

            AlertTypes.visBekræftDialog("Destillat oprettet", "Destillat er oprettet med ID: " + fad.getFadID() + " og tapningsdato: " + tapningsDato);
            this.close();

        } catch (NumberFormatException e) {
            AlertTypes.visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Vand, alkoholprocent og væskemængde skal være tal.");
        } catch (IllegalArgumentException | NullPointerException e) {
            AlertTypes.visDialog(Alert.AlertType.ERROR, "Fejl ved oprettelse", e.getMessage());
        }
    }
}
