package Application.Passenger;

import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TicketdetailsController {

    @FXML
    private Text date;

    @FXML
    private Text departuretime;

    @FXML
    private Text flightclass;

    @FXML
    private Text flightno;

    @FXML
    private Text from;

    @FXML
    private Text passengername;

    @FXML
    private Text seatno;

    @FXML
    private Text to;
    @FXML
    public void initialize() {
        Airport a = Airport.getInstance();
        Flight f = a.getFlight();
        String [] time = f.getDeparturetime().split(" ");
        date.setText(time[0]);
        departuretime.setText(time[1]);
        flightclass.setText(a.getPassenger().getTicket().gettype());
        flightno.setText(f.getFlightnumber());
        from.setText(f.getDeparturecity());
        passengername.setText(a.getPassenger().getProfile().getName());
        seatno.setText(a.getPassenger().getTicket().getSeat().getSeatnum());
        to.setText(f.getArrivalcity());
    }

}
