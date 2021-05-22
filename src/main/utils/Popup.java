package main.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * This class contains the functionality to display dialog popups to the user.
 */
public class Popup {

    private static final String[] errorTitles = {"Invalid Login", "No Item Selected", "Invalid Input",
            "Appointment Conflict", "Identifiant invalide"};
    private static final String[] errorMessages = {
            "The username and/or password provided was incorrect.",
            "No item was selected. Please select an item from the table\n and try again.",
            "The input is not valid. All items need to be filled in.",
            "The time of this appointment conflicts with an \nexisting appointment for this customer or the \nappointment is starting before the current time.",
            "Le nom d'utilisateur et / ou le mot de passe fournis \nétaient incorrects."
    };

    /**
     * This method will display a confirmation dialog.
     * @param itemType The type of item being altered (e.g. customers, appointments)
     * @param action The action being taken on the item (e.g. delete, update)
     * @return Returns true if the user confirms their action. Returns false otherwise.
     */
    public static boolean displayConfirmation(String itemType, String action) {
        boolean confirmation;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(action + " " + itemType);
        alert.setContentText("Are you sure you want to " + action.toLowerCase() + " this " + itemType.toLowerCase() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        confirmation = result.get() == ButtonType.OK;
        alert.close();
        return confirmation;
    }

    /**
     * This method will display an error dialog.
     * @param idx The index of the array of possible messages
     */
    public static void displayError(int idx) {
        String[] errorText = {errorTitles[idx], errorMessages[idx]};
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(errorText[0]);
        alert.setContentText(errorText[1]);
        if (idx == 4) {
            alert.setTitle("Boîte de dialogue d'erreur");
        }
        alert.showAndWait();
    }

    /**
     * This method will display a information dialog.
     * @param itemType The type of item being altered (e.g. customers, appointments)
     * @param action The action being taken on the item (e.g. delete, update)
     */
    public static void displayInformation(String itemType, String action) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Successful " + action + " of " + itemType);
        alert.setContentText("The " + action.toLowerCase() + " of " + itemType.toLowerCase() +
                " was a success.");
        alert.showAndWait();
    }

    /**
     * This method will display a information dialog for deleting an appointment.
     * @param id The id of the deleted appointment
     * @param type The type of the deleted appointment
     */
    public static void displayInformation(int id, String type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Successful Delete of Appointment");
        alert.setContentText("The cancellation  of appointment (ID: " + id + ", Type: " + type.toLowerCase() + ")\n" +
                " was a success.");
        alert.showAndWait();
    }
}
