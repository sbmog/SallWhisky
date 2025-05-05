package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledButton extends VBox {
    private final Button button;

    public LabeledButton(String labelText, String buttonText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        button = new Button(buttonText);
        button.setMaxWidth(Double.MAX_VALUE); // Knappen fylder hele bredden

        this.getChildren().addAll(label, button);
    }

    public Button getButton() {
        return button;
    }
}
