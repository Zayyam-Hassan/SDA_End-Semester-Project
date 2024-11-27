package Application.Admin;

import AirportManagement.Airport;
import AirportManagement.Passenger;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Ticket;
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
    private static Passenger passenger ;
    public static void setPassenger(Passenger p) {
        passenger = p;
    }
    public void initialize() {
        Flight f = passenger.getTicket().getTicketDescription().getFlight();
        String [] time = f.getDeparturetime().split(" ");
        date.setText(time[0]);
        departuretime.setText(time[1]);
        flightclass.setText(passenger.getTicket().gettype());
        flightno.setText(f.getFlightnumber());
        from.setText(f.getDeparturecity());
        passengername.setText(passenger.getProfile().getName());
        seatno.setText(passenger.getTicket().getSeat().getSeatnum());
        to.setText(f.getArrivalcity());
    }


}
