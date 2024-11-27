package Application.Passenger;

import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import AirportManagement.*;
public class NotificationsController {

    @FXML
    private Button Previousbutton;

    @FXML
    private Button nextbutton;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Pane pane3;

    @FXML
    private Pane pane4;

    @FXML
    private Pane pane5;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private Text text5;

    private LinkedList<Notification> notificationQueue;
    private int currentIndex;

    public void initialize() {
        notificationQueue = new LinkedList<>();
        currentIndex = 0;
        List<Notification> notifications = Airport.getInstance().getLoggedinNotifications();
        notificationQueue.addAll(notifications);

        displayNotifications();
    }

    private void displayNotifications() {
        resetPanes();

        if (!notificationQueue.isEmpty()) {
            displayPane(pane1, text1, 0);
            displayPane(pane2, text2, 1);
            displayPane(pane3, text3, 2);
            displayPane(pane4, text4, 3);
            displayPane(pane5, text5, 4);
        }

        Previousbutton.setDisable(currentIndex == 0);
        nextbutton.setDisable(currentIndex + 5 >= notificationQueue.size());
    }

    private void resetPanes() {
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        pane4.setVisible(false);
        pane5.setVisible(false);
    }

    private void displayPane(Pane pane, Text text, int offset) {
        int index = currentIndex + offset;
        if (index < notificationQueue.size()) {
            Notification notification = notificationQueue.get(index);
            text.setText(notification.getMessage());
            pane.setVisible(true);
        }
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (currentIndex + 5 < notificationQueue.size()) {
            currentIndex += 5;
            displayNotifications();
        }
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        if (currentIndex - 5 >= 0) {
            currentIndex -= 5;
            displayNotifications();
        }
    }
}
