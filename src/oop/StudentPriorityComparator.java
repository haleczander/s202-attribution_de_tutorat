package oop;

import java.util.Comparator;

import graphs.affectation.Assignment;
import graphs.affectation.Couple;

public class StudentPriorityComparator implements Comparator<Student> {
    Assignment tutorat;

    public StudentPriorityComparator(Assignment tutorat){
        this.tutorat= tutorat;
    }

    @Override
    public int compare(Student o1, Student o2) {
        if (Couple.containsStudent(tutorat.getForcedCouples(), o1)) {
            return -1;
        }
        if (Couple.containsStudent(tutorat.getForcedCouples(), o2)) {
            return 1;
        }
        return (int) (100* (o1.getWeight(tutorat.getResource()) - o2.getWeight(tutorat.getResource())));
    }
    
}
