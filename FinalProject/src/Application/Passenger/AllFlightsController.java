package Application.Passenger;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AllFlightsController {

    @FXML
    private Button BuyNow;

    @FXML
    private Button BuyNow1;

    @FXML
    private Button BuyNow2;

    @FXML
    private Button BuyNow3;

    @FXML
    private Text Date;

    @FXML
    private Text Date1;

    @FXML
    private Text Date2;

    @FXML
    private Text Date3;

    @FXML
    private Text DepartureTime;

    @FXML
    private Text DepartureTime1;

    @FXML
    private Text DepartureTime2;

    @FXML
    private Text DepartureTime3;

    @FXML
    private Text Flightno;

    @FXML
    private Text Flightno1;

    @FXML
    private Text Flightno2;

    @FXML
    private Text Flightno3;

    @FXML
    private Button Next;

    @FXML
    private Pane Pane1;

    @FXML
    private Pane Pane2;

    @FXML
    private Pane Pane3;

    @FXML
    private Pane Pane4;

    @FXML
    private Button Previous;

    @FXML
    private TextField Search;

    @FXML
    private Text To;

    @FXML
    private Text To1;

    @FXML
    private Text To2;

    @FXML
    private Text To3;

    @FXML
    private Text NoRecordText;

    @FXML
    private Button mySearchButton;

    private LinkedList<Flight> flightQueue;
    private int currentIndex;

    public void initialize() {
        NoRecordText.setVisible(false);
        flightQueue = new LinkedList<>();
        currentIndex = 0;
        List<Flight> flights = Airport.getInstance().GetFlights();
        System.out.println(flights.size());
        
        if (flights.size() == 0) {
            Pane1.setVisible(false);
            Pane2.setVisible(false);
            Pane3.setVisible(false);
            Pane4.setVisible(false);
            Previous.setDisable(true);
            Next.setDisable(true);
            NoRecordText.setVisible(true);
        } else {
            flightQueue.addAll(flights);
            displayFlights();
        }
    }

    private void displayFlights() {
        if (!flightQueue.isEmpty()) {
            Flight flight1 = flightQueue.get(currentIndex);
            Flight flight2 = currentIndex + 1 < flightQueue.size() ? flightQueue.get(currentIndex + 1) : null;
            Flight flight3 = currentIndex + 2 < flightQueue.size() ? flightQueue.get(currentIndex + 2) : null;
            Flight flight4 = currentIndex + 3 < flightQueue.size() ? flightQueue.get(currentIndex + 3) : null;

            // Display the flights on the UI
            Flightno.setText(flight1.getFlightnumber());
            String datetime = flight1.getDeparturetime();
            String [] parts = datetime.split(" ");
            DepartureTime.setText(parts[1]);
            Date.setText(parts[0]);
            To.setText(flight1.getArrivalcity());

            if (flight2 != null) {
                Flightno1.setText(flight2.getFlightnumber());
                datetime = flight2.getDeparturetime();
                parts = datetime.split(" ");
                DepartureTime1.setText(parts[1]);
                Date1.setText(parts[0]);
                To1.setText(flight2.getArrivalcity());
                Pane2.setVisible(true);
            }
            else {
                Pane2.setVisible(false);
            }
            if (flight3 != null) {
                Flightno2.setText(flight3.getFlightnumber());
                datetime = flight3.getDeparturetime();
                parts = datetime.split(" ");
                DepartureTime2.setText(parts[1]);
                Date2.setText(parts[0]);
                To2.setText(flight3.getArrivalcity());
                Pane3.setVisible(true);
            }
            else {
                Pane3.setVisible(false);
            }
            if (flight4 != null) {
                Flightno3.setText(flight4.getFlightnumber());
                datetime = flight4.getDeparturetime();
                parts = datetime.split(" ");
                DepartureTime3.setText(parts[1]);
                Date3.setText(parts[0]);
                To3.setText(flight4.getArrivalcity());
                Pane4.setVisible(true);
            }
            else {
                Pane4.setVisible(false);
            }
            System.out.println(currentIndex);
            // Enable/Disable Previous and Next buttons based on the index
            Previous.setDisable(currentIndex == 0);
            Next.setDisable(currentIndex + 4 >= flightQueue.size());
        }
    }

    @FXML
    void onClickBuyNow(ActionEvent event) {
        Object source = event.getSource();
        Flight selectedFlight = null;
        if (source == BuyNow) {
            selectedFlight = flightQueue.get(currentIndex);
        } else if (source == BuyNow1) {
            selectedFlight = currentIndex + 1 < flightQueue.size() ? flightQueue.get(currentIndex + 1) : null;
        } else if (source == BuyNow2) {
            selectedFlight = currentIndex + 2 < flightQueue.size() ? flightQueue.get(currentIndex + 2) : null;
        } else if (source == BuyNow3) {
            selectedFlight = currentIndex + 3 < flightQueue.size() ? flightQueue.get(currentIndex + 3) : null;
        }
        System.out.println(selectedFlight == null);
        AvailableSeatsController.setFlight(selectedFlight);
        System.out.println(selectedFlight.getid());
        ScreenFactory.getInstance().loadPopupScreen("/Application/Passenger/AvailableSeats.fxml", "Airport Management System");
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (currentIndex + 4 < flightQueue.size()) {
            currentIndex += 4; // Move to the next set of flights
            displayFlights();
        }
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        if (currentIndex - 4 >= 0) {
            currentIndex -= 4; // Move to the previous set of flights
            displayFlights();
        }
    }

    @FXML
    void onClickSearch(ActionEvent event) {
        String search = Search.getText();
        if (Search.getText() == "" || Search.getLength() == 0) {
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
