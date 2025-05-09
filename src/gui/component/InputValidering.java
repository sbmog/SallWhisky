package gui.component;

import javafx.scene.control.Alert;

import static gui.component.AlertTypes.visDialog;

public class InputValidering {

    private boolean valid = true;

    public boolean isValid() {
        return valid;
    }

    public InputValidering validateNotEmpty(LabeledTextInput input, String errorMessage) {
        if (input.getInputValue().isEmpty()) {
            showAlert("Fejl", errorMessage);
            valid = false;
        }
        return this;
    }

    public InputValidering validateInteger(LabeledTextInput input, String fejlbesked) {
        try {
            Integer.parseInt(input.getInputValue());
        } catch (NumberFormatException e) {
            showAlert("Fejl", fejlbesked);
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

    public InputValidering validateDateOrder(LabeledDateInput start, LabeledDateInput end, String errorMessage) {
        if (start.getInputValue() == null || end.getInputValue() == null || end.getInputValue().isBefore(start.getInputValue())) {
            showAlert("Fejl", errorMessage);
            valid = false;
        }
        return this;
    }

    public InputValidering validateIfSelected(LabeledCheckBoxInput<?> checkBox, Runnable validationLogic) {
        if (checkBox.getCheckBox().isSelected()) {
            validationLogic.run();
        }
        return this;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
