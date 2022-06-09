package ihm.utils;

import ihm.Interface;
import ihm.events.TutoringSelectorListener;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import oop.Resource;

public class TutoringUtils {  
    public static void updateLists(Interface iface) {
        iface.tutors.getItems().clear();
        iface.tutors.refresh();
        iface.tutors.getItems().addAll(iface.dpt.currentTutoring.getTutors());       

        iface.tutored.getItems().clear();
        iface.tutored.refresh();
        iface.tutored.getItems().addAll(iface.dpt.currentTutoring.getTutored());
    }

    public static HBox initMatieres(Interface iface) {
        HBox matieres = new HBox();
        matieres.setAlignment(Pos.CENTER_LEFT);
        iface.cbMatieres.setPromptText("Choisir une matière");
        iface.cbMatieres.setMaxWidth(150);
        iface.cbMatieres.getSelectionModel().selectedItemProperty().addListener(new TutoringSelectorListener(iface));
        for (Resource resource : iface.dpt.getTutorings().keySet()) {
            iface.cbMatieres.getItems().add(resource);
        }

        matieres.getChildren().addAll(iface.cbMatieres);
        return matieres;
    }
}
