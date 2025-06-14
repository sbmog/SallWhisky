package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AttributeDisplay extends VBox {
    private final Label valueLabel;
    private final Label headerLabel;

    public AttributeDisplay(String labelText, String value) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        headerLabel = new Label(labelText);
        headerLabel.setStyle("-fx-font-weight: bold;");

        valueLabel = new Label(value);

        this.getChildren().addAll(headerLabel, valueLabel);
    }

    public void setValue(String value) {
        this.valueLabel.setText(value);
    }

    public Label getHeaderLabel() {
        return headerLabel;
    }
}
