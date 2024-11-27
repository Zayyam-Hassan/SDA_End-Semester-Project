package Application.Passenger;

import java.io.IOException;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.Airport;
import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserProfileController {

    @FXML
    private Button BookNow;

    @FXML
    private Button Contactus;

    @FXML
    private Button Flight;

    @FXML
    private Button Home;

    @FXML
    private Button Logout;

    @FXML
    private Button Notifications;

    @FXML
    private Button NotifyMe;

    @FXML
    private Button Ticket;

    @FXML
    private Button UpdateProfile;

    @FXML
    private Pane inner_pane;

    @FXML
    private Pane profile_image;
    @FXML
    public void initialize() {
        Flight flight = Airport.getInstance().getFlight();
        if (flight == null) {
            Flight.setDisable(true);
        }
    }

    @FXML
    void onClickBookNow(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Ticket.fxml", "Ticket");
    }
    @FXML
    void onClickContactus(ActionEvent event) {

    }

    @FXML
    void onClickFlight(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Flight.fxml", "Flight Details");
    }

    @FXML
    void onClickHome(ActionEvent event) {

    }

    @FXML
    void onClickLogout(ActionEvent event) {
        Airport.getInstance().Logout();;
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Loginpage.fxml", "Login In");
    }

    @FXML
    void onClickNotifications(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Notifications.fxml", "Notifications");
    }

    @FXML
    void onClickNotifyme(ActionEvent event) {

    }

    @FXML
    void onClickTicket(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Ticket.fxml", "Ticket");
    }

    @FXML
    void onClickUpdateDetails(ActionEvent event) {

    }

}
