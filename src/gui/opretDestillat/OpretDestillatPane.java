package gui.opretDestillat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OpretDestillatPane extends Stage {
    public OpretDestillatPane(){
        this.setTitle("Registrér ny destillation");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(10));
        pane.setVgap(2);
        pane.setHgap(2);

        Scene scene = new Scene(pane, 500, 660);
        this.setScene(scene);
        this.show();
    }
}
