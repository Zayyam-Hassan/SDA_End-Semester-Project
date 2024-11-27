package Application.Passenger;

import Application.Utility.ScreenFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import AirportManagement.FlightManagement.Seat;
public class AvailableSeatsController {

    @FXML
    private Button AddBaggage;

    @FXML
    private CheckBox Business;

    @FXML
    private ComboBox<String> ChooseSeat;

    @FXML
    private CheckBox Economy;

    @FXML
    private CheckBox FirstClass;
    private static Flight selectedFlight ;
    private String [] getString(List<Seat> seats) {
        String [] list = new String [seats.size()];
        for (int i = 0; i < list.length ; i++) 
            list[i] = seats.get(i).getid() +":" + seats.get(i).getSeatnum();
        return list;
    }
    public void initialize() {
        if (selectedFlight != null) {
            List<Seat> Seats = Airport.getInstance().GetAvailableSeat(selectedFlight);
            String [] Seatnum = getString(Seats);
            ChooseSeat.getItems().clear();
            ChooseSeat.getItems().addAll(Seatnum);
            System.out.println("Done!");
        }
    }
    public static void setFlight(Flight f) {
        selectedFlight = f;
    }

    @FXML
    void onClickAddBaggage(ActionEvent event) {
        String value = ChooseSeat.getValue();
        String [] val = value.split(":");
        if (value.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "Seat Selection Error", "Please choose a seat.");
            return;
        }        
        Seat seat = selectedFlight.getSeat(Integer.parseInt(val[0]));
        AddBaggageController.setValues(selectedFlight , seat );
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Addbaggage.fxml", "Airport Management System");
    }
    @FXML
    void onClickBusiness(ActionEvent event) {
        Economy.setSelected(false);
        FirstClass.setSelected(false);
        ChooseSeat.getItems().clear();
        List<Seat> Seats = Airport.getInstance().GetAvailableSeat(selectedFlight , "Business");
        String[] args = getString(Seats);
        ChooseSeat.getItems().addAll(args);
    }
    @FXML
    void onClickEconomy(ActionEvent event) {
        Business.setSelected(false);
        FirstClass.setSelected(false);
        ChooseSeat.getItems().clear();
        List<Seat> Seats = Airport.getInstance().GetAvailableSeat(selectedFlight , "Economy");
        String[] args = getString(Seats);
        ChooseSeat.getItems().addAll(args);
    }

    @FXML
    void onClickFirstClass(ActionEvent event) {
        Economy.setSelected(false);
        Business.setSelected(false);
        ChooseSeat.getItems().clear();
        List<Seat> Seats = Airport.getInstance().GetAvailableSeat(selectedFlight , "First Class");
        String[] args = getString(Seats);
        ChooseSeat.getItems().addAll(args);

    }

}
