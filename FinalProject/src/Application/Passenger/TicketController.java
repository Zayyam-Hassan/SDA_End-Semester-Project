package Application.Passenger;

import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import AirportManagement.Airport;
public class TicketController {

    @FXML
    private Button BuyTicket;

    @FXML
    private Button Flight;

    @FXML
    private Button Home;

    @FXML
    private Button Logout;

    @FXML
    private Button Notifications;

    @FXML
    private Button ReserveSeat;

    @FXML
    private Button Ticket;

    @FXML
    private Button TicketDetails;

    @FXML
    private Pane inner_pane;

    @FXML
    private Pane profile_image;
    public void initialize() {
        Flight flight = Airport.getInstance().getFlight();
        if (flight == null) {
            Flight.setDisable(true);
            ReserveSeat.setDisable(true);
            TicketDetails.setDisable(true);
        }
        if (Airport.getInstance().getPassenger().getTicket() != null) {
            if(Airport.getInstance().getPassenger().getTicket().getpayment().getIsPending() == false)
                ReserveSeat.setDisable(true);
        }
    }


    @FXML
    void onClickBuyTicket(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/allflights.fxml", "Airport Management System");
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
    void onClickNotifications(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Notifications.fxml", "Airport Management System");
    }

    @FXML
    void onClickReserveSeat(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/reserveticket.fxml", "Airport Management System");
    }

    @FXML
    void onClickTicket(ActionEvent event) {

    }

    @FXML
    void onClickTicketDetails(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Ticketdetails.fxml", "Airport Management System");
    }

}
