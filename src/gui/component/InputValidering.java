package gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.util.Optional;

public class InputValidering {

    private boolean valid = true;

    public boolean isValid() {
        return valid;
    }

    public InputValidering validateListViewSelection(ListView<?> listView, String errorMessage) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            visDialog(Alert.AlertType.ERROR, "Fejl", errorMessage);
            valid = false;
        }
        return this;
    }

    public InputValidering validateNotEmpty(LabeledTextInput input, String errorMessage) {
        if (input.getInputValue().isEmpty()) {
            visDialog(Alert.AlertType.ERROR,"Fejl", errorMessage);
            valid = false;
        }
        return this;
    }

    public InputValidering validateInteger(LabeledTextInput input, String fejlbesked) {
        try {
            Integer.parseInt(input.getInputValue());
        } catch (NumberFormatException e) {
            visDialog(Alert.AlertType.ERROR,"Fejl", fejlbesked);
            valid = false;
        }
        return this;
    }

    public InputValidering validateSelected(LabeledComboBoxInput<?> comboBox, String errorMessage) {
        if (comboBox.getComboBox().getSelectionModel() == null) {
            visDialog(Alert.AlertType.ERROR, "Fejl", errorMessage);
            valid = false;
        }
        return this;
    }

    public static void visDialog(Alert.AlertType type, String titel, String besked) {
        Alert alert = new Alert(type);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(besked);
        alert.showAndWait();
    }

    public static boolean visBekræftDialog(String titel, String besked) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(besked);

        Optional<ButtonType> resultat = alert.showAndWait();
        return resultat.isPresent() && resultat.get() == ButtonType.OK;
    }
}
