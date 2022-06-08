package ihm.events;

import java.util.Collections;
import java.util.List;

import ihm.Interface;
import ihm.comparators.StudentAbsencesComparator;
import ihm.comparators.StudentAverageComparator;
import ihm.comparators.StudentForenameComparator;
import ihm.comparators.StudentMotivationComparator;
import ihm.comparators.StudentSurnameComparator;
import ihm.utils.TutoringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import oop.Student;

public class SortListHandler implements EventHandler<ActionEvent> {
    Interface iface;
    String filter;

    public SortListHandler(Interface iface, String filter) {
        this.iface = iface;
        this.filter = filter;
    }

    public void handle(ActionEvent e) {
        List<? extends Student> list = (iface.filterTutored) ? iface.dpt.currentTutoring.getTutored() : iface.dpt.currentTutoring.getTutors();
        System.out.println("Filter tutored :" + iface.filterTutored);
        switch(filter){
            case "nom" :
                list.sort(new StudentSurnameComparator());
                break;
            case "pre" :
                list.sort(new StudentForenameComparator());
                break;
            case "avg" :
                list.sort(new StudentAverageComparator(iface.dpt.currentTutoring.getResource()));
                break;
            case "abs" :
                list.sort(new StudentAbsencesComparator());
                break;
            case "mot" :
                list.sort(new StudentMotivationComparator());
                break;
        }
        if (((Button) e.getTarget()).getText().equals("▲")){
            Collections.reverse(list);
        }
        TutoringUtils.updateLists(iface);
        
    }
}
