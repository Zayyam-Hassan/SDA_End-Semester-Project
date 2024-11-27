package Application.Admin;

import org.w3c.dom.Node;

import AirportManagement.Airport;
import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminProfileController {

    @FXML
    private Button AddEmergency;

    @FXML
    private Button EmergencySituations;

    @FXML
    private Button FlightDetails;

    @FXML
    private Button Home;

    @FXML
    private Button Logout;

    @FXML
    private Button Notifications;

    @FXML
    private Button PassengerInfo;

    @FXML
    private Button ScheduleFlight;

    @FXML
    private Pane inner_pane;

    @FXML
    private Pane profile_image;

    @FXML
    void onClickAddEmergency(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Admin/AddEmergency.fxml", "Add Emergency");
    }

    @FXML
    void onClickEmergencySituations(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Admin/NotifyAuthorities.fxml", "Emergency Situations");
    }

    @FXML
    void onClickFlightDetails(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Admin/viewflights.fxml", "View Flights");
        
    }

    @FXML
    void onClickHome(ActionEvent event) {

    }

    @FXML
    void onClickLogout(ActionEvent event) {
        Airport.getInstance().Logout();
        Stage currentStage = (Stage) ( (javafx.scene.Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Loginpage.fxml", "Login In");
    }

    @FXML
    void onClickNotifications(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Notifications.fxml", "Notifications");
    }

    @FXML
    void onClickPassengerInfo(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Admin/passengerdetails.fxml", "Passenger Information");
    }

    @FXML
    void onClickScheduleFlight(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Admin/scheduleflight.fxml", "Schedule Flight");
    }

}
