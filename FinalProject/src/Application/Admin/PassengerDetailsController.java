package Application.Admin;

import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

import AirportManagement.*;
import AirportManagement.FlightManagement.Flight;

public class PassengerDetailsController {

    @FXML
    private Text CNIC1;

    @FXML
    private Text CNIC2;

    @FXML
    private Text CNIC3;

    @FXML
    private Text CNIC4;

    @FXML
    private Text Gender1;

    @FXML
    private Text Gender2;

    @FXML
    private Text Gender3;

    @FXML
    private Text Gender4;

    @FXML
    private TextField MySearchBar;

    @FXML
    private Button MySearchButton;

    @FXML
    private Text Name1;

    @FXML
    private Text Name2;

    @FXML
    private Text Name3;

    @FXML
    private Text Name4;

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
    private Text Phoneno1;

    @FXML
    private Text Phoneno2;

    @FXML
    private Text Phoneno3;

    @FXML
    private Text Phoneno4;

    @FXML
    private Button Previous;

    @FXML
    private Button TicketDetails;

    @FXML
    private Button TicketDetails1;

    @FXML
    private Button TicketDetails2;

    @FXML
    private Button TicketDetails4;

    private LinkedList<Passenger> passengerQueue;
    private int currentIndex;

    public void initialize() {
        passengerQueue = new LinkedList<>();
        currentIndex = 0;

        // Assuming Airport.getInstance().GetPassengers() retrieves all passengers
        List<Passenger> passengers = Airport.getInstance().getPassengers();
        passengerQueue.addAll(passengers);

        displayPassengers();
    }

    private void displayPassengers() {
        resetPanes();

        if (!passengerQueue.isEmpty()) {
            displayPane(Pane1, Name1, CNIC1, Phoneno1, Gender1, TicketDetails, 0);
            displayPane(Pane2, Name2, CNIC2, Phoneno2, Gender2, TicketDetails1, 1);
            displayPane(Pane3, Name3, CNIC3, Phoneno3, Gender3, TicketDetails2, 2);
            displayPane(Pane4, Name4, CNIC4, Phoneno4, Gender4, TicketDetails4, 3);
        }

        Previous.setDisable(currentIndex == 0);
        Next.setDisable(currentIndex + 4 >= passengerQueue.size());
    }

    private void resetPanes() {
        Pane1.setVisible(false);
        Pane2.setVisible(false);
        Pane3.setVisible(false);
        Pane4.setVisible(false);
    }

    private void displayPane(Pane pane, Text name, Text cnic, Text phone, Text gender, Button ticketDetails, int offset) {
        int index = currentIndex + offset;
        if (index < passengerQueue.size()) {
            Passenger passenger = passengerQueue.get(index);
            name.setText(passenger.getProfile().getName());
            cnic.setText(passenger.getProfile().getCNIC());
            phone.setText(passenger.getProfile().getPhonenumber());
            gender.setText(passenger.getProfile().getGender());

            // Enable ticket details button if the passenger has a ticket
            ticketDetails.setDisable(passenger.getTicket() == null);
            pane.setVisible(true);
        }
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (currentIndex + 4 < passengerQueue.size()) {
            currentIndex += 4;
            displayPassengers();
        }
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        if (currentIndex - 4 >= 0) {
            currentIndex -= 4;
            displayPassengers();
        }
    }

    @FXML
    void onClickSearchButton(ActionEvent event) {
        
    }

    @FXML
    void onClickTicketDetails(ActionEvent event) {
        Object source = event.getSource();
        Passenger selectedpassenger = null;
        if (source == TicketDetails) {
            selectedpassenger = passengerQueue.get(currentIndex);
        } else if (source == TicketDetails1) {
            selectedpassenger = currentIndex + 1 < passengerQueue.size() ? passengerQueue.get(currentIndex + 1) : null;
        } else if (source == TicketDetails2) {
            selectedpassenger = currentIndex + 2 < passengerQueue.size() ? passengerQueue.get(currentIndex + 2) : null;
        } else if (source == TicketDetails4) {
            selectedpassenger = currentIndex + 3 < passengerQueue.size() ? passengerQueue.get(currentIndex + 3) : null;
        }
        Application.Admin.TicketdetailsController.setPassenger(selectedpassenger);
        ScreenFactory.getInstance().loadPopupScreen("/Application/Admin/Ticketdetails.fxml", "Ticket Details");
    }
}
