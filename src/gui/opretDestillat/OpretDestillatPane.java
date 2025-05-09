package gui.opretDestillat;


import application.controller.Controller;
import application.model.MaltBatch;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storage.Storage;

import java.time.LocalDate;

public class OpretDestillatPane extends Stage {

    private final HeaderLabel opretDestiliat = new HeaderLabel("Opret destillat");

    private final LabeledTextInput indtastDestillatID = new LabeledTextInput("Destillat navn");
    private final LabeledDateInput indtastDestillatStartDato = new LabeledDateInput("Indsæt startdato");
    private final LabeledDateInput indtastDestillatSlutDato = new LabeledDateInput("Indsæt slutdato");
    private final LabeledTextInput indtastDestillatLiterVand = new LabeledTextInput("Indsæt vand i liter");
    private final LabeledTextInput indtastDestillatAlkoholprocent = new LabeledTextInput("Indsæt alkoholprocent");
    private final LabeledTextInput indtastMæskningsMængde = new LabeledTextInput("Indsæt mæskningsmængde i liter");
    private final LabeledCheckBoxInput checkRøget = new LabeledCheckBoxInput("Er den røget", "Ja");
    private final LabeledComboBoxInput vælgBatch = new LabeledComboBoxInput<>("Vælg batch");
    private final LabeledButton opretDestillatButton = new LabeledButton("Opret destillat", "Opret");


    public OpretDestillatPane() {
        this.setTitle("Registrér ny destillation");


        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(opretDestiliat, indtastDestillatID, indtastDestillatStartDato, indtastDestillatSlutDato, indtastDestillatLiterVand, indtastDestillatAlkoholprocent, indtastMæskningsMængde, checkRøget, vælgBatch, opretDestillatButton);


        Scene scene = new Scene(vbox, 300, 450);
        vbox.setPadding(new Insets(0, 5, 10, 10));
        this.setScene(scene);
        this.show();

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
            MaltBatch batch = (MaltBatch) vælgBatch.getSelectedValue();

            Controller.createDestillat(navn, startDato, slutDato, literVand, alkoholProcent, røget, væskemængde, batch);

            AlertTypes.visBekræftDialog("Destillat oprettet", "Destillat er oprettet med ID: " + navn);
            this.close();

        } catch (NumberFormatException e) {
            AlertTypes.visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Vand, alkoholprocent og væskemængde skal være tal.");
        } catch (IllegalArgumentException | NullPointerException e) {
            AlertTypes.visDialog(Alert.AlertType.ERROR, "Fejl ved oprettelse", e.getMessage());
        }
    }
}