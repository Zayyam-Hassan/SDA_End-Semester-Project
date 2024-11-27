package Application.Passenger;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import AirportManagement.*;
import AirportManagement.FlightManagement.*;;
public class BoardingPassController {

    @FXML
    private Text flightno;

    @FXML
    private Text from;

    @FXML
    private Text fromdate;

    @FXML
    private Text fromtime;

    @FXML
    private Text gate;

    @FXML
    private Text passenger;

    @FXML
    private Text seat;

    @FXML
    private Text terminal;

    @FXML
    private Text to;

    @FXML
    private Text todate;

    @FXML
    private Text totime;
    public void initialize() {
        Airport a = Airport.getInstance();
        Flight f = a.getFlight();
        String [] time =f.getArrivaltime().split(" ");
        todate.setText(time[0]);
        totime.setText(time[1]);
        to.setText(f.getArrivalcity());
        time = f.getDeparturetime().split(" ");
        fromdate.setText(time[0]);
        fromtime.setText(time[1]);
        from.setText(f.getDeparturecity());
        flightno.setText(f.getFlightnumber());
        passenger.setText(a.getPassenger().getProfile().getName());
        seat.setText(a.getPassenger().getTicket().getSeat().getSeatnum());
        gate.setText(String.valueOf(f.getgate().getgateid()));
        terminal.setText(String.valueOf(f.getgate().getterminalid()));
    }

}
