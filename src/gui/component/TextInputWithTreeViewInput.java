package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class TextInputWithTreeViewInput<T> extends VBox {
    private final TreeView<T> treeView = new TreeView<>();
    private final TextField textField = new TextField();

    public TextInputWithTreeViewInput(String labelText, String promptText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");

        textField.setPromptText(promptText);
        this.setSpacing(2);

        this.getChildren().addAll(label, textField, treeView);
    }

    public String getTextInputValue() {
        return textField.getText().trim();
    }

    public TextField getTextField() {
        return textField;
    }

    public void setRoot(TreeItem<T> root) {
        treeView.setRoot(root);
    }

    public TreeItem<T> getSelectedItem() {
        return treeView.getSelectionModel().getSelectedItem();
    }

    public void expandAll(TreeItem<T> item) {
        item.setExpanded(true);
        for (TreeItem<T> child : item.getChildren()) {
            expandAll(child);
        }
    }
}
