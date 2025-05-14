package gui.opretTapning;

import application.controller.Controller;
import application.model.Fad;
import application.model.Tapning;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static gui.component.InputValidering.visDialog;

public class OpretTapningPane extends Stage {
    private final LabeledDateInput tapningsDatoInput = new LabeledDateInput("Indsæt tapningsdato");
    private final LabeledTextInput initialerForMedarbejderInput = new LabeledTextInput("Indtast initialer for medarbejder");
    private final LabeledTextInput antalLiterFraFadInput = new LabeledTextInput("Indtast antal liter fra fad");
    private final LabeledTextInput angelShareInput = new LabeledTextInput("Angel share i procent");
    private final LabeledComboBoxInput<Fad> fad = new LabeledComboBoxInput<>("Vælg fad");
    private final LabeledCheckBoxInput fortyndingCheckBox = new LabeledCheckBoxInput("Tilføj fortynding", "Ja");
    private final LabeledTextInput fortyndingInput = new LabeledTextInput("Indtast fortynding i liter");
    private final LabeledTextInput whiskyMængdeInput = new LabeledTextInput("Total mængde whisky i liter");
    private final LabeledButton opretDestillatButton = new LabeledButton("Opret tapning", "Opret");
    private final Label fejlLabel = new Label();

    public OpretTapningPane() {
        setTitle("Tapning af fad");
        setupGUI();
        initializeValues();
        setupListeners();
    }

    private void setupGUI() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(fad, tapningsDatoInput, initialerForMedarbejderInput, antalLiterFraFadInput, angelShareInput,
                fortyndingCheckBox, fortyndingInput, whiskyMængdeInput,
                fejlLabel, opretDestillatButton);

        angelShareInput.getTextField().setEditable(false);
        whiskyMængdeInput.getTextField().setEditable(false);
        fortyndingInput.getTextField().setDisable(true);

        fejlLabel.setTextFill(javafx.scene.paint.Color.RED);
        fejlLabel.setVisible(false);

