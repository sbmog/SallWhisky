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
import storage.Storage;

import java.util.ArrayList;
import java.util.List;

import static gui.component.InputValidering.visDialog;

public class RegistrerWhiskyPane extends Stage {
    private static RegistrerWhiskyPane registrerWhiskyPane = null;
    private final List<Tapning> tapninger = new ArrayList<>();
    private final LabeledTextInput whiskyIdInput = new LabeledTextInput("Whisky ID");
    private final LabeledTextInput whiskyNavnInput = new LabeledTextInput("Indtast whisky navn");
    private final LabeledTextInput alkoholProcentInput = new LabeledTextInput("Indtast alkoholprocent");
    private final LabeledCheckBoxInput fortyndningCheckBox = new LabeledCheckBoxInput("Tilføj fortyndning", "Ja");
    private final LabeledTextInput vandMængdeFraFadInput = new LabeledTextInput("Indtast vandmængde");
    private final LabeledComboBoxInput<WhiskyType> whiskyTypeInput = new LabeledComboBoxInput<>("Vælg whisky type");
    private final LabeledComboBoxInput<Integer> flaskeStørrelseCombo = new LabeledComboBoxInput<>("Vælg flaske størrelse (cl)");
    private final LabeledTextInput antalFlaskerOutPut = new LabeledTextInput("Antal flasker (beregnet)");
    private final LabeledButton tilføjTapning = new LabeledButton("Tilføj tapning", "Tilføj");
    private final LabeledButton registrerButton = new LabeledButton("Registrer Whisky", "Registrer");


    public static RegistrerWhiskyPane getInstance(Fad fad, double antalLiter, double fortynding, Tapning nyTapning) {
        if (registrerWhiskyPane == null) {
            registrerWhiskyPane = new RegistrerWhiskyPane(fad, antalLiter, fortynding, nyTapning);
        } else {
            registrerWhiskyPane.addTapning(nyTapning);
        }
        return registrerWhiskyPane;
    }

    public RegistrerWhiskyPane(Fad fad, double antalLiter, double fortynding, Tapning førsteTapning) {
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
        vbox.getChildren().addAll(whiskyIdInput, whiskyNavnInput, alkoholProcentInput, vandMængdeFraFadInput,
                fortyndningCheckBox, whiskyTypeInput, flaskeStørrelseCombo, antalFlaskerOutPut, spacer, tilføjTapning, registrerButton);

        whiskyIdInput.setInputValue(String.valueOf(Storage.getWhiskyer().size() + 1));
        whiskyIdInput.setDisable(true);
        vandMængdeFraFadInput.setInputValue(String.valueOf(antalLiter));
        vandMængdeFraFadInput.setDisable(true);
        fortyndningCheckBox.getCheckBox().setDisable(true);
        antalFlaskerOutPut.setDisable(true);

        tapninger.add(førsteTapning);

        if (fortynding > 0) {
            fortyndningCheckBox.getCheckBox().setSelected(true);
            visDialog(Alert.AlertType.INFORMATION, "fortynding", "fortynding: " + fortynding + " liter");
        }
        whiskyTypeInput.addItems(WhiskyType.values());

        flaskeStørrelseCombo.addItems(5, 50, 70);
        flaskeStørrelseCombo.getComboBox().valueProperty().addListener((obs, oldVal, newVal) -> {
            updateAntalFlasker();
        });

        updateAntalFlasker();

        tilføjTapning.getButton().setOnAction(e -> {
            OpretTapningPane nyTapning = new OpretTapningPane();
            nyTapning.show();
        });

        registrerButton.getButton().setOnAction(event -> håndterWhiskyRegistrering());

        Scene scene = new Scene(vbox, 300, 650);
        this.setScene(scene);
        this.show();
    }

    private void håndterWhiskyRegistrering() {
        try {
            double whiskyID = Double.parseDouble(whiskyIdInput.getInputValue());
            String navn = whiskyNavnInput.getInputValue();
            double alkoholProcent = Double.parseDouble(alkoholProcentInput.getInputValue());
            boolean fortyndet = fortyndningCheckBox.getCheckBox().isSelected();
            double whiskyMængde = Double.parseDouble(vandMængdeFraFadInput.getInputValue());
            WhiskyType whiskyType = whiskyTypeInput.getComboBox().getValue();
            Integer flaskeStørrelse = flaskeStørrelseCombo.getComboBox().getValue();

            if (navn == null || navn.isEmpty()) {
                throw new IllegalArgumentException("Whisky navn skal udfyldes.");
            }
            if (whiskyType == null) {
                throw new IllegalArgumentException("Whisky type skal vælges.");
            }
            if (flaskeStørrelse == null || flaskeStørrelse <= 0) {
                throw new IllegalArgumentException("Flaske størrelse skal være valgt og større end 0.");
            }

            // Opret Whisky
            Whisky whisky = Controller.createWhisky(
                    whiskyID,
                    navn,
                    alkoholProcent,
                    fortyndet,
                    whiskyMængde,
                    new ArrayList<>(tapninger),
                    whiskyType
            );

            // Beregn og opret flasker (sum over alle tapninger)
            int antalFlasker = 0;
            for (Tapning t : tapninger) {
                antalFlasker += t.beregnAntalFlasker(flaskeStørrelse);
            }

            for (int i = 0; i < antalFlasker; i++) {
                whisky.createFlaske();
            }

            // Vis bekræftelse
            visDialog(Alert.AlertType.CONFIRMATION, "Whisky registreret",
                    "Whisky med navn: " + navn + " er registreret med " + antalFlasker + " flasker.");

            this.close();

        } catch (NumberFormatException e) {
            visDialog(Alert.AlertType.ERROR, "Ugyldigt input", "Alkoholprocent og vandmængde skal være tal.");
        } catch (IllegalArgumentException e) {
            visDialog(Alert.AlertType.ERROR, "Fejl ved registrering", e.getMessage());
        }
    }


    public void addTapning(Tapning tapning) {
        this.tapninger.add(tapning);
        updateAntalFlasker();
    }

    private void updateAntalFlasker() {
        Integer flaskeStørrelse = flaskeStørrelseCombo.getComboBox().getValue();
        if (flaskeStørrelse != null && flaskeStørrelse > 0) {
            try {
                int samletAntalFlasker = 0;
                for (Tapning t : tapninger) {
                    samletAntalFlasker += t.beregnAntalFlasker(flaskeStørrelse);
                }
                antalFlaskerOutPut.getTextField().setText(String.valueOf(samletAntalFlasker));
            } catch (Exception e) {
                antalFlaskerOutPut.getTextField().setText("Beregningsfejl");
            }
        } else {
            antalFlaskerOutPut.getTextField().setText("Vælg flaske størrelse");
        }

    }
}
