package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TextInputWithButton extends VBox {

    private final TextField textField = new TextField();
    private final Button button;

    public TextInputWithButton(String labelText, String buttonText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        button = new Button(buttonText);

        HBox feltBox = new HBox(5, textField, button);
        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(feltBox, Priority.ALWAYS);
        feltBox.setMaxWidth(Double.MAX_VALUE);

        this.getChildren().addAll(label, feltBox);
    }

    public String getInputValue() {
        return textField.getText().trim();
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getButton() {
        return button;
    }
}
