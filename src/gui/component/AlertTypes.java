package gui.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertTypes {

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
