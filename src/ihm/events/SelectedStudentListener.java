package ihm.events;

import ihm.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class SelectedStudentListener implements EventHandler<ActionEvent> {
    Interface iface;

    public SelectedStudentListener(Interface iface) {
        this.iface = iface;
    }


    @Override
    public void handle(ActionEvent e) {
        Student student = (Student)e.getTarget();
        if (iface.doubleSelect) {
            if (iface.selectedStudent.isTutored() != student.isTutored()){
                if (iface.selectedStudent.isTutored()){
                    Events.ForcedAffectationHandler(iface, (Tutored) iface.selectedStudent, (Tutor) student, iface.affectationInterdite);
                }
                else {
                    Events.ForcedAffectationHandler(iface, (Tutored) student, (Tutor) iface.selectedStudent, iface.affectationInterdite);
                }
            }
        }
        else{
            iface.selectedStudent = student;
        }
    }

}