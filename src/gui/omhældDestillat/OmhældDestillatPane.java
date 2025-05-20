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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static gui.component.InputValidering.visDialog;

public class OmhældDestillatPane extends Stage {
    private final LabeledTextInput initialerInput = new LabeledTextInput("Indtast initialer for medarbejder");
    private final TextInputWithListViewInput<Fad> fraFad = new TextInputWithListViewInput<>("Vælg fad der skal hældes fra (Indhold)", "Søg fad");
    private final TextInputWithListViewInput<Fad> tilFad = new TextInputWithListViewInput<>("Vælg fad der skal fyldes på (Kapacitet)", "Søg fad");
    private final LabeledTextInput antalLiter = new LabeledTextInput("Antal liter der skal hældes");

    private final LabeledButton omhældButton = new LabeledButton("Omhæld destillat", "Omhæld");
    private final Label fejlLabel = new Label();

    public OmhældDestillatPane() {
        this.setTitle("Omhæld destillat");

        fejlLabel.setTextFill(javafx.scene.paint.Color.RED);
        fejlLabel.setVisible(false);

        fraFad.getListView().getItems().addAll(Controller.getFadeMedPåfyldning());
        tilFad.getListView().getItems().addAll(Controller.getFadeMedPlads());

        omhældButton.getButton().setOnAction(e -> omhældDestillat());

        VBox root = new VBox(10, initialerInput, fraFad, tilFad, antalLiter, fejlLabel, omhældButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 300, 700);
        this.setScene(scene);
        this.show();

        konfigurerFraFadListView();
        konfigurerTilFadListView();

        fraFad.getTextField().setOnAction(e -> søgFraFad());
        tilFad.getTextField().setOnAction(e -> søgTilFad());

        antalLiter.getTextField().setOnAction(e -> omhældDestillat());
    }

    private void søgTilFad() {
        String søgetekst = tilFad.getTextField().getText().toLowerCase().trim();
        if (!søgetekst.isEmpty()) {
            List<Fad> resultater = Controller.søgFade(søgetekst).stream()
                    .filter(fad -> fad.getFadILiter() - fad.getNuværendeIndhold() != 0)
                    .toList();
            tilFad.getListView().getItems().setAll(resultater);
        } else {
            tilFad.getListView().getItems().setAll(Controller.getFadeMedPlads());
        }
    }

    private void søgFraFad() {
        String søgetekst = fraFad.getTextField().getText().toLowerCase().trim();
        if (!søgetekst.isEmpty()) {
            List<Fad> resultater = Controller.søgFade(søgetekst).stream()
                    .filter(fad -> fad.getPåfyldning() != null && fad.getNuværendeIndhold() > 0)
                    .toList();
            fraFad.getListView().getItems().setAll(resultater);
        } else {
            fraFad.getListView().getItems().setAll(Controller.getFadeMedPåfyldning());
        }
    }

    private String validerInputs() {
        Fad fra = fraFad.getListView().getSelectionModel().getSelectedItem();
        Fad til = tilFad.getListView().getSelectionModel().getSelectedItem();

        // Valider valg
        if (fra == null || til == null) {
            return "Vælg både 'fra' og 'til' fad.";
        }

        // Valider liter
        String tekst = antalLiter.getTextField().getText();
        double liter = -1;
        if (tekst.isBlank()) {
            return "Angiv antal liter.";
        } else {
            try {
                liter = Double.parseDouble(tekst);
                if (liter <= 0) {
                    return "Antal liter skal være over 0.";
                }
            } catch (NumberFormatException e) {
                return "Antal liter skal være et tal.";
            }
        }

        // Valider størrelse
        if (fra != null && til != null && liter > 0) {
            if (liter > fra.getNuværendeIndhold()) {
                return "Antal liter overstiger kildefadets nuværende antal liter.";
            } else if (til.getNuværendeIndhold() + liter > til.getFadILiter()) {
                fejlLabel.setVisible(true);
                return "Antal liter overstiger modtagende fadets kapacitet.";
            }
        }
        return null;
    }

    private void visFejl(String fejltekst) {
        fejlLabel.setText(fejltekst);
        fejlLabel.setVisible(true);
    }

    private void omhældDestillat() {
        String fejl = validerInputs();
        if (fejl != null) {
            visFejl(fejl);
            return; // Stop hvis der er fejl
        }
        fejlLabel.setVisible(false);

        String initialer = initialerInput.getInputValue();
        Fad fra = fraFad.getListView().getSelectionModel().getSelectedItem();
        Fad til = tilFad.getListView().getSelectionModel().getSelectedItem();
        double liter = Double.parseDouble(antalLiter.getTextField().getText());

        Destillat destillat = fra.getPåfyldning() != null ? fra.getPåfyldning().getDestillat() : null;

        HyldePlads hyldePlads = til.getFadPlacering() != null ? til.getFadPlacering().getHyldePlads() : Controller.getFørsteLedigHyldePlads();

        Controller.omhældDestillat(liter, fra, til, destillat, hyldePlads, initialer);

        visDialog(Alert.AlertType.CONFIRMATION,
                "Antal liter omhældt",
                "Der er hældt " + liter + " liter fra fad #" + fra.getFadID() + " til fad #" + til.getFadID());

        // Opdater begge listviews med ny data
        fraFad.getListView().getItems().setAll(Controller.getFadeMedPåfyldning());
        tilFad.getListView().getItems().setAll(Controller.getFadeMedPåfyldning());

        this.close();
    }

    private void konfigurerFraFadListView() {
        fraFad.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                setText(empty || fad == null ? null :
                        "Fad #" + fad.getFadID() + " (" + String.format("%.0f", fad.getNuværendeIndhold()) + " L)");
            }
        });
    }

    private void konfigurerTilFadListView() {
        tilFad.getListView().setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Fad fad, boolean empty) {
                super.updateItem(fad, empty);
                if (empty || fad == null) {
                    setText(null);
                } else {
                    double indhold = fad.getNuværendeIndhold();
                    double kapacitet = fad.getFadILiter();
                    if (indhold > 0) {
                        setText("Fad #" + fad.getFadID() +
                                " - " + fad.getDestillater().getLast() +
                                " (" + String.format("%.0f", kapacitet - indhold) + " L tilbage)");
                    } else {
                        setText("Fad #" + fad.getFadID() +
                                " (" + String.format("%.0f", kapacitet - indhold) + " L)");
                    }
                }
            }
        });
    }
}
