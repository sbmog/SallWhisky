package gui.lagerAdm;

import application.model.Fad;
import application.model.HyldePlads;
import application.model.Lager;
import application.model.Reol;
import gui.component.AttributeDisplay;
import gui.component.HeaderLabel;
import javafx.scene.layout.VBox;

public class LagerInformationPane extends VBox {
    private final HeaderLabel information = new HeaderLabel("Information");

    private final AttributeDisplay lagerNavn = new AttributeDisplay("Lager navn", "");
    private final AttributeDisplay lagerAdresse = new AttributeDisplay("Lager adresse", "");
    private final AttributeDisplay antalFadPåLager = new AttributeDisplay("Antal fad på lager", "");

    private final AttributeDisplay reolID = new AttributeDisplay("Reol ID", "");
    private final AttributeDisplay hyldeID = new AttributeDisplay("Hylde plads ID", "");
    private final AttributeDisplay fadID = new AttributeDisplay("Fad ID", "");
    private final AttributeDisplay fadIndhold = new AttributeDisplay("Fadets indhold", "");

    public LagerInformationPane() {
        this.setMaxWidth(Double.MAX_VALUE);
        this.setSpacing(10);
        this.getChildren().addAll(
                information,
                lagerNavn, lagerAdresse, antalFadPåLager,
                reolID, hyldeID,
                fadID, fadIndhold
        );
    }

    public void clear() {
        lagerNavn.setValue("");
        lagerAdresse.setValue("");
        antalFadPåLager.setValue("");
        reolID.setValue("");
        hyldeID.setValue("");
        fadID.setValue("");
        fadIndhold.setValue("");
    }

    public void updateInfo(Object selectedObject) {
        clear();

        if (selectedObject instanceof Lager lager) {
            lagerNavn.setValue(lager.getNavn());
            lagerAdresse.setValue(lager.getAdresse());
            antalFadPåLager.setValue(String.valueOf(lager.getTotalAntalFadePåLager()));
        } else if (selectedObject instanceof Reol reol) {
            lagerNavn.setValue(reol.getLager().getNavn());
            lagerAdresse.setValue(reol.getLager().getAdresse());
            antalFadPåLager.setValue(String.valueOf(reol.getLager().getTotalAntalFadePåLager()));
            reolID.setValue(String.valueOf(reol.getReolID()));
        } else if (selectedObject instanceof HyldePlads hylde) {
            lagerNavn.setValue(hylde.getReol().getLager().getNavn());
            lagerAdresse.setValue(hylde.getReol().getLager().getAdresse());
            antalFadPåLager.setValue(String.valueOf(hylde.getReol().getLager().getTotalAntalFadePåLager()));
            reolID.setValue(String.valueOf(hylde.getReol().getReolID()));
            hyldeID.setValue(String.valueOf(hylde.getHyldePladsID()));
            if (!hylde.isPladsFri() && hylde.getFadPlaceret() != null) {
                Fad fad = hylde.getFadPlaceret().getFad();
                fadID.setValue(String.valueOf(fad.getFadID()));
                fadIndhold.setValue(String.valueOf(fad.getPåfyldning().getDestillat()));
            }
        } else if (selectedObject instanceof Fad fad) {
            HyldePlads hyldePlads = fad.getFadPlacering().getHyldePlads();
            lagerNavn.setValue(hyldePlads.getReol().getLager().getNavn());
            lagerAdresse.setValue(hyldePlads.getReol().getLager().getAdresse());
            antalFadPåLager.setValue(String.valueOf(hyldePlads.getReol().getLager().getTotalAntalFadePåLager()));
            reolID.setValue(String.valueOf(hyldePlads.getReol().getReolID()));
            hyldeID.setValue(String.valueOf(hyldePlads.getHyldePladsID()));
            fadID.setValue(String.valueOf(fad.getFadID()));
            fadIndhold.setValue(String.valueOf(fad.getPåfyldning().getDestillat()));
        }
    }
}
