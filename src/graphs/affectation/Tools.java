package graphs.affectation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import fr.ulille.but.sae2_02.graphes.Arete;

/**
 * Utility class that regroups multiple static methods useful for list
 * optimisation, assignments, and retrieving information. Those method were
 * seperated from the {@code Assignment.java} class for multiple reasons :
 * methods might be reused later in the application, and these methods do not
 * fundamentally work with the graph itself, they only help with data
 * processing.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 * @see Assignment
 * 
 */
public final class Tools {
    private Tools() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    /**
     * Returns a list of Students from a list of any children class of Student.
     * 
     * @param list Student list to convert.
     * @return Resulting Student list.
     */
    public static List<Student> getStudentList(List<? extends Student> list) {
        List<Student> students = new ArrayList<>();
        students.addAll(list);
        return students;
    }

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
     * @see #tutorsSplit(List, int)
     * @see Tutored#compareTo(Student)
     * @see Tutor#compareTo(Student)
     * @see Tutor#duplicate()
     */
    public static List<Tutor> tutorsSplit(List<Tutor> list, int diff) {
        list.sort((s1, s2) -> s1.compareTo(s2));
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

    /**
     * Orders a given list of student, then removes the last {@code diff} students
     * of this list, placing them in a waiting list that is returned. Order is
     * arbitrary. Order does not change after building the waiting list as it does
     * not impact the assignment.
     * 
     * @param list list to sort and build the waiting list from.
     * @param diff number of students to remove.
     * @return The waiting list of students.
     * 
     * @see Tutored#compareTo(Student)
     * @see Tutor#compareTo(Student)
     */
    public static List<Student> waitingListBuilder(List<? extends Student> list, int diff) {
        List<Student> waitingList = new ArrayList<>();
        list.sort((s1, s2) -> s1.compareTo(s2));

        ListIterator<? extends Student> it = list.listIterator(list.size());
        while (it.hasPrevious() && diff != 0) {
            Student student = it.previous();
            waitingList.add(student);
            diff--;
        }
        for (Student student : waitingList) {
            list.remove(student);
        }

        return waitingList;
    }

    /**
     * Static method that returns a numerical value of a character representing the
     * motivation of a student.
     * 
     * @param motivation A, B, or C the motivation of the student.
     * @return a value to assess the motivation of a student.
     * @throws IllegalArgumentException if the motivation character is not A, B or
     *                                  C.
     */
    public static double motivationValue(char motivation) throws IllegalArgumentException {
        if (motivation < 'A' && motivation > 'C') {
            throw new IllegalArgumentException("The motivation character is not valid (A, B or C).");
        }
        return 1 + (motivation - 'B') * 0.1;
    }

    /**
     * Calculates the average from all students in a given list.
     * 
     * @param list students to get the average from.
     * @return the average of all students.
     */
    public static double computeAverage(List<? extends Student> list) {
        double sum = 0;
        for (Student student : list) {
            sum += student.getAverage();
        }
        return sum / list.size();
    }

    /**
     * Calculates the average of absences from all students in a given list.
     * 
     * @param list students to get the average of absences from.
     * @return the average of absences of all students.
     */
    public static double computeAbsenceAverage(List<? extends Student> list) {
        double sum = 0;
        for (Student student : list) {
            sum += student.getAbsences();
        }
        return sum / list.size();
    }

    /**
     * Tests if the toString method of 2 edges return the same text. Used as an
     * {@code equals} method to test edges equality.
     * 
     * @param edge1 first edge.
     * @param edge2 second edge.
     * @return true if text is equals, false otherwise.
     */
    public static boolean edgeTextEquals(Arete<Student> edge1, Arete<Student> edge2) {
        return edge1.toString().equals(edge2.toString());
    }
}