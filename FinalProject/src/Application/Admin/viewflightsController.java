package Application.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.List;
import java.util.LinkedList;

import AirportManagement.*;
import AirportManagement.FlightManagement.*;;
public class viewflightsController {

     @FXML
    private Button Nextbutton;

    @FXML
    private Button Previousbutton;

    @FXML
    private Button cancelflight1;

    @FXML
    private Button cancelflight2;

    @FXML
    private Button cancelflight3;

    @FXML
    private Button cancelflight4;

    @FXML
    private Text date1;

    @FXML
    private Text date2;

    @FXML
    private Text date3;

    @FXML
    private Text date4;

    @FXML
    private Text flightno1;

    @FXML
    private Text flightno2;

    @FXML
    private Text flightno3;

    @FXML
    private Text flightno4;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private Button searchbutton;

    @FXML
    private TextField searchhere;

    @FXML
    private Text time1;

    @FXML
    private Text time2;

    @FXML
    private Text time3;

    @FXML
    private Text time4;

    @FXML
    private Text to1;

    @FXML
    private Text to2;

    @FXML
    private Text to3;

    @FXML
    private Text to4;

    private LinkedList<Flight> flightQueue;
    private int currentIndex;

    public void initialize() {
        flightQueue = new LinkedList<>();
        currentIndex = 0;
        List<Flight> flights = Airport.getInstance().GetFlights(); // Assuming this method retrieves all flights
        flightQueue.addAll(flights);

        displayFlights();
    }

    private void displayFlights() {
        resetPanes();

        if (!flightQueue.isEmpty()) {
            displayPane(pane1, flightno1, date1, time1, to1, 0);
            displayPane(pane2, flightno2, date2, time2, to2, 1);
            displayPane(pane3, flightno3, date3, time3, to3, 2);
            displayPane(pane4, flightno4, date4, time4, to4, 3);
        }

        Previousbutton.setDisable(currentIndex == 0);
        Nextbutton.setDisable(currentIndex + 4 >= flightQueue.size());
    }

    private void resetPanes() {
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);
    }

    private void displayPane(Pane pane, Text flightNo, Text date, Text time, Text to, int offset) {
        int index = currentIndex + offset;
        if (index < flightQueue.size()) {
            Flight flight = flightQueue.get(index);
            flightNo.setText(flight.getFlightnumber());
            date.setText(flight.getDeparturetime().split(" ")[0]);
            time.setText(flight.getDeparturetime().split(" ")[1]);
            to.setText(flight.getArrivalcity());
            pane.setVisible(true);
        }
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (currentIndex + 4 < flightQueue.size()) {
            currentIndex += 4;
            displayFlights();
        }
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        if (currentIndex - 4 >= 0) {
            currentIndex -= 4;
            displayFlights();
        }
    }

    @FXML
    void onClickcancelflight1(ActionEvent event) {
        cancelFlight(0);
    }

    @FXML
    void onClickcancelflight2(ActionEvent event) {
        cancelFlight(1);
    }

    @FXML
    void onClickcancelflight3(ActionEvent event) {
        cancelFlight(2);
    }

    @FXML
    void onClickcancelflight4(ActionEvent event) {
        cancelFlight(3);
    }

    private void cancelFlight(int offset) {
        int index = currentIndex + offset;
        if (index < flightQueue.size()) {
            Flight flight = flightQueue.get(index);
            Airport.getInstance().CancelFlight(flight.getid()); // Assuming this method cancels the flight
            flightQueue.remove(index);
            displayFlights();
        }
    }
    @FXML
    void onClicksearchbutton(ActionEvent event) {
        String search = searchhere.getText();
        if (searchhere.getText() == "" || searchhere.getLength() == 0) {
            List<Flight> flights = Airport.getInstance().GetFlights();
            flightQueue.clear();
            flightQueue.addAll(flights);
            displayFlights();
            currentIndex = 0;
            return ;
        }
        List<Flight> filteredFlights = Airport.getInstance().GetFlights(search);
        flightQueue.clear();
        flightQueue.addAll(filteredFlights);
        currentIndex = 0;
        displayFlights();
        return ;
    }
}
