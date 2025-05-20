package gui.produktionsAdm;

import application.model.Whisky;
import gui.component.LabeledButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class WhiskyHistorikPane extends Stage {

    public WhiskyHistorikPane(Whisky whisky) {
        this.setTitle("Whisky Historik");

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 400);
        this.setScene(scene);

        // Saml hele historikken som én streng
        String historik = whisky.getHistorik();

        TextArea textArea = new TextArea(historik);
        textArea.setPrefWidth(380);
        textArea.setPrefHeight(380);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        LabeledButton labledbutton = new LabeledButton("Print", "Print");
        labledbutton.getButton().setOnAction(e -> {
            try (FileWriter fileWriter = new FileWriter("Whisky_historik.txt")) {
                fileWriter.write(historik);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(textArea,labledbutton);
    }
}