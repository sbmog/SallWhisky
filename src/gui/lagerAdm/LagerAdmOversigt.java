package gui.lagerAdm;

import application.model.HyldePlads;
import application.model.Lager;
import application.model.Reol;
import gui.component.AttributeDisplay;
import gui.component.HeaderLabel;
import gui.component.LabeledButton;
import gui.component.LabeledTreeViewInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storage.Storage;

public class LagerAdmOversigt extends Stage {

    LagerTreeViewPane treeViewPane = new LagerTreeViewPane();

    public LagerAdmOversigt() {

        SplitPane splitPane = new SplitPane();

        LagerInformationPane infoPane = new LagerInformationPane();
        LagerAdministrationPane adminPane = new LagerAdministrationPane(treeViewPane);

        treeViewPane.setOnSelectionChanged(obj -> {
            infoPane.updateInfo(obj);
            adminPane.opdaterKnapper(obj);
        });

        VBox højreSide = new VBox(infoPane, adminPane);
        splitPane.getItems().addAll(treeViewPane, højreSide);

        Scene scene = new Scene(splitPane, 900, 600);
        this.setScene(scene);
        this.setTitle("Lageradministration");
        this.show();

    }


    //  Venstresplit
    private final LabeledTreeViewInput<Object> treeViewInput = new LabeledTreeViewInput<>("Lageroversigt");

    //  Højresplit - venstre
    private final HeaderLabel information = new HeaderLabel("Information");
    private final AttributeDisplay lagerNavn = new AttributeDisplay("Lager navn", "");
    private final AttributeDisplay lagerAdresse = new AttributeDisplay("Lager adresse", "");
    private final AttributeDisplay antalFadPåLager = new AttributeDisplay("Antal fad på lager", "");
    private final AttributeDisplay reolID = new AttributeDisplay("Reol ID", "");
    private final AttributeDisplay hyldeID = new AttributeDisplay("Hylde plads ID", "");
    private final AttributeDisplay fadID = new AttributeDisplay("Fad ID", "");
    private final AttributeDisplay fadIndhold = new AttributeDisplay("Fadets indhold", "");

    //    Højresplit - højre
    private final HeaderLabel administrer = new HeaderLabel("Administrer");
    private final LabeledButton opretLager = new LabeledButton("Opret nyt lager", "Opret");
    private final LabeledButton sletLager = new LabeledButton("Slet valgte lager", "Slet");
    private final LabeledButton opretReol = new LabeledButton("Opret reol på valgte lager", "Opret");
    private final LabeledButton sletReol = new LabeledButton("Slet valgte reol", "Slet");
    private final LabeledButton opretHylde = new LabeledButton("Opret hyldeplads på valgte reol", "Opret");
    private final LabeledButton sletHylde = new LabeledButton("Slet valgte hyldeplads", "Slet");
    private final LabeledButton flytFad = new LabeledButton("Flyt fad til ny hyldeplads", "Flyt fad");

    public LagerAdmOversigt() {
        this.setTitle("Lager administrationsoversigt");

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.40);

        Scene scene = new Scene(splitPane, 800, 600);
        this.setScene(scene);
        this.show();

        // Venstre
        TreeItem<Object> rootItem = new TreeItem<>("Lagre");
        treeViewInput.setRoot(rootItem);

        for (Lager lager : Storage.getLagre()) {
            TreeItem<Object> lagerItem = new TreeItem<>(lager);
            rootItem.getChildren().add(lagerItem);

            for (Reol reol : lager.getReoler()) {
                TreeItem<Object> reolItem = new TreeItem<>("Reol " + reol);
                lagerItem.getChildren().add(reolItem);

                for (HyldePlads hylde : reol.getHyldePladser()) {
                    TreeItem<Object> hyldeItem = new TreeItem<>("Hylde " + hylde);
                    reolItem.getChildren().add(hyldeItem);

                    if (!hylde.isPladsFri()) {
                        TreeItem<Object> fadItem = new TreeItem<>("Fad #" + hylde.getFadPlaceret().getFad());
                        hyldeItem.getChildren().add(fadItem);

                    }
                }
            }
        }

        treeViewInput.expandAll(rootItem);

        // Højre
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setGridLinesVisible(true);

        HeaderLabel headerLabelHøjre = new HeaderLabel("Oversigt");
        gridPane.add(headerLabelHøjre, 0, 0, 2, 1);

        VBox venstreBox = new VBox(10);
        venstreBox.setPadding(new Insets(0, 5, 10, 10));
        venstreBox.setAlignment(Pos.TOP_CENTER);
        gridPane.add(venstreBox, 0, 1);
        venstreBox.getChildren().addAll(information, lagerNavn, lagerAdresse, antalFadPåLager, reolID, hyldeID, fadID, fadIndhold);

        VBox højreBox = new VBox(10);
        højreBox.setPadding(new Insets(0, 5, 10, 10));
        højreBox.setAlignment(Pos.TOP_CENTER);
        gridPane.add(højreBox, 1, 1);
        højreBox.getChildren().addAll(administrer, opretLager, sletLager, opretReol, sletReol, opretHylde, sletHylde, flytFad);

        splitPane.getItems().addAll(treeViewInput, gridPane);
    }
}
