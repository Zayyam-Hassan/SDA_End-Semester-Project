package Application.Passenger;

import AirportManagement.Airport;
import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WaitingtimesController {

    @FXML
    private Button CheckInQueue;

    @FXML
    private Button Flight;

    @FXML
    private Button Home;

    @FXML
    private Button Logout;

    @FXML
    private Button LoungeStatus;

    @FXML
    private Button Notifications;

    @FXML
    private Button TerminalStatus;

    @FXML
    private Button Ticket;

    @FXML
    private Button ViewBaggage;

    @FXML
    private Button goBack;

    @FXML
    private Pane inner_pane;

    @FXML
    void onClickCheckInNow(ActionEvent event) {
        Airport.getInstance().OnlinecheckIn();
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/checkin_status.fxml", "Airport Management System");
    }

    @FXML
    void onClickFlight(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Flight.fxml", "Airport Management System");
    }

    @FXML
    void onClickHome(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Airport Management System");
    }

    @FXML
    void onClickLogout(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/LoginPage.fxml", "Airport Management System");
    }

    @FXML
    void onClickLoungeCheckNow(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Lounge_status.fxml", "Airport Management System");
    }

    @FXML
    void onClickNotifications(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Notifications.fxml", "Airport Management System");

    }

    @FXML
    void onClickTerminalCheckNow(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/terminal_status.fxml", "Airport Management System");

    }

    @FXML
    void onClickTicket(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Ticket.fxml", "Airport Management System");
    }

    @FXML
    void onClickViewBaggage(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/baggage_status.fxml", "Airport Management System");

    }

    @FXML
    void onClickgoBack(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Airport Management System");
    }

}
