package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        pane.setGridLinesVisible(true);

        Scene scene = new Scene(pane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        Label headLabel = new Label("Sall Whisky Distillery");
        headLabel.setStyle("-fx-font-weight: bold;");
        pane.add(headLabel,0,0);

        VBox opretObjekter = new VBox(5);
        opretObjekter.setPadding(new Insets(0,5,10,10));

        Button opretDestillat = new Button("Start Destillats produktion");

    }
}
