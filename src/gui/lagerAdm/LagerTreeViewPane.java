package gui.lagerAdm;

import application.controller.Controller;
import application.model.HyldePlads;
import application.model.Lager;
import application.model.Reol;
import gui.component.LabeledTreeViewInput;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class LagerTreeViewPane extends VBox {
    private final LabeledTreeViewInput<Object> treeViewInput = new LabeledTreeViewInput<>("Lageroversigt");
    private Consumer<Object> selectionCallback;


    public LagerTreeViewPane() {
        this.getChildren().add(treeViewInput);
        this.setSpacing(5);
        opbygTreeView();

        treeViewInput.getTreeView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            TreeItem<Object> selectedItem = treeViewInput.getTreeView().getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectionCallback != null) {
                selectionCallback.accept(selectedItem.getValue());
            }
        });
    }

    public void opbygTreeView() {
        TreeItem<Object> rootItem = new TreeItem<>("Lagre");

        for (Lager lager : Controller.getLagre()) {
            TreeItem<Object> lagerItem = new TreeItem<>(lager);
            rootItem.getChildren().add(lagerItem);

            for (Reol reol : lager.getReoler()) {
                TreeItem<Object> reolItem = new TreeItem<>(reol);
                lagerItem.getChildren().add(reolItem);

                for (HyldePlads hylde : reol.getHyldePladser()) {
                    TreeItem<Object> hyldeItem = new TreeItem<>(hylde);
                    reolItem.getChildren().add(hyldeItem);

                    if (!hylde.isPladsFri()) {
                        TreeItem<Object> fadItem = new TreeItem<>(hylde.getFadPlaceret().getFad());
                        hyldeItem.getChildren().add(fadItem);
                    }
                }
            }
        }

        treeViewInput.setRoot(rootItem);
        treeViewInput.expandAll(rootItem);
    }

    public void setOnSelectionChanged(Consumer<Object> callback) {
        this.selectionCallback = callback;
    }

    public Object getValgtObjekt() {
        TreeItem<Object> selectedItem = treeViewInput.getTreeView().getSelectionModel().getSelectedItem();
        return selectedItem != null ? selectedItem.getValue() : null;
    }
}
