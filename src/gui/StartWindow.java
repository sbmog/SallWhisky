package gui;

import gui.component.HeaderLabel;
import gui.component.LabeledButton;
import gui.lagerAdm.LagerAdmOversigt;
import gui.opretDestillat.OpretDestillatPane;
import gui.opretFad.OpretFadPane;
import gui.opretMaltBatch.OpretMaltBatchPane;
import gui.opretPåfyldning.OpretPåfyldningPane;
import gui.opretTapning.OpretTapningPane;
import gui.produktionsAdm.ProduktionsAdmOversigt;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sall Whisky Distillery");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Scene scene = new Scene(pane, 700, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        HeaderLabel headLabel = new HeaderLabel("Sall Whisky Distillery");
        pane.add(headLabel, 0, 0, 3, 1);

        VBox opretObjekter = new VBox(5);
        opretObjekter.setPadding(new Insets(0, 5, 10, 10));
        opretObjekter.setAlignment(Pos.CENTER);

        HeaderLabel produktionsHeader = new HeaderLabel("Produktion");

        LabeledButton opretDestillat = new LabeledButton("Registrér ny destillation", "Start");
        opretDestillat.getButton().setOnAction(e -> opretDestillatButton());

        LabeledButton opretPåfyldning = new LabeledButton("Påfyldning af fad", "Fyld");
        opretPåfyldning.getButton().setOnAction(e -> opretPåfyldningButton());

        LabeledButton opretTapning = new LabeledButton("Tapning af fad", "Tap");
        opretTapning.getButton().setOnAction(e -> opretTapningButton());

        opretObjekter.getChildren().addAll(produktionsHeader, opretDestillat, opretPåfyldning, opretTapning);
        pane.add(opretObjekter, 0, 1);

        VBox registrerRåVarer = new VBox(5);
        registrerRåVarer.setPadding(new Insets(0, 5, 10, 10));
        registrerRåVarer.setAlignment(Pos.TOP_CENTER);

        HeaderLabel registrerHeader = new HeaderLabel("Registrer varer fra Leverandør");

        LabeledButton opretFad = new LabeledButton("Registrer nyt fad", "Start");
        opretFad.getButton().setOnAction(e -> opretFadButton());

        LabeledButton opretMaltBatch = new LabeledButton("Registrer ny maltbatch", "Start");
        opretMaltBatch.getButton().setOnAction(e -> opretMaltBatchButton());

        registrerRåVarer.getChildren().addAll(registrerHeader, opretFad, opretMaltBatch);
        pane.add(registrerRåVarer, 1, 1);

        VBox administrationsOversigt = new VBox(5);
        administrationsOversigt.setPadding(new Insets(0, 5, 10, 10));
        administrationsOversigt.setAlignment(Pos.TOP_CENTER);

        HeaderLabel oversigter = new HeaderLabel("Administrationsoversigter");

        LabeledButton produktionsAdm = new LabeledButton("General produktionsoversigt", "Oversigt");
        produktionsAdm.getButton().setOnAction(e -> produktionsAdmButton());

        LabeledButton lagerAdm = new LabeledButton("Lager administrationsoversigt", "Oversigt");
        lagerAdm.getButton().setOnAction(e -> lagerAdmButton());

        administrationsOversigt.getChildren().addAll(oversigter, produktionsAdm, lagerAdm);
        pane.add(administrationsOversigt, 2, 1);
    }

    private void opretMaltBatchButton() {
        OpretMaltBatchPane opretMaltBatchPane = new OpretMaltBatchPane();
        if (!opretMaltBatchPane.isShowing()) opretMaltBatchPane.showAndWait();
    }

    private void opretFadButton() {
        OpretFadPane opretFadPane = new OpretFadPane();
        if (!opretFadPane.isShowing()) opretFadPane.showAndWait();
    }

    private void lagerAdmButton() {
        LagerAdmOversigt lagerAdmOversigt = new LagerAdmOversigt();
        if (!lagerAdmOversigt.isShowing()) lagerAdmOversigt.showAndWait();
    }

    private void produktionsAdmButton() {
        ProduktionsAdmOversigt produktionsAdmOversigt = new ProduktionsAdmOversigt();
        if (!produktionsAdmOversigt.isShowing()) produktionsAdmOversigt.showAndWait();
    }

    private void opretTapningButton() {
        OpretTapningPane opretTapningPane = new OpretTapningPane();
        if (!opretTapningPane.isShowing()) opretTapningPane.showAndWait();
    }

    private void opretPåfyldningButton() {
        OpretPåfyldningPane opretPåfyldningPane = new OpretPåfyldningPane();
        if (!opretPåfyldningPane.isShowing()) opretPåfyldningPane.showAndWait();
    }

    private void opretDestillatButton() {
        OpretDestillatPane opretDestillatPane = new OpretDestillatPane();
        if (!opretDestillatPane.isShowing()) opretDestillatPane.showAndWait();
    }
}
