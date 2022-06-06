package oop;

import java.util.Comparator;

import graphs.affectation.Tutorat;

public class StudentComparator implements Comparator<Student> {
    Tutorat tutorat;

    public StudentComparator(Tutorat tutorat){
        this.tutorat= tutorat;
    }

    @Override
    public int compare(Student o1, Student o2) {
        return (int) (100* (o1.getWeight(tutorat.getResource()) - o2.getWeight(tutorat.getResource())));
    }
    
}
