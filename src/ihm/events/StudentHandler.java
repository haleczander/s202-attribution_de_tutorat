package ihm.events;

import ihm.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class StudentHandler implements EventHandler<ActionEvent> {
    Interface iface;

    public StudentHandler(Interface iface) {
        this.iface = iface;
    }

    public void handle(ActionEvent e) {
        Button bt = ((Button) e.getTarget());
        if (bt.getText().equals("+")) {
            System.out.println("J'ajoute");
        } else if (bt.getText().equals("‒")) {
            System.out.println("Je retire");
        } else if (bt.getText().equals("🔗")) { // ⩆
            System.out.println("J'affecte'");
        } else {
            System.out.println("J'interdis'");
        }
    }
}