package Application.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.LinkedList;
import java.util.List;
import AirportManagement.*;

public class NotifyAuthoritiesController {

    @FXML
    private Text AuthoritiesNotified1;

    @FXML
    private Text AuthoritiesNotified2;

    @FXML
    private Text AuthoritiesNotified3;

    @FXML
    private Text AuthoritiesNotified4;

    @FXML
    private Text DateTime1;

    @FXML
    private Text DateTime2;

    @FXML
    private Text DateTime3;

    @FXML
    private Text DateTime4;

    @FXML
    private Text EmergencyDescription1;

    @FXML
    private Text EmergencyDescription2;

    @FXML
    private Text EmergencyDescription3;

    @FXML
    private Text EmergencyDescription4;

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
    private Button Resolve1;

    @FXML
    private Button Resolve2;

    @FXML
    private Button Resolve3;

    @FXML
    private Button Resolve4;

    private LinkedList<EmergencySituation> emergencyQueue;
    private int currentIndex;

    public void initialize() {
        emergencyQueue = new LinkedList<>();
        currentIndex = 0;
        List<EmergencySituation> emergencies = Airport.getInstance().getEmergencySituations();
         // Assuming this method retrieves all emergency situation
         emergencyQueue.addAll(emergencies);
        displayEmergencySituations();
    }

    private void displayEmergencySituations() {
        resetPanes(); // Reset pane visibility before showing new data

        if (!emergencyQueue.isEmpty()) {
            // Show each emergency's data in its respective pane if it exists
            displayPane(Pane1, AuthoritiesNotified1, DateTime1, EmergencyDescription1, Resolve1, 0);
            displayPane(Pane2, AuthoritiesNotified2, DateTime2, EmergencyDescription2, Resolve2, 1);
            displayPane(Pane3, AuthoritiesNotified3, DateTime3, EmergencyDescription3, Resolve3, 2);
            displayPane(Pane4, AuthoritiesNotified4, DateTime4, EmergencyDescription4, Resolve4, 3);
        }

        Previous.setDisable(currentIndex == 0);
        Next.setDisable(currentIndex + 4 >= emergencyQueue.size());
    }

    private void resetPanes() {
        Pane1.setVisible(false);
        Pane2.setVisible(false);
        Pane3.setVisible(false);
        Pane4.setVisible(false);
    }

    private void displayPane(Pane pane, Text authoritiesNotified, Text dateTime, Text emergencyDescription, Button resolveButton, int offset) {
        int index = currentIndex + offset;
        if (index < emergencyQueue.size()) {
            EmergencySituation emergency = emergencyQueue.get(index);
            authoritiesNotified.setText(emergency.getAuthorityString());
            dateTime.setText(emergency.getDatetime());
            emergencyDescription.setText(emergency.getDescription().split(" ")[0] +  "....");
            resolveButton.setDisable(emergency.getResolved());  // Disable Resolve button if already resolved
            pane.setVisible(true);  // Make the pane visible when there's data
        }
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (currentIndex + 4 < emergencyQueue.size()) {
            currentIndex += 4;
            displayEmergencySituations();
        }
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        if (currentIndex - 4 >= 0) {
            currentIndex -= 4;
            displayEmergencySituations();
        }
    }

    @FXML
    void onClickResolveEmergency(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int offset = -1;

        // Determine which resolve button was clicked
        if (clickedButton == Resolve1) {
            offset = 0;
        } else if (clickedButton == Resolve2) {
            offset = 1;
        } else if (clickedButton == Resolve3) {
            offset = 2;
        } else if (clickedButton == Resolve4) {
            offset = 3;
        }

        if (offset != -1) {
            resolveEmergency(offset);
        }
    }

    private void resolveEmergency(int offset) {
        int index = currentIndex + offset;
        if (index < emergencyQueue.size()) {
            EmergencySituation emergency = emergencyQueue.get(index);
            Airport.getInstance().resolveemergency(emergency.getid());
            displayEmergencySituations(); // Refresh the list to update the button state and pane visibility
        }
    }
}
