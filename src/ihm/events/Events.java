package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.popup.AddStudent;
import ihm.utils.TutoringUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonType;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Events {

    public static void RemoveStudentHandler(Interface iface) {
        if (iface.dpt.currentTutoring == null) {
            return;
        }
        if (iface.selectedStudent != null) {
            Alert alert = new Alert(AlertType.WARNING,
                    "Vous allez supprimer " + iface.selectedStudent.getName() + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                if (!iface.selectedStudent.isTutored()) {
                    iface.dpt.currentTutoring.removeStudent((Tutor) iface.selectedStudent);
                } else {
                    iface.dpt.currentTutoring.removeStudent((Tutored) iface.selectedStudent);

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
        TutoringUtils.updateLists(iface);
    }

    public static void DragNDropHandler(MouseEvent e, Interface iface, Student student) {
        if (!student.getClass().equals(iface.draggedStudent.getClass())) {
            boolean interdite = e.getButton() == MouseButton.SECONDARY;
            if (iface.draggedStudent.isTutored()) {
                ForcedAffectationHandler(iface, (Tutored) iface.draggedStudent, (Tutor) student, interdite);
            } else {
                ForcedAffectationHandler(iface, (Tutored) student, (Tutor) iface.draggedStudent, interdite);
            }
        }
        iface.draggedStudent = null;
    }

    public static void ForcedAffectationHandler(Interface iface, boolean interdite) {
        if (iface.selectedStudent == null) {
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION,
                "Pour " + (interdite ? "interdire" : "forcer") + " l'affectation avec " + iface.selectedStudent.getName()
                        + ", choisissez un second étudiant.",
                ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            iface.affectationInterdite = interdite;
            iface.doubleSelect = true;
        }
        else{
            iface.selectedStudent = null;
            iface.doubleSelect =false;
        }
    }

    public static void ForcedAffectationHandler(Interface iface, Tutored tutored, Tutor tutor, boolean interdite) {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Vous allez " + (interdite ? "interdire" : "forcer") + " l'affectation entre " + tutored.getName()
                        + " et " + tutor.getName() + ". Êtes-vous certain(e)?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            if (interdite) {
                iface.dpt.currentTutoring.addForbiddenAssignments(tutored, tutor);
            } else {
                iface.dpt.currentTutoring.addForcedAssignments(tutored, tutor);
            }
        }
        iface.selectedStudent = null;
        iface.doubleSelect = false;
    }
}
