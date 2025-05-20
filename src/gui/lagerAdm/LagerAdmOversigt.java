package gui.lagerAdm;

import application.controller.Controller;
import application.model.Fad;
import gui.component.TextInputWithButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LagerAdmOversigt extends Stage {

    private final LagerTreeViewPane treeViewPane = new LagerTreeViewPane();
    private final TextInputWithButton søgFad = new TextInputWithButton("Søg fad", "Ryd");

    private final Label beskedLabel = new Label();

    public LagerAdmOversigt() {

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.45);

        LagerInformationPane infoPane = new LagerInformationPane();
        LagerAdministrationPane adminPane = new LagerAdministrationPane(treeViewPane);

        treeViewPane.setOnSelectionChanged(obj -> infoPane.updateInfo(obj));

        beskedLabel.setStyle("-fx-text-fill: red;");

        VBox venstreSide = new VBox(5, treeViewPane, søgFad, beskedLabel);
        venstreSide.setAlignment(Pos.TOP_CENTER);
        venstreSide.setPadding(new Insets(0, 5, 10, 10));

        HBox højreSide = new HBox(5, infoPane, adminPane);
        højreSide.setPadding(new Insets(0, 5, 10, 10));
        højreSide.setAlignment(Pos.TOP_CENTER);

        HBox.setHgrow(infoPane, Priority.ALWAYS);
        HBox.setHgrow(adminPane, Priority.ALWAYS);

        splitPane.getItems().addAll(venstreSide, højreSide);

        Scene scene = new Scene(splitPane, 900, 600);
        this.setScene(scene);
        this.setTitle("Lageradministration");
        this.show();

        søgFad.getTextField().textProperty().addListener((obs, oldVal, newVal) -> søgning(newVal));
        søgFad.getButton().setOnAction(e -> {
            søgFad.getTextField().clear();
            treeViewPane.opbygTreeView();
        });
    }

    private void søgning(String input) {
        String søgeTekst = input.toLowerCase().trim();

        if (søgeTekst.isEmpty()) {
            treeViewPane.opbygTreeView();
            beskedLabel.setText("");
        } else {
            List<Fad> søgeFade = Controller.søgFade(søgeTekst);
            if (søgeFade.isEmpty()) {
                treeViewPane.visFadeSøgning(søgeFade);
                beskedLabel.setText("Ingen fade matcher søgningen.");
            } else {
                treeViewPane.visFadeSøgning(søgeFade);
                beskedLabel.setText("Viser " + søgeFade.size() + " resultater.");
            }
        }
    }
}
