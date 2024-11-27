package Application.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import AirportManagement.*;
import Application.Utility.ScreenFactory;
public class AddEmergencyController {

    @FXML
    private Button AddEmergency;

    @FXML
    private TextField EmergencyDescription;

    @FXML
    private CheckBox FireBrigade;

    @FXML
    private CheckBox MedicalTeam;

    @FXML
    private CheckBox Police;

    @FXML
    void onClickAddEmergency(ActionEvent event) {
        String description = EmergencyDescription.getText();
        boolean fireBrigadeChecked = FireBrigade.isSelected();
        boolean medicalTeamChecked = MedicalTeam.isSelected();
        boolean policeChecked = Police.isSelected();
        if (description.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "Add Emergency Failed", "Please enter the emergency details.");
            return;
        }
        // Add emergency to the Airport system
        int lvl = 1;
        if (fireBrigadeChecked) lvl++;
        if (medicalTeamChecked) lvl++;
        if (policeChecked) lvl++;
        int emergencyId = Airport.getInstance().addEmergency(description, lvl , fireBrigadeChecked || medicalTeamChecked || policeChecked); // Assume this returns the ID of the emergency
    
        // Randomized and hardcoded data for authorities
        String policeName = "Officer Brown";
        String policeContact = "111-222-3333";
        String fireBrigadeName = "Fire Chief";
        String fireBrigadeContact = "987-654-3210";
        String doctorName = "Dr. Smith";
        String doctorContact = "555-123-4567";
    
        String policeResponseTime = getRandomResponseTime();
        String fireBrigadeResponseTime = getRandomResponseTime();
        String doctorResponseTime = getRandomResponseTime();
    
        // Notify and add Police authority
        if (policeChecked) {
            int teamSize = (int) (Math.random() * 15) + 1; // Randomize between 1 and 15 team members
            boolean weaponsAvailable = Math.random() < 0.7; // 70% chance
            Airport.getInstance().notifyauthority(
                emergencyId, 1, policeName, policeContact, policeResponseTime, teamSize, weaponsAvailable, null
            );
        }
    
        // Notify and add Fire Brigade authority
        if (fireBrigadeChecked) {
            int numTrucks = (int) (Math.random() * 5) + 1; // Randomize between 1 and 5 trucks
            boolean hasFireEquipment = Math.random() < 0.5; // 50% chance
            Airport.getInstance().notifyauthority(
                emergencyId, 2, fireBrigadeName, fireBrigadeContact, fireBrigadeResponseTime, numTrucks, hasFireEquipment, null
            );
        }
    
        // Notify and add Doctor authority
        if (medicalTeamChecked) {
            int numDoctors = (int) (Math.random() * 10) + 1; // Randomize between 1 and 10 doctors
            String specialization = "General"; // Default specialization
            Airport.getInstance().notifyauthority(
                emergencyId, 3, doctorName, doctorContact, doctorResponseTime, numDoctors, false, specialization
            );
        }
    
        // Close the stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
    
        private String getRandomResponseTime() {
            int minutes = (int) (Math.random() * 51) + 10; // Randomize between 10 and 60 minutes
            int seconds = (int) (Math.random() * 60); // Randomize seconds (0-59)
            return String.format("00:%02d:%02d", minutes, seconds);
        }
        

}
