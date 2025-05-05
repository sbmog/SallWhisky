package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HeaderLabel extends VBox {
    private final Label label;

    public HeaderLabel(String text) {
        this.setStyle("-fx-border-color: transparent; -fx-background-color: aliceblue; -fx-background-radius: 10;");
        this.setPadding(new Insets(10));
        this.setMinWidth(200);

        label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        this.getChildren().add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public Label getLabel() {
        return label;
    }
}