        setScene(new Scene(root, 300, 700));
        show();
    }

    private void initializeValues() {
        List<Fad> alleFade = Controller.getFadeDerErKlarTilAtBliveTappet();
        List<Fad> fadeMedIndhold = new ArrayList<>();

        for (int i = 0; i < alleFade.size(); i++) {
            Fad f = alleFade.get(i);
            if (f.getNuværendeIndhold() > 0) {
                fadeMedIndhold.add(f);
            }
        }

        fad.addItems(fadeMedIndhold);
    }

    private void setupListeners() {
        antalLiterFraFadInput.getTextField().textProperty().addListener((obs, oldVal, newVal) -> {
            Fad valgteFad = fad.getComboBox().getValue();
            if (valgteFad == null) {
                clearFejl();
            return;
        }
                    try {
                        double antalliter = Double.parseDouble(newVal);
                        if(antalliter > valgteFad.getNuværendeIndhold()) {
                            visFejl("Du kan ikke tappe mere end fadets indhold (" + valgteFad.getNuværendeIndhold() + " L).");
                        } else {
                            clearFejl();
                        }
                    } catch (NumberFormatException e) {
                        clearFejl();
                    }

            updateAngelShare();
            updateWhiskyMængde();
        });

        tapningsDatoInput.getDatePicker().valueProperty().addListener((obs, oldVal, newVal) -> {
            updateAngelShare();
            checkTapningsDato();
        });

        fad.getComboBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            updateAngelShare();
            checkTapningsDato();
        });

        fortyndingCheckBox.getCheckBox().selectedProperty().addListener((obs, oldVal, newVal) -> {
            fortyndingInput.getTextField().setDisable(!newVal);
            if (!newVal) fortyndingInput.getTextField().clear();
            updateWhiskyMængde();
        });

        fortyndingInput.getTextField().textProperty().addListener((obs, oldVal, newVal) -> updateWhiskyMængde());

        opretDestillatButton.getButton().setOnAction(e -> håndterOpretTapning());
    }

    private void updateAngelShare() {
        try {
            Fad selectedFad = fad.getComboBox().getValue();
            LocalDate tapningsDato = tapningsDatoInput.getInputValue();
            double antalLiter = Double.parseDouble(antalLiterFraFadInput.getInputValue());

            if (selectedFad != null && tapningsDato != null) {
                double angelShare = Tapning.beregnAngelShareIProcent(antalLiter, selectedFad, tapningsDato);
                angelShareInput.getTextField().setText(String.format("%.2f %%", angelShare));
                clearFejl();
            }
        } catch (IllegalArgumentException e) {
            visFejl("Angel share beregning fejlede: " + e.getMessage());
        }
    }

    private void updateWhiskyMængde() {
        try {
            double antalLiter = Double.parseDouble(antalLiterFraFadInput.getInputValue());
            double fortynding = fortyndingCheckBox.isSelected() ?
                    Double.parseDouble(fortyndingInput.getInputValue()) : 0;
            whiskyMængdeInput.getTextField().setText(String.format("%.2f", antalLiter + fortynding));
            clearFejl();
        } catch (NumberFormatException e) {
            whiskyMængdeInput.getTextField().setText("Ugyldigt input");
        }
    }

    private void checkTapningsDato() {
        try {
            Fad selectedFad = fad.getComboBox().getValue();
            LocalDate valgtDato = tapningsDatoInput.getInputValue();
            if (selectedFad != null) {
                LocalDate påfyldningsDato = selectedFad.getPåfyldning().getDatoForPåfyldning();
                if (valgtDato.isBefore(påfyldningsDato.plusYears(3))) {
                    visFejl("Destillatet er ikke klar til tapning.");
                } else {
                    clearFejl();
                }
            }
        } catch (Exception e) {
            visFejl("Ugyldig dato valgt.");
        }
    }

    private void håndterOpretTapning() {
        try {
            LocalDate tapningsDato = tapningsDatoInput.getInputValue();
            String initialer = initialerForMedarbejderInput.getInputValue();
            int antalLiter = Integer.parseInt(antalLiterFraFadInput.getInputValue());
            Fad selectedFad = fad.getSelectedValue();

            if (selectedFad == null) throw new IllegalArgumentException("Fad skal vælges");
            if (antalLiter > selectedFad.getNuværendeIndhold()) {
                visFejl("Du kan ikke tappe mere end fadets indhold (" + selectedFad.getNuværendeIndhold() + " L).");
                return;
            }

            Tapning tapning = Controller.createTapning(tapningsDato, initialer, antalLiter, selectedFad);
            double fortynding = fortyndingCheckBox.isSelected() ?
                    Double.parseDouble(fortyndingInput.getInputValue()) : 0;

            if (fortynding > 0) {
                tapning.createFortynding(fortynding);
            }

            selectedFad.opdaterNuværendeInhold(antalLiter);
            if (selectedFad.getNuværendeIndhold() == 0) {
                selectedFad.fjernFraHyldeHvisTom();
                visDialog(Alert.AlertType.INFORMATION, "Fad fjernet", "Fad #" + selectedFad.getFadID() + " er nu tomt og fjernes fra hylde.");
            }

            new RegistrerWhiskyPane(selectedFad, antalLiter + fortynding, fortynding, tapning);

            visDialog(Alert.AlertType.CONFIRMATION, "Tapning oprettet", "Fad #" + selectedFad.getFadID() + " er tappet med " + (antalLiter + fortynding) + " liter.");
            this.close();

        } catch (Exception e) {
            visFejl("Fejl: " + e.getMessage());
        }
    }

    private void visFejl(String tekst) {
        fejlLabel.setText(tekst);
        fejlLabel.setVisible(true);
    }

    private void clearFejl() {
        fejlLabel.setText("");
        fejlLabel.setVisible(false);
    }
}
