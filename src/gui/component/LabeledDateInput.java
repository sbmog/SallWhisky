package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class LabeledDateInput extends VBox {
    DatePicker datePicker = new DatePicker();
    public LabeledDateInput(String labelText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        this.getChildren().addAll(label, datePicker);
    }

    public LocalDate getInputValue() {
        return datePicker.getValue();
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
