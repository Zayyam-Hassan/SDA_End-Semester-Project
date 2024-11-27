package Application.Passenger;

import DataBase.AirportDBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AirportManagement.Airport;
import Application.Utility.*;
public class LoginpageController {

    @FXML
    private Button loginbutton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernamefield;

    @FXML
    void onClickLogin(ActionEvent event) {
        String username = usernamefield.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "Login Failed", "Please fill in both username and password.");
            usernamefield.clear();
            passwordField.clear();
            return;
        }
        String[] type = new String[1];
        type[0] = "";
        int[] id = new int[1];
        id[0] = -1;
        System.out.println(username + " " + password);
        Airport.getInstance().login(username, password, type , id);
        System.out.println(id[0] + " " + type[0]);
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        if (id[0] == -1) {
            ScreenFactory.showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
        }
        else if (type[0].equals("Passenger")) {
            ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Passenger Profile");

        }
        else if (type[0].equals("Staff")){
            ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Admin/AdminProfile.fxml", "Admin Profile");

        }
    }
    @FXML
     void onClickSignUp(ActionEvent event) {
        // Use ScreenFactory to load the SignUp screen
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/SignupPage.fxml", "Sign Up");
    }

}