package Application.Passenger;
import AirportManagement.Airport;
import Application.Utility.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField CNICno;

    @FXML
    private TextField Email;

    @FXML
    private TextField Fullname;

    @FXML
    private TextField Passportno;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Phoneno;

    @FXML
    private Hyperlink SignIn;

    @FXML
    private Button SignUp;

    @FXML
    private TextField Username;

    @FXML
    private CheckBox female;

    @FXML
    private CheckBox male;

    @FXML
    void onClickFemale(ActionEvent event) {
        male.setSelected(false);
    }

    @FXML
    void onClickMale(ActionEvent event) {
        female.setSelected(false);
    }
    @FXML
    void onClickSIgnUp(ActionEvent event) {
        String username = Username.getText();
        String password = Password.getText();
        String email = Email.getText();
        String fullname = Fullname.getText();
        String cnic = CNICno.getText();
        String passport = Passportno.getText();
        String phone = Phoneno.getText();
        String gender = female.isSelected()? "F" : male.isSelected()? "M" : "";
        if (username.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Username cannot be empty.");
            return;
        }
        if (password.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Password cannot be empty.");
            return;
        }
        if (email.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Email cannot be empty.");
            return;
        }
        if (fullname.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Fullname cannot be empty.");
            return;
        }
        if (cnic.isEmpty() || cnic.length() != 13 || cnic.contains("-")) {
            if (cnic.contains("-")) {
                ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "CNIC cannot contain '-' characters.");
                return;
            }
            if (cnic.length() != 13) {
                ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "CNIC should have 13 digits.");
                return;
            }
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "CNIC cannot be empty.");
            return;
        }
        if (passport.isEmpty() || !passport.matches("^[A-Za-z]{2}\\d{7}$")) {
            if (!passport.matches("^[A-Za-z]{2}\\d{7}$")) {
                ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Passport must have 2 alphabets followed by 7 digits.");
                return;
            }
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Passport cannot be empty.");
            return;
        }
        if (phone.isEmpty() || phone.length() != 11 || phone.contains("-")) {
            if (phone.contains("-")) {
                ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Phone number cannot contain '-' characters.");
                return;
            }
            if (phone.length() != 11) {
                ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Phone number should have 11 digits.");
                return;
            }
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Phone number cannot be empty.");
            return;
        }
        if (gender.isEmpty()) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "Gender cannot be empty.");
            return;
        }        
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        boolean success = Airport.getInstance().AddPassenger(username, password, email, fullname, phone, cnic, passport, gender);
        if (!success) {
            ScreenFactory.showAlert(AlertType.ERROR, "SignUp Failed", "An account with the same username or password already exists.");
        }
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/UserProfile.fxml", "Passenger Profile");

    }
    @FXML
    void onClickSignIn(ActionEvent event) {
        Stage currentStage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        ScreenFactory.getInstance().loadScreen(currentStage, "/Application/Passenger/Loginpage.fxml", "Login In");
    }

}
