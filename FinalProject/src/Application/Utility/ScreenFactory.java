package Application.Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ScreenFactory {

    private static ScreenFactory instance;

    private ScreenFactory() {
        // Private constructor to prevent instantiation
    }

    public static ScreenFactory getInstance() {
        if (instance == null) {
            instance = new ScreenFactory();
        }
        return instance;
    }

    public void loadScreen(Stage currentStage, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);  // Optional: Set the header text (null to hide it)
        alert.setContentText(content);
        alert.showAndWait();  // Show the alert and wait for the user to close it
    }
     public void loadPopupScreen(String fxmlFile, String title) {
        try {
            // Load the FXML file for the popup screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Create a new Stage for the popup screen
            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            popupStage.setScene(new Scene(root));

            // Make the popup modal (blocking interaction with the underlying window)
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Optional: Style the popup screen (e.g., undecorated for a cleaner look)
            popupStage.showAndWait(); // Wait until this popup is closed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
