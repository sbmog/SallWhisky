package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Malt;
import application.model.MaltBatch;
import gui.component.AttributeDisplay;
import gui.component.LabeledDateInput;
import gui.component.LabeledListViewInput;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MaltBatchTab extends BaseTab<MaltBatch> {

    private final AttributeDisplay batchNummer = new AttributeDisplay("Batchnummer", "");
    private final AttributeDisplay dato = new AttributeDisplay("Dato", "");
    private final AttributeDisplay mængde = new AttributeDisplay("Mængde i Kg", "");

    private final LabeledListViewInput<Malt> malte = new LabeledListViewInput<>("Malt");

    public MaltBatchTab() {
        super("Søg malt batch", "Malt batches");
        liste.getListView().getItems().addAll(Controller.getMaltBatch());

        malte.getListView().setPrefHeight(150);
        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        attributVisning.getChildren().addAll(batchNummer, dato, mængde, malte, spacer);

        liste.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                batchNummer.setValue(newValue.getBatchNummer());
                DateTimeFormatter longDateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
                dato.setValue(newValue.getDato().format(longDateFormat));
                mængde.setValue(newValue.getMængde() + " Kg");

                malte.getListView().getItems().clear();
                malte.getListView().getItems().setAll(newValue.getMalt());
            }
        });
        søgeFelt.getTextField().setOnAction(e -> søgning());
    }

    private void søgning() {
        String søgeTekst = søgeFelt.getInputValue().toLowerCase().trim();
        liste.getListView().getItems().setAll(Controller.søgMaltBatch(søgeTekst));
    }
}
