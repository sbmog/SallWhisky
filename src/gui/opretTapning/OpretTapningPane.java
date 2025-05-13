package gui.opretTapning;

import application.controller.Controller;
import application.model.Fad;
import application.model.Påfyldning;
import application.model.Tapning;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static gui.component.InputValidering.visDialog;

public class OpretTapningPane extends Stage {
    private final HeaderLabel opretTapning = new HeaderLabel("Opret tapning");
    private final LabeledDateInput tapningsDatoInput = new LabeledDateInput("Indsæt tapningsdato");
    private final LabeledTextInput initialerForMedarbejderInput = new LabeledTextInput("Indtast initialer for medarbejder");
    private final LabeledTextInput antalLiterFraFadInput = new LabeledTextInput("Indtast antal liter fra fad");
    private final LabeledTextInput angelShareInput = new LabeledTextInput("Angel share i procent");
    private final LabeledComboBoxInput<Fad> fad = new LabeledComboBoxInput<>("Vælg fad");
    private final LabeledCheckBoxInput fortyndingCheckBox = new LabeledCheckBoxInput("Tilføj fortyndning", "Ja");
    private final LabeledTextInput fortyndingInput = new LabeledTextInput("Indtast fortyndning i liter");
    private final LabeledTextInput whiskyMængdeInput = new LabeledTextInput("Total mængde whisky i liter");
    private final LabeledButton opretDestillatButton = new LabeledButton("Opret tapning", "Opret");
    private final Label datoFejlLabel = new Label();

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

        datoFejlLabel.setTextFill(javafx.scene.paint.Color.RED);
        datoFejlLabel.setVisible(false);
        tapningsDatoInput.getChildren().add(datoFejlLabel);

        vbox.getChildren().addAll(opretTapning, fad, tapningsDatoInput, initialerForMedarbejderInput, antalLiterFraFadInput, angelShareInput, fortyndingCheckBox, fortyndingInput, whiskyMængdeInput, spacer, opretDestillatButton);

        vbox.setPadding(new Insets(0, 5, 10, 10));

        angelShareInput.getTextField().setEditable(false);
        whiskyMængdeInput.getTextField().setEditable(false);

        Scene scene = new Scene(vbox, 300, 650);
        this.setScene(scene);
        this.show();

        List<Fad> alleFade = Controller.getFade().stream().filter(f -> f.getNuværendeIndhold() > 0).collect(Collectors.toList());
        fad.addItems(alleFade);

        VBox.setVgrow(vbox, Priority.ALWAYS);


        antalLiterFraFadInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Fad selectedFad = fad.getComboBox().getValue();
                LocalDate tapningsDato = tapningsDatoInput.getInputValue();
                if (selectedFad != null && !newValue.isEmpty()) {
                    double antalLiter = Double.parseDouble(newValue);
                    double angelShare = Tapning.beregnAngelShareIProcent(antalLiter, selectedFad, tapningsDato);
                    angelShareInput.getTextField().setText(String.format("%.2f %%", angelShare));
                }
            } catch (NumberFormatException e) {
                angelShareInput.getTextField().setText("Ugyldigt input");
            } catch (IllegalArgumentException e) {
                datoFejlLabel.setText("Ikke klar til tapning");
                datoFejlLabel.setVisible(true);
            }
        });

        setupWhiskyMængdeUpdater();

        tapningsDatoInput.getDatePicker().valueProperty().addListener((obs,oldVal,newVal) -> {
            try {
                LocalDate valgtDato = newVal;

                LocalDate påfyldningsDato = fad.getSelectedValue().getPåfyldning().getDatoForPåfyldning();
                if (valgtDato.isBefore(påfyldningsDato.plusYears(3))) {
                    datoFejlLabel.setText("Destillatet er ikke klar til tapning.");
                    datoFejlLabel.setVisible(true);
                } else {
                    datoFejlLabel.setVisible(false);
                }
            } catch (Exception e) {
                datoFejlLabel.setText("Ugyldig dato valgt.");
                datoFejlLabel.setVisible(true);
            }
        });


        opretDestillatButton.getButton().setOnAction(event -> HåndterOpretTapning());


        fortyndingInput.getTextField().setDisable(true);

        fortyndingCheckBox.getCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
            fortyndingInput.getTextField().setDisable(!newValue);
            if (!newValue) {
                fortyndingInput.getTextField().clear();
            }
        });
    }

    private void setupWhiskyMængdeUpdater() {
        antalLiterFraFadInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> updateWhiskyMængde());
        fortyndingInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> updateWhiskyMængde());
        fad.getComboBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            antalLiterFraFadInput.getTextField().setText(antalLiterFraFadInput.getTextField().getText());
        });

        tapningsDatoInput.getDatePicker().valueProperty().addListener((obs, oldVal, newVal) -> {
            antalLiterFraFadInput.getTextField().setText(antalLiterFraFadInput.getTextField().getText());
        });
    }

    private void updateWhiskyMængde() {
        try {
            double antalLiter = Double.parseDouble(antalLiterFraFadInput.getInputValue());

            double totalMængde;
            if (!fortyndingCheckBox.isSelected()) {
                totalMængde = antalLiter;
            } else {
                double fortynding = Double.parseDouble(fortyndingInput.getInputValue());
                totalMængde = antalLiter + fortynding;
            }
            whiskyMængdeInput.getTextField().setText(String.format("%.2f", totalMængde));
        } catch (NumberFormatException e) {
            whiskyMængdeInput.getTextField().setText("Ugyldigt input");
        }
    }

    private void HåndterOpretTapning() {
        try {
            LocalDate tapningsDato = tapningsDatoInput.getInputValue();
            String initialerForMedarbejder = initialerForMedarbejderInput.getInputValue();
            int antalLiterFraFad = Integer.parseInt(antalLiterFraFadInput.getInputValue());
            Fad selectedFad = (Fad) this.fad.getComboBox().getValue();

            if (selectedFad == null) {
                throw new IllegalArgumentException("Fad skal vælges");
            }


            Tapning newTapning = Controller.createTapning(tapningsDato, initialerForMedarbejder, antalLiterFraFad, selectedFad);

            double fortynding = 0;

            if (fortyndingCheckBox.isSelected()) {
                double fortyndingLiter = Double.parseDouble(fortyndingInput.getInputValue());
                newTapning.createFortynding(fortyndingLiter);
            }

            selectedFad.opdaterNuværendeInhold(antalLiterFraFad);

            if (selectedFad.getNuværendeIndhold() == 0) {
                selectedFad.fjernFraHyldeHvisTom();
                visDialog(Alert.AlertType.INFORMATION, "Fad fjerners fra hylde", "Fad #" + selectedFad.getFadID() + " er nu tomt og fjernes fra hylde.");
            }

            double totalMængde = antalLiterFraFad + fortynding;
            new RegistrerWhiskyPane(selectedFad, totalMængde, fortynding, newTapning);

            visDialog(Alert.AlertType.CONFIRMATION, "Fadet er tappet", "Fad #" + selectedFad.getFadID() + " er nu tappet med " + totalMængde + " liter.");

            this.close();


        } catch (
                NumberFormatException e) {
            visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Vand, alkoholprocent og væskemængde skal være tal.");
        } catch (IllegalArgumentException |
                 NullPointerException e) {
            visDialog(Alert.AlertType.ERROR, "Fejl ved oprettelse", e.getMessage());
        }
    }
}
