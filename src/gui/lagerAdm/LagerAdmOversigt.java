package gui.lagerAdm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LagerAdmOversigt extends Stage {

    LagerTreeViewPane treeViewPane = new LagerTreeViewPane();

    public LagerAdmOversigt() {

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.45);

        LagerInformationPane infoPane = new LagerInformationPane();
        LagerAdministrationPane adminPane = new LagerAdministrationPane(treeViewPane);

        treeViewPane.setOnSelectionChanged(obj -> {
            infoPane.updateInfo(obj);
        });

        HBox højreSide = new HBox(infoPane, adminPane);
        højreSide.setSpacing(10);
        højreSide.setPadding(new Insets(0,5,10,10));
        højreSide.setAlignment(Pos.TOP_CENTER);

        HBox.setHgrow(infoPane, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(adminPane, javafx.scene.layout.Priority.ALWAYS);

        splitPane.getItems().addAll(treeViewPane, højreSide);

        Scene scene = new Scene(splitPane, 900, 600);
        this.setScene(scene);
        this.setTitle("Lageradministration");
        this.show();

    }
}
