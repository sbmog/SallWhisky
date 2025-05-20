package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class LabeledComboBoxInput<T> extends VBox {

    private final ComboBox<T> comboBox = new ComboBox<>();

    public LabeledComboBoxInput(String labelText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        this.getChildren().addAll(label, comboBox);
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public T getSelectedValue() {
        return comboBox.getValue();
    }

    public void addItems(T... items) {
        comboBox.getItems().addAll(items);
    }

    public void addItems(List<T> items) {
        comboBox.getItems().addAll(items);
    }
}
