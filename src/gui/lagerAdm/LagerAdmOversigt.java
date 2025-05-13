package gui.lagerAdm;

import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LagerAdmOversigt extends Stage {

    private final LagerTreeViewPane treeViewPane = new LagerTreeViewPane();
    private final LabeledTextInput søgFad = new LabeledTextInput("Søg fad med fad ID");

    public LagerAdmOversigt() {

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.45);

        LagerInformationPane infoPane = new LagerInformationPane();
        LagerAdministrationPane adminPane = new LagerAdministrationPane(treeViewPane);

        treeViewPane.setOnSelectionChanged(obj -> infoPane.updateInfo(obj));

        VBox venstreSide = new VBox(5, treeViewPane, søgFad);
        venstreSide.setAlignment(Pos.TOP_CENTER);
        venstreSide.setPadding(new Insets(0,5,10,10));

        HBox højreSide = new HBox(5, infoPane, adminPane);
        højreSide.setPadding(new Insets(0, 5, 10, 10));
        højreSide.setAlignment(Pos.TOP_CENTER);

        HBox.setHgrow(infoPane, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(adminPane, javafx.scene.layout.Priority.ALWAYS);

        splitPane.getItems().addAll(venstreSide, højreSide);

        Scene scene = new Scene(splitPane, 900, 600);
        this.setScene(scene);
        this.setTitle("Lageradministration");
        this.show();

        søgFad.getTextField().setOnAction(e -> søgning());
    }

    private void søgning() {
//        TODO
    }
}
