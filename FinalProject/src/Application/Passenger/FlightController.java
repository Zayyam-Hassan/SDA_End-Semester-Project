package Application.Passenger;

import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Ticket;
import AirportManagement.FlightManagement.Seat;
public class FlightController {

    @FXML
    private Button CancelFlight;

    @FXML
    private Button CheckWaitingTime;

    @FXML
    private Button Flight;

    @FXML
    private Button Home;

    @FXML
    private Button Logout;

    @FXML
    private Button Notifications;

    @FXML
    private Button Ticket;

    @FXML
    private Button ViewBoardingPass;

    @FXML
    private Text departuretime;

    @FXML
    private Text destination;

    @FXML
    private Text estarrivaltime;

    @FXML
    private Text flightClass;

    @FXML
    private Text flightno;

    @FXML
    private Pane inner_pane;

    @FXML
    private Pane profile_image;

    @FXML
    private Text seatno;

    @FXML
    private Text ticketid;
    public void initialize() {
        Flight f = Airport.getInstance().getFlight() ;
        flightno.setText(f.getFlightnumber());
        Ticket t  = Airport.getInstance().getPassenger().getTicket() ;
        ticketid.setText(String.valueOf(t.getid()));
        seatno.setText(t.getSeat().getSeatnum());
        flightClass.setText(t.gettype());
        destination.setText(f.getArrivalcity());
        departuretime.setText(f.getDeparturetime().split(" ")[0]);
        estarrivaltime.setText(f.getArrivaltime());
    }

    @FXML
    void onClickCancelFlight(ActionEvent event) {
        Airport a = Airport.getInstance();
        Flight flight = a.getFlight();
        a.CancelReservation(flight.getid(), a.getPassenger().getTicket().getid());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Airport Management System");

    }

    @FXML
    void onClickCheckWaitingTime(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/waiting_times.fxml", "Airport Management System");
    }

    @FXML
    void onClickFlight(ActionEvent event) {

    }

    @FXML
    void onClickHome(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Airport Management System");
    }

    @FXML
    void onClickLogout(ActionEvent event) { 
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Loginpage.fxml", "Ticket");
    }

    @FXML
    void onClickNotifications(ActionEvent event) {
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/Notifications.fxml", "Notifications");
    }

    @FXML
    void onClickTicket(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Ticket.fxml", "Ticket");
    }

    @FXML
    void onClickViewBoardingPass(ActionEvent event) {
        Flight f = Airport.getInstance().getFlight();
        Ticket t = Airport.getInstance().getPassenger().getTicket();
        Airport.getInstance().generateBoardingPass(f.getid() , t.getid()); 
        ScreenFactory.getInstance().loadPopupScreen( "/Application/Passenger/BoardingPass.fxml", "Boarding Pass");
    }

}
