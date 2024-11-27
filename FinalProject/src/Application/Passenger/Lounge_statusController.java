package Application.Passenger;

import AirportManagement.Airport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Lounge_statusController {

    @FXML
    private Button Exitloungequeue;

    @FXML
    private Text Loungewaitingtime;
    public void initialize() {
        Loungewaitingtime.setText(String.valueOf(Airport.getInstance().LoungeWait()) + "minutes");
    }

    @FXML
    void onClickExitloungequeue(ActionEvent event) {
        Airport.getInstance().ExitLoungeQueue();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
