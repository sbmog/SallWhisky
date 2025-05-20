package gui.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class LabeledTreeViewInput<T> extends VBox {
    private final TreeView<T> treeView = new TreeView<>();

    public LabeledTreeViewInput(String labelText) {
        this.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1; -fx-background-color: aliceblue; -fx-background-radius: 10; -fx-border-radius: 10;");
        this.setPadding(new Insets(5));
        this.setMinWidth(200);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        this.getChildren().addAll(label, treeView);
    }

    public TreeView<T> getTreeView() {
        return treeView;
    }

    public void setRoot(TreeItem<T> root) {
        treeView.setRoot(root);
    }

    public void expandAll(TreeItem<T> item) {
        item.setExpanded(true);
        for (TreeItem<T> child : item.getChildren()) {
            expandAll(child);
        }
    }
}
