package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.popup.Login;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AuthentificationHandler implements EventHandler<ActionEvent> {    Interface iface;

    public AuthentificationHandler(Interface iface) {
        this.iface = iface;
    }
    
    public void handle(ActionEvent e) {
        if (!Login.loggedIn) {
            new Login(iface);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez vous déconnecter. Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                Login.loggedIn = false;
                Login.account = null;
                iface.updateSession();
            }
        }
    }


}