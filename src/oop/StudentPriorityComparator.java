package oop;

import java.util.Comparator;

import graphs.affectation.Tutorat;
import utility.Couples;

public class StudentPriorityComparator implements Comparator<Student> {
    Tutorat tutorat;

    public StudentPriorityComparator(Tutorat tutorat){
        this.tutorat= tutorat;
    }

    @Override
    public int compare(Student o1, Student o2) {
        if (Couples.containsStudent(tutorat.getForcedCouples(), o1)) {
            return -1;
        }
        if (Couples.containsStudent(tutorat.getForcedCouples(), o2)) {
            return 1;
        }
        return (int) (100* (o1.getWeight(tutorat.getResource()) - o2.getWeight(tutorat.getResource())));
    }
    
}
