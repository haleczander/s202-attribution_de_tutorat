package ihm.utils;

import java.util.ArrayList;

import ihm.Interface;
import ihm.events.TutoringSelectorListener;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import oop.Resource;
import oop.Tutor;
import oop.Tutored;

public class TutoringUtils {  

    public static void updateLists(Interface iface){
        updateLists(iface, false);
    }

    public static void updateLists(Interface iface, boolean color) {
        iface.tutors.getItems().clear();
        iface.tutors.getItems().addAll(new ArrayList<Tutor>(iface.dpt.currentTutoring.getTutors()));

        iface.tutored.setCellFactory(new StudentListColorier(iface));
        iface.tutors.setCellFactory(new StudentListColorier(iface));
        

        iface.tutored.getItems().clear();
        iface.tutored.getItems().addAll(new ArrayList<Tutored>(iface.dpt.currentTutoring.getTutored()));
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
