package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LabeledTextInput extends VBox {
    TextField textField = new TextField();

    public LabeledTextInput(String labelText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        this.getChildren().addAll(label, textField);
    }

    public String getInputValue() {
        return textField.getText().trim();
    }

    public TextField getTextField() {
        return textField;
    }
}
