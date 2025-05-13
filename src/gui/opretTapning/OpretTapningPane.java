package gui.opretTapning;

import application.controller.Controller;
import application.model.Fad;
import application.model.Tapning;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
                Fad selectedFad = (Fad) fad.getComboBox().getValue();
                if (selectedFad != null) {
                    Tapning tempTapning = new Tapning(LocalDate.now(), "temp", Double.parseDouble(newValue), selectedFad);
                    angelShareUpdate(tempTapning, angelShareInput.getTextField());
                }
            } catch (NumberFormatException e) {
                angelShareInput.getTextField().setText("Ugyldigt input");
            }
        });
        setupWhiskyMængdeUpdater();


        opretDestillatButton.getButton().setOnAction(event -> HåndterOpretTapning());


        fortyndingInput.getTextField().setDisable(true);

        fortyndingCheckBox.getCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
            fortyndingInput.getTextField().setDisable(!newValue);
            if (!newValue) {
                fortyndingInput.getTextField().clear();
            }
        });
    }

    private void angelShareUpdate(Tapning tapning, TextField angelShareInput) {
        try {
            double angelShare = tapning.angelShareiProcent();
            angelShareInput.setText(String.format("%.2f", angelShare) + " %");
        } catch (Exception e) {
            angelShareInput.setText("Fejl ved beregning");
        }
    }

    private void setupWhiskyMængdeUpdater() {
        antalLiterFraFadInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> updateWhiskyMængde());
        fortyndingInput.getTextField().textProperty().addListener((observable, oldValue, newValue) -> updateWhiskyMængde());
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
            double fortyndning = 0;
            if (fortyndingCheckBox.isSelected()) {
                fortyndning = Double.parseDouble(fortyndingInput.getInputValue());
                newTapning.createFortynding(fortyndning);

            }

            if (fortyndingCheckBox.isSelected()) {
                double fortyndingLiter = Double.parseDouble(fortyndingInput.getInputValue());
                newTapning.createFortynding(fortyndingLiter);
            }

            selectedFad.opdaterNuværendeInhold(antalLiterFraFad);

            if (selectedFad.getNuværendeIndhold() == 0) {
                selectedFad.fjernFraHyldeHvisTom();
                visDialog(Alert.AlertType.INFORMATION, "Fad fjerners fra hylde", "Fad #" + selectedFad.getFadID() + " er nu tomt og fjernes fra hylde.");
            }

            double totalMængde = antalLiterFraFad + fortyndning;
            new RegistrerWhiskyPane(selectedFad, totalMængde, fortyndning, newTapning);


            visDialog(Alert.AlertType.CONFIRMATION, "Fadet er tappet", "Fad #" + selectedFad.getFadID() + " er nu tappet med " + totalMængde + " liter.");

            this.close();

        } catch (NumberFormatException e) {
            visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Antal liter og fortynding skal være tal.");
        } catch (IllegalArgumentException | NullPointerException e) {
            visDialog(Alert.AlertType.ERROR, "Fejl ved oprettelse", e.getMessage());


        }
    }
}
