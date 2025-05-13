package gui.produktionsAdm;

import application.model.Fad;
import application.model.Malt;
import application.model.Whisky;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WhiskyHistorikPane extends Stage {

    public WhiskyHistorikPane(Whisky whisky) {
        this.setTitle("Whisky Historik");

        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 400);
        this.setScene(scene);

        // Saml hele historikken som én streng
        String historik = whisky.getHistorik() + "\nFade:\n";

        // Tilføj fade til historik
        for (int i = 0; i < whisky.getTapninger().size(); i++) {
            Fad fad = whisky.getTapninger().get(i).getFad();
            historik += "- ID: " + fad.getFadID() + ", " + fad.getFadType() + ", " + fad.getMateriale() + "\n";
        }
        // Tilføj malt til historik
        historik += "Malt:\n";
        for (int i = 0; i < whisky.getTapninger().size(); i++) {
            var malts = whisky.getTapninger().get(i).getFad().getPåfyldning().getDestillat().getMaltBatch().getMalt();
            for (int j = 0; j < malts.size(); j++) {
                Malt malt = malts.get(j);
                historik += "- " + malt.toString() + "\n";
            }
        }

        // Opret en TextArea til at vise historikken
        TextArea textArea = new TextArea(historik);
        textArea.setPrefWidth(380); // Sæt bredden
        textArea.setPrefHeight(380); // Sæt højden
        textArea.setEditable(false); // Gør den skrivebeskyttet
        textArea.setWrapText(true); // Tillad tekstombrydning

        // Tilføj TextArea til layoutet
        root.getChildren().add(textArea);
    }
}
