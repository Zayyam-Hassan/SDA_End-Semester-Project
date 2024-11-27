package Application.Passenger;

import AirportManagement.Airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class terminal_statusController {

    @FXML
    private Button ExitTerminalqueue;

    @FXML
    private Text Terminalwaitingtime;
    public void initialize() {
        Terminalwaitingtime.setText(String.valueOf(Airport.getInstance().GateWait()) + "minutes");
    }

    @FXML
    void onClickExitTerminalqueue(ActionEvent event) {
        Airport.getInstance().EnterGateQueue();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
