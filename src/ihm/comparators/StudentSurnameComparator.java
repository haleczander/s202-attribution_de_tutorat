package ihm.comparators;

import java.util.Comparator;

import oop.Student;

public class StudentSurnameComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        int diff = o1.getSurname().compareTo(o2.getSurname());
        if ( diff == 0 ) {
            return o1.getForename().compareTo(o2.getForename());
        }
        return diff;
    }

}
