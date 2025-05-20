package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledCheckBoxInput extends VBox {
    private final CheckBox checkBox = new CheckBox();

    public LabeledCheckBoxInput(String labelText, String checkBoxText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        checkBox.setText(checkBoxText);

        this.getChildren().addAll(label, checkBox);
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}
