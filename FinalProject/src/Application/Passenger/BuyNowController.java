package Application.Passenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Seat;
public class BuyNowController {

    @FXML
    private Text Class;

    @FXML
    private Text Date;

    @FXML
    private Text Fullname;

    @FXML
    private Button PayNow;

    @FXML
    private Text Price;

    @FXML
    private Text Seat;

    @FXML
    private Text flightno;
    private static Flight flight ;
    private static Seat seat ;
    private static Stage stage ;
    private static void setstage (Stage s) {
        stage = s;
    }
    public void initialize() {
        Class.setText(seat.getSeattype());
        Airport a = Airport.getInstance();
        Fullname.setText(a.getPassenger().getProfile().getName());
        String [] date = flight.getDeparturetime().split(" ");
        Date.setText(date[0]);
        flightno.setText(flight.getFlightnumber());
        Seat.setText(seat.getSeattype());
        Price.setText(Double.toString(a.getPrice(flight , seat.getSeattype() )));

    }
    public static void setValues (Flight f , Seat s) {
        flight = f ;
        seat = s ;
    }
    @FXML
    void onClickPayNow(ActionEvent event) {
        Airport.getInstance().BuyTicket(flight.getid(), seat.getid());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
