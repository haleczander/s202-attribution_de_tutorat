package ihm.utils;

import graphs.Couple;
import ihm.Interface;
import ihm.events.TutoringSelectorListener;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import oop.Resource;
import oop.Student;

public class TutoringUtils {  
    public static void updateLists(Interface iface) {
        iface.tutors.getItems().clear();
        iface.tutored.getItems().clear();
        if (iface.dpt.currentTutoring.affectations.size() ==0 ){
            iface.tutors.getItems().addAll(iface.dpt.currentTutoring.getTutors()); 
            iface.tutored.getItems().addAll(iface.dpt.currentTutoring.getTutored());
        }
        else {
            for (Couple couple : iface.dpt.currentTutoring.affectations){
                iface.tutored.getItems().add(couple.getTutored());
                iface.tutors.getItems().add(couple.getTutor());
            }
            for (Student student : iface.dpt.currentTutoring.getWaitingList()){
                if (student.isTutored()){
                    iface.tutored.getItems().add(student);
                }
                else {
                    iface.tutors.getItems().add(student);
                }
            }
            

        }
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
