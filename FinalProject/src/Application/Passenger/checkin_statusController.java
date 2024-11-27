package Application.Passenger;

import AirportManagement.Airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class checkin_statusController {

    @FXML
    private Text checkinqueue;

    @FXML
    private Button exitcheckinqueue;
    public void initialize() {
        checkinqueue.setText(String.valueOf(Airport.getInstance().CheckInwait()) + "minutes");
    }

    @FXML
    void onClickexitcheckinqueue(ActionEvent event) {
        Airport.getInstance().ExitCheckInQueue();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
