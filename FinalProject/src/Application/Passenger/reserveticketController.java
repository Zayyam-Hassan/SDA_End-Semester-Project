package Application.Passenger;

import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class reserveticketController {

    @FXML
    private Button CancelReservation;

    @FXML
    private Text Class;

    @FXML
    private Button ConfirmPayment;

    @FXML
    private Text Date;

    @FXML
    private Text DepartureTime;

    @FXML
    private Text Flight;

    @FXML
    private Text Flightno;

    @FXML
    private Text From;

    @FXML
    private Text Fullname;

    @FXML
    private Text Seat;

    @FXML
    private Text To;
    public void initialize() {
        Airport a = Airport.getInstance();
        Flight f = a.getFlight();
        Ticket t = a.getPassenger().getTicket();
        Class.setText(t.gettype());
        Date.setText(f.getDeparturetime().split(" ")[0]);
        DepartureTime.setText(f.getDeparturetime().split(" ")[1]);
        Flightno.setText(f.getFlightnumber());
        From.setText(f.getDeparturecity());
        To.setText(f.getArrivalcity());
        Seat.setText(t.getSeat().getSeatnum());
        Fullname.setText(a.getPassenger().getProfile().getName());

    }

    @FXML
    void onClickCancelReservation(ActionEvent event) {
        Airport a = Airport.getInstance();
        Flight f = a.getFlight();
        Ticket t = a.getPassenger().getTicket();
        a.CancelReservation(f.getid(), t.getid());
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void onClickConfirmPayment(ActionEvent event) {
        Airport a = Airport.getInstance();
        Flight f = a.getFlight();
        Ticket t = a.getPassenger().getTicket();
        a.Makepayment(f.getid(), t.getid());
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
