package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.popup.AddStudent;
import ihm.utils.TutoringUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Events {

    public static void RemoveStudentHandler(Interface iface){
        if (iface.dpt.currentTutoring == null) {
            return;
        }
        if (iface.selectedStudent != null){
            Alert alert = new Alert(AlertType.WARNING,
                        "Vous allez supprimer " + iface.selectedStudent.getName()  + ". Êtes-vous certain(e)?",
                        ButtonType.YES, ButtonType.CANCEL);
                alert.headerTextProperty().set("");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    if (iface.selectedStudent instanceof Tutor) {
                        iface.dpt.currentTutoring.removeStudent((Tutor)iface.selectedStudent);
                    }
                    else {
                        iface.dpt.currentTutoring.removeStudent((Tutored)iface.selectedStudent);
        
                    }
                    iface.selectedStudent = null;
                    TutoringUtils.updateLists(iface);
                }               
        }
    }

    public static void AddStudentHandler(Interface iface) {
        if (iface.dpt.currentTutoring == null) {
            return;
        }
        new AddStudent(iface);
    }

    public static void AffectationHandler(Interface iface) {
        if (iface.dpt.currentTutoring == null) {
            return;
        }
        iface.aretes.getItems().clear();
        iface.dpt.currentTutoring.affectations();
        iface.aretes.getItems().addAll(iface.dpt.currentTutoring.affectations);
        TutoringUtils.updateLists(iface, true);         
    }

    public static void DragNDropHandler(Interface iface, Student student){
        if (!student.getClass().equals(iface.draggedStudent.getClass())){
            Alert alert = new Alert(AlertType.CONFIRMATION,
                        "Vous allez forcer l'affectation entre " + iface.draggedStudent.getName() + " et " + student.getName()  + ". Êtes-vous certain(e)?",
                        ButtonType.YES, ButtonType.CANCEL);
                alert.headerTextProperty().set("");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    if (iface.draggedStudent instanceof Tutored) {
                        iface.dpt.currentTutoring.addForcedAssignments((Tutored)iface.draggedStudent, (Tutor)student);
                    }
                    else {
                        iface.dpt.currentTutoring.addForcedAssignments((Tutored)student, (Tutor)iface.draggedStudent);
                    }
                }
                
        }
        iface.draggedStudent = null;
    }
}
