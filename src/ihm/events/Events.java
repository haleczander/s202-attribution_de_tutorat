package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.popup.AddStudent;
import ihm.utils.ListCellFactory;
import ihm.utils.TutoringUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    public static void AddForcedAffectationHandler(Interface iface, boolean interdite){
        if (iface.dpt.currentTutoring == null || iface.selectedStudent == null) {
            return;
        }
        new AddStudent(iface, interdite);
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

    public static void DragNDropHandler(Interface iface, Student entered, boolean interdite) {
        if (ListCellFactory.draggedStudent == null){
        } else if ((!entered.isTutored() && ((Tutor)entered).isDuplicate()) 
            |(entered.getClass().equals(ListCellFactory.draggedStudent.getClass()))){
        }
        else {
            if (ListCellFactory.draggedStudent.isTutored()) {
                ForcedAffectationHandler(iface, (Tutored) ListCellFactory.draggedStudent, (Tutor) entered, interdite);
            } else {
                ForcedAffectationHandler(iface, (Tutored) entered, (Tutor) ListCellFactory.draggedStudent, interdite);
            }
        }
        ListCellFactory.draggedStudent = null;

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
    }

}
