package gui.opretDestillat;

import application.controller.Controller;
import application.model.MaltBatch;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

import static gui.component.InputValidering.visDialog;

public class OpretDestillatPane extends Stage {

    private final LabeledTextInput indtastDestillatID = new LabeledTextInput("Destillat navn");
    private final LabeledDateInput indtastDestillatStartDato = new LabeledDateInput("Indsæt startdato");
    private final LabeledDateInput indtastDestillatSlutDato = new LabeledDateInput("Indsæt slutdato");
    private final LabeledTextInput indtastDestillatLiterVand = new LabeledTextInput("Indsæt vand i liter");
    private final LabeledTextInput indtastDestillatAlkoholprocent = new LabeledTextInput("Indsæt alkoholprocent");
    private final LabeledTextInput indtastMæskningsMængde = new LabeledTextInput("Indsæt mæskningsmængde i liter");
    private final LabeledCheckBoxInput checkRøget = new LabeledCheckBoxInput("Er den røget", "Ja");
    private final LabeledComboBoxInput<MaltBatch> vælgBatch = new LabeledComboBoxInput<>("Vælg malt batch");
    private final LabeledButton opretDestillatButton = new LabeledButton("Opret destillat", "Opret");


    public OpretDestillatPane() {
        this.setTitle("Registrér ny destillation");

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(indtastDestillatID, indtastDestillatStartDato,
                indtastDestillatSlutDato, indtastDestillatLiterVand, indtastDestillatAlkoholprocent,
                indtastMæskningsMængde, checkRøget, vælgBatch, spacer, opretDestillatButton);

        Scene scene = new Scene(vbox, 300, 650);
        vbox.setPadding(new Insets(0, 5, 10, 10));
        this.setScene(scene);
        this.show();

        VBox.setVgrow(vbox, Priority.ALWAYS);

        vælgBatch.addItems(Controller.getMaltBatch());

        opretDestillatButton.getButton().setOnAction(event -> HåndterOpretDestillat());
    }

    private void HåndterOpretDestillat() {
        try {
            String navn = indtastDestillatID.getInputValue();
            LocalDate startDato = indtastDestillatStartDato.getInputValue();
            LocalDate slutDato = indtastDestillatSlutDato.getInputValue();
            double literVand = Double.parseDouble(indtastDestillatLiterVand.getInputValue());
            double alkoholProcent = Double.parseDouble(indtastDestillatAlkoholprocent.getInputValue());
            double væskemængde = Double.parseDouble(indtastMæskningsMængde.getInputValue());
            boolean røget = checkRøget.isSelected();
            MaltBatch batch = vælgBatch.getSelectedValue();

            Controller.createDestillat(navn, startDato, slutDato, literVand, alkoholProcent, røget, væskemængde, batch);

            visDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Destillat er oprettet med ID: " + navn,
                    " destillat er nu oprettet");
            this.close();

        } catch (NumberFormatException e) {
            visDialog(
                    Alert.AlertType.ERROR,
                    "Ugyldigt input",
                    "Ugyldigt input i et eller flere felter");
        } catch (IllegalArgumentException | NullPointerException e) {
            visDialog(
                    Alert.AlertType.ERROR,
                    "Fejl ved oprettelse",
                    e.getMessage());
        }
    }
}