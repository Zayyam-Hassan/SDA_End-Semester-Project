package Application.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import AirportManagement.Airport;
import AirportManagement.Gate;
import AirportManagement.Terminal;
import AirportManagement.FlightManagement.Flight;
import Application.Utility.ScreenFactory;

public class ScheduleFlightController {

    @FXML
    private DatePicker ArrivalTime;

    @FXML
    private ComboBox<String> BusinessClassBaggage;

    @FXML
    private TextField BusinessClassPrice;

    @FXML
    private DatePicker DepartureTime;

    @FXML
    private ComboBox<String> EconomyBaggage;

    @FXML
    private TextField EconomyPrice;

    @FXML
    private ComboBox<String> FirstClassBaggage;

    @FXML
    private TextField FirstClassPrice;

    @FXML
    private TextField FlightNum;

    @FXML
    private ComboBox<String> From;

    @FXML
    private Button ScheduleFlight;

    @FXML
    private ComboBox<String> SelectGate;

    @FXML
    private ComboBox<String> To;

    public void initialize() {
        // Add unique random cities to "From" and "To" ComboBoxes
        List<Terminal> t = Airport.getInstance().getTerminals();
        for (Terminal terminal : t) {
        for (Gate gate : terminal.getGate()) {
            String gateOption = "Terminal " + terminal.getid() + " - Gate " + gate.getgateid();
            SelectGate.getItems().add(gateOption);
        }
    }
        String[] cities = {
            "Karachi", "Lahore", "Islamabad", "Quetta", "Peshawar",
            "Multan", "Faisalabad", "Sialkot", "Hyderabad", "Rawalpindi"
        };

        Set<String> selectedCities = new HashSet<>();
        Random random = new Random();

        while (selectedCities.size() < 10) {
            selectedCities.add(cities[random.nextInt(cities.length)]);
        }

        int count = 0;
        for (String city : selectedCities) {
            if (count < 5) {
                From.getItems().add(city);
            } else {
                To.getItems().add(city);
            }
            count++;
        }

        // Generate random baggage weights (greater than 60, less than 80, 5kg difference)
        for (int i = 0; i < 5; i++) {
            int baggageWeight = 60 + 5 * (i + 1);
            String weightOption = baggageWeight + " kg";
            EconomyBaggage.getItems().add(weightOption);
            BusinessClassBaggage.getItems().add(weightOption);
            FirstClassBaggage.getItems().add(weightOption);
        }
    }

    @FXML
    void onClickScheduleFlight(ActionEvent event) {
            String departureDateTime = getFormattedDateTime(DepartureTime);
            String arrivalDateTime = getFormattedDateTime(ArrivalTime);
            if (departureDateTime.isEmpty() || arrivalDateTime.isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Departure and Arrival times cannot be empty.");
                return;
            }
            String flightNumber = FlightNum.getText();
            String fromCity = From.getValue();
            String toCity = To.getValue();
            String selectedGate = SelectGate.getValue();
            if (flightNumber.isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Flight Number cannot be empty.");
                return;
            }
            if (fromCity == null || toCity == null || fromCity.isEmpty() || toCity.isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Departure and Destination cities cannot be empty.");
                return;
            }
            if (fromCity.equals(toCity)) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Departure and Destination cities cannot be the same.");
                return;
            }
            if (selectedGate.isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Gate selection is required.");
                return;
            }
            // Extract terminal and gate numbers from the selected gate
            int terminalNo = 0;
            int gateNo = 0;
    
            if (selectedGate != null && !selectedGate.isEmpty()) {
                String[] gateParts = selectedGate.split(" - ");
                String terminalPart = gateParts[0]; // "Terminal X"
                String gatePart = gateParts[1]; // "Gate Y"
    
                // Extract terminal number (e.g., "Terminal X")
                terminalNo = Integer.parseInt(terminalPart.replace("Terminal ", "").trim());
    
                // Extract gate number (e.g., "Gate Y")
                gateNo = Integer.parseInt(gatePart.replace("Gate ", "").trim());
            }
           // Convert prices to double
            double economyPrice = Double.parseDouble(EconomyPrice.getText());
            double businessClassPrice = Double.parseDouble(BusinessClassPrice.getText());
            double firstClassPrice = Double.parseDouble(FirstClassPrice.getText());
            if (EconomyPrice.getText().isEmpty() || BusinessClassPrice.getText().isEmpty() || FirstClassPrice.getText().isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Prices cannot be empty");
                return;
            }
            double economyBaggage = Double.parseDouble(EconomyBaggage.getValue().split(" ")[0]);
            double businessBaggage = Double.parseDouble(BusinessClassBaggage.getValue().split(" ")[0]);
            double firstClassBaggage = Double.parseDouble(FirstClassBaggage.getValue().split(" ")[0]);
            if (EconomyBaggage.getValue().isEmpty() || BusinessClassBaggage.getValue().isEmpty() || FirstClassBaggage.getValue().isEmpty()) {
                ScreenFactory.showAlert(AlertType.ERROR, "Schedule Flight Failed", "Baggage allowance cannot be empty");
                return;
            }
            System.out.println("Scheduling Flight");
            Airport.getInstance().ScheduleFlight(flightNumber, departureDateTime,arrivalDateTime ,fromCity, toCity, 30, economyPrice , firstClassPrice , businessClassPrice , economyBaggage , firstClassBaggage , businessBaggage);
            Airport.getInstance().allocategate(Flight.getidcount() , terminalNo , gateNo);
            Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
            currentStage.close();
    }

    private String getFormattedDateTime(DatePicker datePicker) {
        // Get the selected date from the DatePicker
        LocalDateTime dateTime = datePicker.getValue().atStartOfDay();
    
        // Generate random hours, minutes, and seconds
        Random random = new Random();
        int randomHour = random.nextInt(24);
        int randomMinute = random.nextInt(60); 
        int randomSecond = random.nextInt(60); 
        dateTime = dateTime.plusHours(randomHour).plusMinutes(randomMinute).plusSeconds(randomSecond);
    
        // Format the resulting datetime
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
}
