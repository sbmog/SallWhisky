package gui.produktionsAdm;

import gui.produktionsAdm.tabPanes.DestillatTab;
import gui.produktionsAdm.tabPanes.FadTab;
import gui.produktionsAdm.tabPanes.MaltBatchTab;
import gui.produktionsAdm.tabPanes.WhiskyTab;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class ProduktionsAdmOversigt extends Stage {
    public ProduktionsAdmOversigt() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().add(new Tab("MaltBatch", new MaltBatchTab()));
        tabPane.getTabs().add(new Tab("Destillat", new DestillatTab()));
        tabPane.getTabs().add(new Tab("Fad", new FadTab()));
        tabPane.getTabs().add(new Tab("Whisky", new WhiskyTab()));

        Scene scene = new Scene(tabPane, 600, 655);
        this.setScene(scene);
        this.setTitle("Produktions oversigt");
    }
}
