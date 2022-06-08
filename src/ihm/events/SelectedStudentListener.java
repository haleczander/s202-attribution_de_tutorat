package ihm.events;

import ihm.Interface;
import javafx.collections.ListChangeListener;
import oop.Student;

public class SelectedStudentListener implements ListChangeListener<Student> {
    Interface iface;

    public SelectedStudentListener(Interface iface) {
        this.iface = iface;
    }

    public void onChanged(Change<? extends Student> c) {
        if (c.getList().size()>0) {
            iface.selectedStudent = c.getList().get(0);
        }
        
    }

}