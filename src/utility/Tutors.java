package utility;

import java.util.ArrayList;
import java.util.List;

import graphs.affectation.Tutorat;
import oop.Student;
import oop.StudentComparator;
import oop.Tutor;
import oop.Tutored;

public class Tutors {
        /**
     * Duplicates up to all the tutors that can take multiple students in charge in a
     * given list. Number of splits is determined by the number of tutored students
     * without an available tutors. Splits are done after list has been sorted.
     * Returns the list with potential student splits. Current max number of tutored
     * students that a tutor can take in charge is 2.
     * 
     * @param list List of tutors to process.
     * @param diff Number of potential splits.
     * @return List of tutors with all available or necessary splits.
     * 
     * @see #gapClose(List, int)
     * @see Tutored#compareTo(Student)
     * @see Tutor#compareTo(Student)
     * @see Tutor#duplicate()
     */
    public static List<Tutor> gapClose(List<Tutor> list, Tutorat tutorat, int diff) {
        // list.sort((s1, s2) -> s1.compareTo(s2));
        list.sort(new StudentComparator(tutorat));
        List<Tutor> toAdd = new ArrayList<>();
        for (Tutor t : list) {
            if (t.getNbofTutored() > 1) {
                toAdd.add(t.duplicate());
                diff--;
            }
            if (diff == 0) {
                break;
            }
        }
        list.addAll(toAdd);
        return list;
    }
}
