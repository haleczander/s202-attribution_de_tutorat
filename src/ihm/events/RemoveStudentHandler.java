package ihm.events;

import java.util.Optional;

import ihm.Interface;
import ihm.utils.TutoringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import oop.Tutor;
import oop.Tutored;

public class RemoveStudentHandler implements EventHandler<ActionEvent> {
    Interface iface;

    public RemoveStudentHandler(Interface iface) {
        this.iface = iface;
    }

    public void handle(ActionEvent e){
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
}
