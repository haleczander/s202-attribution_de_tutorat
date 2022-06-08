package ihm.events;

import ihm.Interface;
import ihm.utils.TutoringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import oop.Tutor;
import oop.Tutored;

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
            if (iface.selectedStudent != null){
                if (iface.selectedStudent instanceof Tutor) {
                    iface.dpt.currentTutoring.removeStudent((Tutor)iface.selectedStudent);
                }
                else {
                    iface.dpt.currentTutoring.removeStudent((Tutored)iface.selectedStudent);

                }
                iface.selectedStudent = null;
                TutoringUtils.updateLists(iface);    
            }
        } else if (bt.getText().equals("🔗")) { // ⩆
            System.out.println("J'affecte'");
        } else {
            System.out.println("J'interdis'");
        }
    }
}