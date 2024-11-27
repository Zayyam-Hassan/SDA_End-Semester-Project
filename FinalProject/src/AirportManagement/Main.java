package AirportManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    Airport airport ; 
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an Airport object
        // Load the FXML file
        Airport airport = Airport.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/Passenger/Loginpage.fxml"));
        Parent root = loader.load();

        // Set the scene
        Scene scene = new Scene(root);

        // Set stage properties
        primaryStage.setTitle("Airport Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        launch(args);
    }
}
