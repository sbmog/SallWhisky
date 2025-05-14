package gui.opretPåfyldning;

import application.controller.Controller;
import application.model.*;
import gui.component.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

import static gui.component.InputValidering.visDialog;

public class OpretPåfyldningPane extends Stage {

    private final LabeledTextInput initialerInput = new LabeledTextInput("Initialer for den ansvarlige");
    private final LabeledTextInput antalLiterInput = new LabeledTextInput("Antal Liter påfyldt");

    private final TextInputWithListViewInput<Destillat> destillater = new TextInputWithListViewInput<>("Vælg destillatet der er klar til påfyldning", "Søg destillat");

    private final TextInputWithListViewInput<Fad> fade = new TextInputWithListViewInput<>("Vælg et ledig fad der skal fyldes på", "Søg fad");

    private final TextInputWithTreeViewInput<Object> hyldePladser = new TextInputWithTreeViewInput<>("Vælg en fri hylde at placere fadet på", "Søg Hylde plads");

    private final LabeledButton opretButton = new LabeledButton("Registrer påfyldning", "Registrer");

    public OpretPåfyldningPane() {
        this.setTitle("Påfyldning af fad");

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        VBox root = new VBox(5, initialerInput, destillater, fade, antalLiterInput, hyldePladser, spacer, opretButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 700);
        this.setScene(scene);
        this.show();

        destillater.getListView().getItems().setAll(Controller.getDestillaterMedResterendeIndhold());
        fade.getListView().getItems().setAll(Controller.getLedigeFade());
        opbygHyldePladsTreeView();
        konfigurerDestillatListView();
        konfigurerFadListView();

        destillater.getTextField().textProperty().addListener((obs, oldVal, newVal) -> søgDestillat(newVal));
        fade.getTextField().textProperty().addListener((obs, oldVal, newVal) -> søgFad(newVal));
        hyldePladser.getTextField().textProperty().addListener((obs, oldVal, newVal) -> søgHyldePlads(newVal));

        opretButton.getButton().setOnAction(e -> håndterOpretButton());
    }

    private void opbygHyldePladsTreeView() {
        TreeItem<Object> root = new TreeItem<>("Lagre");

        for (Lager lager : Controller.getLagre()) {
            TreeItem<Object> lagerItem = new TreeItem<>(lager);
            for (Reol reol : lager.getReoler()) {
                TreeItem<Object> reolItem = new TreeItem<>(reol);
                for (HyldePlads hylde : reol.getHyldePladser()) {
                    if (hylde.isPladsFri()) {
                        TreeItem<Object> hyldeItem = new TreeItem<>(hylde);
                        reolItem.getChildren().add(hyldeItem);
                    }
                }
                if (!reolItem.getChildren().isEmpty()) {
                    lagerItem.getChildren().add(reolItem);
                }
            }
            if (!lagerItem.getChildren().isEmpty()) {
                root.getChildren().add(lagerItem);
            }
        }
        hyldePladser.setRoot(root);
        hyldePladser.expandAll(root);
    }


    private void håndterOpretButton() {
        if (validerOprettelse()) {
            String initialer = initialerInput.getInputValue();
            double antalLiter = Double.parseDouble(antalLiterInput.getInputValue());
            Fad fad = fade.getListView().getSelectionModel().getSelectedItem();
            Destillat destillat = destillater.getListView().getSelectionModel().getSelectedItem();
            TreeItem<Object> selectedHylde = hyldePladser.getSelectedItem();
            HyldePlads hyldePlads = null;
            if (selectedHylde != null && selectedHylde.getValue() instanceof HyldePlads) {
                hyldePlads = (HyldePlads) selectedHylde.getValue();
            }

            Controller.createPåfyldning(initialer, antalLiter, LocalDate.now(), fad, destillat, hyldePlads);
            visDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Påfyldning registreret",
                    "Påfyldning registreret på " + fad + " fra " + destillat);
            this.close();
        }
    }

    private boolean validerOprettelse() {
        InputValidering validering = new InputValidering();
        validering
                .validateNotEmpty(initialerInput, "Initialer for den ansvarlige må ikke være tomt.")
                .validateNotEmpty(antalLiterInput, "Antal Liter påfyldt må ikke være tomt.")
                .validateInteger(antalLiterInput, "Antal Liter påfyldt skal være et heltal")
                .validateListViewSelection(destillater.getListView(), "Der skal være valgt et destillat")
                .validateListViewSelection(fade.getListView(), "Der skal være valgt et fad");
        TreeItem<Object> selectedHylde = hyldePladser.getSelectedItem();
        if (selectedHylde == null || !(selectedHylde.getValue() instanceof HyldePlads)) {
            visDialog(Alert.AlertType.ERROR, "Validering fejlede", "Der skal være valgt en ledig hyldeplads");
            return false;
        }
        return validering.isValid();
    }

    private void søgFad(String input) {
        String søgetekst = fade.getTextField().getText().toLowerCase().trim();
        if (!søgetekst.isEmpty()) {
            List<Fad> resultater = Controller.søgFade(søgetekst).stream()
                    .filter(fad -> fad.getFadPlacering() == null)
                    .toList();
            fade.getListView().getItems().setAll(resultater);
        } else {
            fade.getListView().getItems().setAll(Controller.getLedigeFade());
        }
    }

    private void søgDestillat(String input) {
        String søgetekst = destillater.getTextField().getText().toLowerCase().trim();
        if (!søgetekst.isEmpty()) {
            List<Destillat> resultater = Controller.søgDestillat(søgetekst);
            destillater.getListView().getItems().setAll(resultater);
        } else {
            destillater.getListView().getItems().setAll(Controller.getDestillater());
        }
    }

    private void søgHyldePlads(String input) {
        String søgeTekst = hyldePladser.getTextField().getText().toLowerCase().trim();

        TreeItem<Object> root = new TreeItem<>("Lagre");

        for (Lager lager : Controller.getLagre()) {
            boolean lagerMatch = lager.toString().toLowerCase().contains(søgeTekst);
            TreeItem<Object> lagerItem = new TreeItem<>(lager);
            for (Reol reol : lager.getReoler()) {
                boolean reolMatch = reol.toString().toLowerCase().contains(søgeTekst);
                TreeItem<Object> reolItem = new TreeItem<>(reol);
                for (HyldePlads hylde : reol.getHyldePladser()) {
                    boolean hyldeMatch = hylde.toString().toLowerCase().contains(søgeTekst);
                    if ((lagerMatch || reolMatch || hyldeMatch) && hylde.isPladsFri()) {
                        reolItem.getChildren().add(new TreeItem<>(hylde));
                    }
                }
                if (!reolItem.getChildren().isEmpty()) {
                    lagerItem.getChildren().add(reolItem);
                }
            }
            if (!lagerItem.getChildren().isEmpty()) {
                root.getChildren().add(lagerItem);
            }
        }
        hyldePladser.setRoot(root);
        hyldePladser.expandAll(root);
    }

    private void konfigurerDestillatListView() {
        destillater.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Destillat destillat, boolean empty) {
                super.updateItem(destillat, empty);
                if (empty || destillat == null) {
                    setText(null);
                } else {
                    double literTilbage = Controller.getAntalLiterTilbagePåDestillat(destillat);
                    setText(destillat.getDestillatID() + " (" + String.format("%.1f", literTilbage) + " L tilbage)");
                }
            }
        });
    }

    private void konfigurerFadListView() {
        fade.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    setText("Fad #" + fad.getFadID() + " (" + String.format("%.0f", fad.getFadILiter()) + " L)");
                }
            }
        });
    }
}
