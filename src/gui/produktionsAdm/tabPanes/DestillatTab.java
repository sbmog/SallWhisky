package gui.produktionsAdm.tabPanes;

import application.controller.Controller;
import application.model.Destillat;
import gui.component.AttributeDisplay;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DestillatTab extends BaseTab<Destillat> {
//    AttributeDisplays
    private final AttributeDisplay destillatID = new AttributeDisplay("Destillat ID", "");
    private final AttributeDisplay startDato = new AttributeDisplay("Startdato", "");
    private final AttributeDisplay slutDato = new AttributeDisplay("Slutdato", "");
    private final AttributeDisplay literVand = new AttributeDisplay("Liter vand i destillatet", "");
    private final AttributeDisplay alkoholProcent = new AttributeDisplay("Alkohol procent", "");
    private final AttributeDisplay røget = new AttributeDisplay("Røget", "");
    private final AttributeDisplay væskeMængde = new AttributeDisplay("Liter af væske i alt", "");
    private final AttributeDisplay maltBatch = new AttributeDisplay("Malt batch", "");

    public DestillatTab() {
        super("Søg destillat","Destillater");

        liste.getListView().getItems().addAll(Controller.getDestillater());

        attributVisning.getChildren().addAll(destillatID, startDato, slutDato, literVand, væskeMængde, alkoholProcent, røget, maltBatch);

        liste.getListView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                destillatID.setValue(newValue.getDestillatID());

                DateTimeFormatter longDateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
                startDato.setValue(newValue.getStartDato().format(longDateFormat));
                slutDato.setValue(newValue.getStartDato().format(longDateFormat));

                literVand.setValue(String.valueOf(newValue.getLiterVand()));
                væskeMængde.setValue(String.valueOf(newValue.getVæskemængde()));
                alkoholProcent.setValue(String.valueOf(newValue.getAlkoholProcent()));

                if (newValue.isRøget()) røget.setValue("Ja");
                else røget.setValue("Nej");

                maltBatch.setValue(String.valueOf(newValue.getMaltBatch()));
            }
        });
        søgeFelt.getTextField().setOnAction(event -> søgning());
    }

    //TODO tilføj søgningsmetode til controller
    private void søgning() {
    }
}