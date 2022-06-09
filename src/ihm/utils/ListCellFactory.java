package ihm.utils;

import ihm.Interface;
import ihm.events.Events;
import ihm.events.SelectedStudentListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import oop.Student;
import oop.Tutor;
import utility.Couples;

public class ListCellFactory implements Callback<ListView<Student>,ListCell<Student>> {
    Interface iface;


    public ListCellFactory(Interface iface) {
        this.iface = iface;
    }

    public ListCell<Student> call(ListView<Student> listview) {
        return new StudentColorier(iface);
    }

    static class StudentColorier extends ListCell<Student> {
        Interface iface;
        StudentColorier(Interface iface){
            this.iface = iface;
        }
        @Override
        public void updateItem(Student item, boolean empty) {
            super.updateItem(item, empty);
                if (item != null) {
                    setOnMouseClicked( e -> new SelectedStudentListener(iface));
                    setOnMousePressed(e -> iface.draggedStudent = item);
                    setOnMouseReleased(e -> {if (!cursorContained(e, iface)) iface.draggedStudent=null;});
                    setOnMouseEntered(e -> {if (iface.draggedStudent!=null) Events.DragNDropHandler(e, iface, item);});


                    if (item.isTutored() &&  iface.dpt.currentTutoring.affectations.size()>0) {
                        if (Couples.containsStudent(iface.dpt.currentTutoring.affectations, item)){
                            setTextFill(Color.GREEN);
                        }
                        else {
                            setTextFill(Color.RED);
                        }
                    }
                    else {
                        if (!item.isTutored() && ((Tutor)item).isDuplicate()){
                            setTextFill(Color.PURPLE);
                        }
                        else {
                            setTextFill(Color.BLACK);
                        }
                    }
                    
                    setText(item.getName());
                }
                else {
                    setText(null);
                }
        }
    }

    static boolean cursorContained(MouseEvent e, Interface iface){
        return cursorContained(e, iface.tutored) || cursorContained(e, iface.tutors);
    }

    static boolean cursorContained(MouseEvent e, ListView<Student> list){
        return list.localToScene(list.getBoundsInLocal()).contains(e.getSceneX(), e.getSceneY());

    }
}
    

