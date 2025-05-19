package gui.omhældDestillat;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.HyldePlads;
import gui.component.LabeledButton;
import gui.component.LabeledTextInput;
import gui.component.TextInputWithListViewInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class OmhældDestillatPane extends Stage {
    private final TextInputWithListViewInput<Fad> fraFad = new TextInputWithListViewInput<>("Vælg fad der skal hældes fra", "Søg fad");
    private final TextInputWithListViewInput<Fad> tilFad = new TextInputWithListViewInput<>("Vælg fad der skal fyldes på", "Søg fad");
    private final LabeledTextInput antalLiter = new LabeledTextInput("Antal liter der skal hældes");
    private final LabeledButton omhældButton = new LabeledButton("Omhæld destillat", "Omhæld");

    private final Label fejlLabel = new Label();

    public OmhældDestillatPane() {
        this.setTitle("Omhæld destillat");

        fejlLabel.setTextFill(javafx.scene.paint.Color.RED);
        fejlLabel.setVisible(false);

        fraFad.getListView().getItems().addAll(Controller.getFadeMedPåfyldning());
        tilFad.getListView().getItems().addAll(Controller.getFadeMedPåfyldning());

        omhældButton.getButton().setOnAction(e -> omhældDestillat());

        VBox root = new VBox(10, fraFad, tilFad, antalLiter, fejlLabel, omhældButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 700);
        this.setScene(scene);
        this.show();

        konfigurerFraFadListView();
        konfigurerTilFadListView();

        // Live-validering
        antalLiter.getTextField().textProperty().addListener((obs, oldVal, newVal) -> validerInputs());
        fraFad.getListView().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validerInputs());
        tilFad.getListView().getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> validerInputs());
    }

    private void validerInputs() {
        StringBuilder fejl = new StringBuilder();

        Fad fra = fraFad.getListView().getSelectionModel().getSelectedItem();
        Fad til = tilFad.getListView().getSelectionModel().getSelectedItem();

        // Valider valg
        if (fra == null || til == null) {
            fejl.append("Vælg både 'fra' og 'til' fad.\n");
        }

        // Valider liter
        String tekst = antalLiter.getTextField().getText();
        double liter = -1;
        if (tekst.isBlank()) {
            fejl.append("Angiv antal liter.\n");
        } else {
            try {
                liter = Double.parseDouble(tekst);
                if (liter <= 0) {
                    fejl.append("Antal liter skal være over 0.\n");
                }
            } catch (NumberFormatException e) {
                fejl.append("Antal liter skal være et tal.\n");
            }
        }

        // Valider størrelse
        if (fra != null && til != null && liter > 0) {
            if (liter > fra.getNuværendeIndhold()) {
                fejl.append("Antal liter overstiger kildefadets nuværende antal liter.\n");
            }
            else if (til.getNuværendeIndhold() + liter > til.getFadILiter()) {
                fejl.append("Antal liter overstiger modtagende fadets kapacitet.\n");
            }
        }

        // Vis eller skjul fejl
        if (fejl.length() > 0) {
            fejlLabel.setText(fejl.toString().trim());
            fejlLabel.setVisible(true);
        } else {
            fejlLabel.setVisible(false);
        }
    }

    private void omhældDestillat() {
        validerInputs();
        if (fejlLabel.isVisible()) {
            return; // Stop hvis der er fejl
        }

        Fad fra = fraFad.getListView().getSelectionModel().getSelectedItem();
        Fad til = tilFad.getListView().getSelectionModel().getSelectedItem();
        double liter = Double.parseDouble(antalLiter.getTextField().getText());

        Destillat destillat = fra.getPåfyldning() != null ? fra.getPåfyldning().getDestillat() : null;

        HyldePlads hyldePlads = til.getFadPlacering() != null ? til.getFadPlacering().getHyldePlads() : null;

        Controller.omhældDestillat(liter, fra, til, destillat, hyldePlads);

        // Opdater begge listviews med ny data
        fraFad.getListView().getItems().setAll(Controller.getFadeMedPåfyldning());
        tilFad.getListView().getItems().setAll(Controller.getFadeMedPåfyldning());

        visDialog(Alert.AlertType.CONFIRMATION,
                "Omhældning gennemført",
                "Destillat er hældt fra fad #" + fra.getFadID() + " til fad #" + til.getFadID());

        visDialog(Alert.AlertType.CONFIRMATION,
                "Antal liter omhældt",
                "Der er hældt " + liter + " liter fra fad #" + fra.getFadID() + " til fad #" + til.getFadID());

        this.close();
    }

    private void konfigurerFraFadListView() {
        fraFad.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                setText(empty || fad == null ? null :
                        "Fad #" + fad.getFadID() + " (" + String.format("%.0f", fad.getFadILiter()) + " L)");
            }
        });
    }

    private void konfigurerTilFadListView() {
        tilFad.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                setText(empty || fad == null ? null :
                        "Fad #" + fad.getFadID() + " (" + String.format("%.0f", fad.getFadILiter()) + " L)");
            }
        });
    }
}
