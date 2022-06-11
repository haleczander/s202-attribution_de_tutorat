package ihm.utils;

import graphs.Couple;
import graphs.Tutoring;
import ihm.Interface;
import ihm.events.TutoringSelectorListener;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oop.Resource;
import oop.Student;

public class TutoringUtils {  


    public static void updateLists(Interface iface) {
        iface.tutors.getItems().clear();
        iface.tutored.getItems().clear();
        if (iface.dpt.currentTutoring.affectations.size() ==0 ){
            iface.scrollBarOne.valueProperty().unbindBidirectional(iface.scrollBarTwo.valueProperty());
            iface.tutors.getItems().addAll(iface.dpt.currentTutoring.getTutors()); 
            iface.tutored.getItems().addAll(iface.dpt.currentTutoring.getTutored());
        }
        else {
            iface.scrollBarOne.valueProperty().bindBidirectional(iface.scrollBarTwo.valueProperty());
            for (Couple couple : iface.dpt.currentTutoring.affectations){
                iface.tutored.getItems().add(couple.getTutored());
                iface.tutors.getItems().add(couple.getTutor());
            }
            for (Student student : iface.dpt.currentTutoring.getWaitingList()){
                if (student.isTutored()){
                    iface.tutored.getItems().add(student);
                    iface.tutors.getItems().add(null);
                }
                else {
                    iface.tutors.getItems().add(student);
                    iface.tutored.getItems().add(null);
                }
            }
            

        }
        updateTutoringInfos(iface);
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

    public static void updateTutoringInfos(Interface iface){
        if (iface.dpt.currentTutoring == null){
            iface.tutoringTeacherLb.setText(null); 
            iface.tutoringAffectedLb.setText(null); 
            iface.tutoringForcedLb.setText(null); 
            iface.tutoringForbiddenLb.setText(null); 
            iface.tutoringAwaitingLb.setText(null); 
            iface.tutoringTutorNbLb.setText("?");             
            iface.tutoringTutoredNbLb.setText("?"); 

        }
        else {
            Tutoring tut = iface.dpt.currentTutoring;
            iface.tutoringTeacherLb.setText(tut.getTeacher().toString());
            int ratio = (int) (100.00*( (double)tut.affectations.size() / tut.getTutored().size()));
            iface.tutoringAffectedLb.setText(ratio + "%");
            iface.tutoringAffectedLb.setTextFill( ratio == 100 ? Color.GREEN : Color.BLACK);
            int att = tut.getTutored().size() - tut.affectations.size();
            iface.tutoringForcedLb.setText(""+tut.getForcedCouples().size());
            iface.tutoringForbiddenLb.setText(""+tut.getForbiddenCouples().size());
            iface.tutoringAwaitingLb.setText(""+att); 
            iface.tutoringAwaitingLb.setTextFill(att > 0 ? Color.RED : Color.BLACK);
            iface.tutoringTutorNbLb.setText(""+tut.getTutors().size());             
            iface.tutoringTutoredNbLb.setText(""+tut.getTutored().size());
        }
        
    }
    static String lineSeparator(String str){
        StringBuilder sb = new StringBuilder();
        for (int i =0 ; i< str.length() ; i++){
            sb.append("﹘");
        }
        return sb.toString();
    }

    public static String getStudentLabel(Student item, Interface iface) {
        Resource resource=iface.dpt.currentTutoring.getResource();
        return 
        item.getName() + "\t(" + (item.isTutored()? "Tutoré" : "Tuteur") + ")"
        + " \n" + 
        lineSeparator(item.getName() + "\t(" + (item.isTutored()? "Tutoré" : "Tuteur") + ")")
        + " \nNotes de " + resource.getName() + " :\t" + item.getGrade(resource)  
        + " \nAbsences :\t\t" + item.getAbsences()
        + " \nAnnée :\t\t\t" + item.getLevel() 
        + " \nMotivation :\t\t" + item.getMotivation();

    }
}
