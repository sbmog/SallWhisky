package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TextInputWithListViewInput<T> extends VBox {

    private final ListView<T> listView = new ListView<>();
    private final TextField textField = new TextField();

    public TextInputWithListViewInput(String labelText, String promptText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        textField.setPromptText(promptText);
        this.setSpacing(2);

        this.getChildren().addAll(label, textField, listView);
    }

    public ListView<T> getListView() {
        return listView;
    }

    public TextField getTextField() {
        return textField;
    }
}
