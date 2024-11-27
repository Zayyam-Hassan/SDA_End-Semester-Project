package Application.Passenger;

import AirportManagement.Airport;
import AirportManagement.FlightManagement.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BaggageStatusController {

    @FXML
    private Text BaggageStatus;

    @FXML
    private Button Exit;
    public void initialize() {
        Airport a = Airport.getInstance();
        BaggageStatus.setText(a.getBaggageLocation());
    }

    @FXML
    void onClickExit(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
