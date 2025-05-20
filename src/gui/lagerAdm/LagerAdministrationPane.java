package gui.lagerAdm;

import application.controller.Controller;
import application.model.Fad;
import application.model.HyldePlads;
import application.model.Lager;
import application.model.Reol;
import gui.component.HeaderLabel;
import gui.component.LabeledButton;
import gui.lagerAdm.inputPanes.FlytFadInputPane;
import gui.lagerAdm.inputPanes.OpretLagerInputPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import static gui.component.InputValidering.visBekræftDialog;
import static gui.component.InputValidering.visDialog;

public class LagerAdministrationPane extends VBox {
    private final HeaderLabel administrer = new HeaderLabel("Administrer");
    private final LabeledButton opretLager = new LabeledButton("Opret nyt lager", "Opret");
    private final LabeledButton sletLager = new LabeledButton("Slet valgte lager", "Slet");
    private final LabeledButton opretReol = new LabeledButton("Opret reol på valgte lager", "Opret");
    private final LabeledButton sletReol = new LabeledButton("Slet valgte reol", "Slet");
    private final LabeledButton opretHylde = new LabeledButton("Opret hyldeplads på valgte reol", "Opret");
    private final LabeledButton sletHylde = new LabeledButton("Slet valgte hyldeplads", "Slet");
    private final LabeledButton flytFad = new LabeledButton("Flyt fad til ny hyldeplads", "Flyt fad");

    private LagerTreeViewPane treeViewPane;

    public LagerAdministrationPane(LagerTreeViewPane treeViewPane) {
        this.treeViewPane = treeViewPane;

        this.setMaxWidth(Double.MAX_VALUE);
        this.setPadding(new Insets(0, 5, 10, 10));
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(5);
        this.getChildren().addAll(administrer, opretLager, sletLager, opretReol, sletReol, opretHylde, sletHylde, flytFad);

        opretLager.getButton().setOnAction(e -> håndterOpretLager());
        sletLager.getButton().setOnAction(e -> håndterSletLager());

        opretReol.getButton().setOnAction(e -> håndterOpretReol());
        sletReol.getButton().setOnAction(e -> håndterSletReol());

        opretHylde.getButton().setOnAction(e -> håndterOpretHylde());
        sletHylde.getButton().setOnAction(e -> håndterSletHylde());

        flytFad.getButton().setOnAction(e -> håndterFlytFad());
    }

    private void håndterFlytFad() {
        Object valgt = treeViewPane.getValgtObjekt();

        if (valgt instanceof Fad valgtFad) {
            FlytFadInputPane flytFadInputPane = new FlytFadInputPane(valgtFad);
            flytFadInputPane.setOnHidden(e -> treeViewPane.opbygTreeView());
            flytFadInputPane.show();
        } else {
            visDialog(
                    Alert.AlertType.WARNING,
                    "Forkert valg",
                    "Vælg et fad du vil flytte.");
        }
    }

    private void håndterOpretLager() {
        OpretLagerInputPane vindue = new OpretLagerInputPane();
        vindue.setOnHidden(e -> treeViewPane.opbygTreeView());
        vindue.show();
    }

    private void håndterSletLager() {
        Object valgt = treeViewPane.getValgtObjekt();
        if (valgt instanceof Lager valgtLager) {
            for (Reol reol : valgtLager.getReoler()) {
                for (HyldePlads hylde : reol.getHyldePladser()) {
                    if (!hylde.isPladsFri()) {
                        visDialog(
                                Alert.AlertType.ERROR,
                                "Kan ikke slette",
                                "Lageret indeholder mindst én hylde med fad og kan derfor ikke slettes.");
                        return;
                    }
                }
            }
            if (visBekræftDialog(
                    "Bekræft sletning",
                    "Er du sikker på, at du vil slette lageret '" + valgtLager.getNavn() + "'?")) {
                Controller.removeLager(valgtLager);
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Lager slettet",
                        "Lageret blev slettet.");
                treeViewPane.opbygTreeView();
            }
        } else {
            visDialog(Alert.AlertType.WARNING, "Forkert valg", "Vælg et lager for at slette det.");
        }
    }

    public void håndterOpretReol() {
        Object valgt = treeViewPane.getValgtObjekt();
        if (valgt instanceof Lager valgtLager) {
            if (valgtLager.getReoler().size() >= valgtLager.getMaxAntalReoler()) {
                visDialog(
                        Alert.AlertType.ERROR,
                        "Fejl",
                        "Der er ikke plads til flere reoler på dette lager.");
                return;
            }
            valgtLager.createReol();
            visDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Reol oprettet",
                    "Reolen blev oprettet som reol nr." + valgtLager.getReoler().size());

            treeViewPane.opbygTreeView();
        } else {
            visDialog(
                    Alert.AlertType.WARNING,
                    "Forkert valg",
                    "Vælg et lager for at oprette en reol.");
        }
    }

    private void håndterSletReol() {
        Object valgt = treeViewPane.getValgtObjekt();
        if (valgt instanceof Reol reol) {
            for (HyldePlads hyldePlads : reol.getHyldePladser()) {
                if (!hyldePlads.isPladsFri()) {
                    visDialog(
                            Alert.AlertType.ERROR,
                            "Kan ikke slette",
                            "Reolen indeholder mindst én hylde med fad, og kan derfor ikke slettes.");
                    return;
                }
            }
            Lager lager = reol.getLager();
            if (lager != null && visBekræftDialog(
                    "Bekræft sletning",
                    "Er du sikker på, at du vil slette reolen?")) {
                lager.removeReol(reol);
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Reol slettet",
                        "Reolen blev slettet.");
                treeViewPane.opbygTreeView();
            }
        } else {
            visDialog(
                    Alert.AlertType.WARNING,
                    "Forkert valg",
                    "Vælg en reol for at slette den.");
        }
    }

    public void håndterOpretHylde() {
        Object valgt = treeViewPane.getValgtObjekt();
        if (valgt instanceof Reol valgtReol) {
            valgtReol.createHyldePlads();
            visDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Hyldeplads oprettet",
                    "Hyldeplads blev oprettet som nr. " + valgtReol.getHyldePladser().size());
            treeViewPane.opbygTreeView();
        } else {
            visDialog(
                    Alert.AlertType.WARNING,
                    "Forkert valg",
                    "Vælg en reol for at oprette en hyldeplads.");
        }
    }

    private void håndterSletHylde() {
        Object valgt = treeViewPane.getValgtObjekt();
        if (valgt instanceof HyldePlads hylde) {
            if (!hylde.isPladsFri()) {
                visDialog(
                        Alert.AlertType.ERROR,
                        "Kan ikke slette",
                        "Hyldepladsen indeholder stadig fad og kan derfor ikke slettes.");
                return;
            }

            Reol reol = hylde.getReol();
            if (reol != null && visBekræftDialog(
                    "Bekræft sletning",
                    "Er du sikker på, at du vil slette hyldepladsen?")) {
                reol.removeHyldePlads(hylde);
                visDialog(
                        Alert.AlertType.CONFIRMATION,
                        "Hylde slettet",
                        "Hyldepladsen blev slettet.");
                treeViewPane.opbygTreeView();
            }
        } else {
            visDialog(
                    Alert.AlertType.WARNING,
                    "Forkert valg",
                    "Vælg en hyldeplads for at slette den.");
        }
    }
}
