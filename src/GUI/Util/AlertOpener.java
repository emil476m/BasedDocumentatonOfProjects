package GUI.Util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertOpener {
    /**
     * Opens a modal to confirm that the user wants to delete something.
     * @param header The header text to display.
     * @param content The content text to display.
     * @return true if the user clicked "ok", otherwise false.
     */
    public static boolean confirm(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

    /**
     * Opens a modal to display a validation error.
     * @param text The text that displays what input was invalid.
     */
    public static void validationError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(text);

        alert.showAndWait();
    }
}