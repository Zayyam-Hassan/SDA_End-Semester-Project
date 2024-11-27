package Application.Passenger;

import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.Random;

import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Seat;
public class AddBaggageController {

    @FXML
    private Button Buynow;

    @FXML
    private ComboBox<String> Dimensions;

    @FXML
    private Button ReserveSeat;

    @FXML
    private ComboBox<String> Weight;
    private static Flight flight;
    private static Seat seat;
     public void initialize() {
        // Initialize Dimensions ComboBox with random dimensions
        String[] dimensions = {"20x30x40 cm", "25x35x45 cm", "30x40x50 cm", "35x45x55 cm", "40x50x60 cm"};
        Dimensions.getItems().clear();
        Dimensions.getItems().addAll(dimensions);
        Weight.getItems().clear();
        // Initialize Weight ComboBox with random weights (up to 40 kg)
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            // Add random weight between 1 and 40 (inclusive)
            int weight = random.nextInt(40) + 1;
            Weight.getItems().add(weight + " kg");
        }
    }
    private String generateBaggageTag() {
        Random random = new Random();

        // Generate 3 random alphabets
        StringBuilder tag = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char letter = (char) ('A' + random.nextInt(26));  // Random letter from A to Z
            tag.append(letter);
        }

        // Generate 4 random numbers
        for (int i = 0; i < 4; i++) {
            tag.append(random.nextInt(10));  // Random digit from 0 to 9
        }

        return tag.toString();
    }
    public static void setValues(Flight f, Seat s){
        flight = f;
        seat = s;
    }
    @FXML
    void getBaggageDimensions(ActionEvent event) {

    }

    @FXML
    void getBaggageWeight(ActionEvent event) {

    }
    private void addBaggage() {
        String weight = Weight.getValue();
        String [] w = weight.split(" ");
        Double weiDouble = Double.parseDouble(w[0]);
        if (weight.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "Weight Selection Error", "Please select a weight option.");
            return;
        }
        if (Dimensions.getValue() == null || Dimensions.getValue().isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "Dimension Selection Error", "Please select a dimension option.");
            return;
        }
        
        String tag = generateBaggageTag();
        String location = "CheckIn Counter";
        Airport.getInstance().addBaggage(flight.getid(), weiDouble, Dimensions.getValue(), tag, location , seat.getSeattype());
    }

    @FXML
    void onClickBuynow(ActionEvent event) {
        addBaggage();
        BuyNowController.setValues(flight, seat);
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/BuyNow.fxml", "Airport Management System");
        
    }

    @FXML
    void onClickReserveSeat(ActionEvent event) {
        addBaggage();
        Airport.getInstance().MakeReservation(flight.getid(), seat.getid());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

    }

}
