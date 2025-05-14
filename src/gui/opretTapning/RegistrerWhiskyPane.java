package gui.opretTapning;

import application.controller.Controller;
import application.model.*;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static gui.component.InputValidering.visDialog;

public class RegistrerWhiskyPane extends Stage {
    private final Tapning tapning;
    private final HeaderLabel registrerWhiskyLabel = new HeaderLabel("Registrer Whisky");
    private final LabeledTextInput whiskyIdInput = new LabeledTextInput("Indtast whisky ID");
    private final LabeledTextInput whiskyNavnInput = new LabeledTextInput("Indtast whisky navn");
    private final LabeledTextInput alkoholProcentInput = new LabeledTextInput("Indtast alkoholprocent");
    private final LabeledCheckBoxInput fortyndningCheckBox = new LabeledCheckBoxInput("Tilføj fortyndning", "Ja");
    private final LabeledTextInput vandMængdeFraFadInput = new LabeledTextInput("Indtast vandmængde");
    private final LabeledComboBoxInput<WhiskyType> whiskyTypeInput = new LabeledComboBoxInput<> ("Vælg whisky type");
    private final LabeledComboBoxInput<Integer> flaskeStørrelseCombo = new LabeledComboBoxInput<>("Vælg flaske størrelse (cl)");
    private final LabeledTextInput antalFlaskerOutPut = new LabeledTextInput("Antal flasker (beregnet)");
    private final LabeledButton registrerButton = new LabeledButton("Registrer Whisky", "Registrer");

    public RegistrerWhiskyPane(Fad fad, double antalLiter, double fortynding, Tapning tapning) {
        this.tapning = tapning;
        this.setTitle("Registrer Whisky");

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
        vbox.getChildren().addAll(whiskyIdInput, whiskyNavnInput, alkoholProcentInput, vandMængdeFraFadInput,fortyndningCheckBox, whiskyTypeInput,flaskeStørrelseCombo,antalFlaskerOutPut,registrerButton);


        whiskyIdInput.setInputValue(String.valueOf(fad.getFadID()));
        whiskyIdInput.setDisable(true);
        vandMængdeFraFadInput.setInputValue(String.valueOf(antalLiter));
        vandMængdeFraFadInput.setDisable(true);
        fortyndningCheckBox.getCheckBox().setDisable(true);
        antalFlaskerOutPut.setDisable(true);


        if (fortynding > 0) {
            fortyndningCheckBox.getCheckBox().setSelected(true);
            visDialog(Alert.AlertType.INFORMATION,"fortynding", "fortynding: " + fortynding + " liter");
        }
        whiskyTypeInput.addItems(WhiskyType.values());

        flaskeStørrelseCombo.addItems(5,50,70);
        flaskeStørrelseCombo.getComboBox().valueProperty().addListener((obs,oldVal,newVal) -> {
            updateAntalFlasker(antalLiter);
        });


        updateAntalFlasker(antalLiter);
        registrerButton.getButton().setOnAction(event -> håndterWhiskyRegistrering(tapning));



        Scene scene = new Scene(vbox, 300, 600);
        this.setScene(scene);
        this.show();
    }
    private void håndterWhiskyRegistrering(Tapning tapning) {
        try {
            double whiskyID = Double.parseDouble(whiskyIdInput.getInputValue());
            String navn = whiskyNavnInput.getInputValue();
            double alkoholProcent = Double.parseDouble(alkoholProcentInput.getInputValue());
            boolean fortyndet = fortyndningCheckBox.getCheckBox().isSelected();
            new ArrayList<>(List.of(tapning));
            double WhiskyMængde = Double.parseDouble(vandMængdeFraFadInput.getInputValue());
            WhiskyType whiskyType = whiskyTypeInput.getComboBox().getValue();


            if (navn == null || navn.isEmpty()) {
                throw new IllegalArgumentException("Whisky navn skal udfyldes.");
            }
            if (whiskyType == null) {
                throw new IllegalArgumentException("Whisky type skal vælges.");
            }

            Controller.createWhisky(whiskyID, navn, alkoholProcent, fortyndet, WhiskyMængde,
                    new ArrayList<>(List.of(tapning)), whiskyType);

            this.close();

            visDialog(Alert.AlertType.CONFIRMATION, "Whisky registreret", "Whisky med navn: " + navn + " er registreret.");
            this.close();
        } catch (NumberFormatException e) {
            visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Alkoholprocent og vandmængde skal være tal.");
        } catch (IllegalArgumentException e) {
            visDialog(Alert.AlertType.ERROR, "Fejl ved registrering", e.getMessage());
        }

    }
    private void updateAntalFlasker(double liter) {
        Integer flaskeStørrelse = flaskeStørrelseCombo.getComboBox().getValue();
        if (flaskeStørrelse != null && flaskeStørrelse > 0) {
            try {
                if(tapning == null) {
                    throw new IllegalArgumentException("Tapning kan ikke være null");
                }

                int antalFlasker = tapning.beregnAntalFlasker(flaskeStørrelse);
                antalFlaskerOutPut.getTextField().setText(String.valueOf(antalFlasker));
            } catch (IllegalArgumentException e) {
                antalFlaskerOutPut.getTextField().setText("Ugyldig flaske størrelse");
            } catch (Exception e) {
                antalFlaskerOutPut.getTextField().setText("Tapning er ikke tilgængelig");
            }
        } else {
            antalFlaskerOutPut.getTextField().setText("Vælg flaske størrelse");
        }
    }
}
