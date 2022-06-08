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
            new AddStudentHandler(iface);
        } else if (bt.getText().equals("‒")) {
            new RemoveStudentHandler(iface);
        } else if (bt.getText().equals("🔗")) { // ⩆
            System.out.println("J'affecte'");
        } else {
            System.out.println("J'interdis'");
        }
    }
}