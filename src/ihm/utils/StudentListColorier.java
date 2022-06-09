package ihm.utils;

import javax.sql.rowset.spi.SyncResolver;

import ihm.Interface;
import ihm.events.Events;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import oop.Student;
import oop.Tutored;
import utility.Couples;

public class StudentListColorier implements Callback<ListView<Student>,ListCell<Student>> {
    Interface iface;

    public StudentListColorier(Interface iface) {
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
                    setOnMousePressed(e -> iface.draggedStudent = item);
                    setOnMouseReleased(e -> {if (!cursorContained(e, iface)) iface.draggedStudent=null;});
                    setOnMouseEntered(e -> {if (iface.draggedStudent!=null) Events.DragNDropHandler(iface, item);});


                    if (item instanceof Tutored &&  iface.dpt.currentTutoring.affectations.size()>0) {
                        if (Couples.containsStudent(iface.dpt.currentTutoring.affectations, item)){
                            setTextFill(Color.GREEN);
                        }
                        else {
                            setTextFill(Color.RED);
                        }
                    }
                    else {
                        setTextFill(Color.BLACK);
                    }
                    
                setText(item.getName());
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
    

