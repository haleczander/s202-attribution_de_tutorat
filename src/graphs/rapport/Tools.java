package graphs.rapport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Utility class that regroups multiple static methods useful for list
 * streamlining, assignments, and retrieving information. Those method were
 * seperated from the {@code Assignment.java} class for multiple reasons :
 * methods might be reused later in the application, and these methods do not
 * fundamentally work with the graph itself, they only help with data
 * processing.
 * 
 * @author Léopold V.
 * @see Assignment
 * 
 */
public final class Tools {
    private Tools() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    /**
     * Returns a Student list from a list of any children class of Student.
     * 
     * @param list Student list to convert.
     * @return Resulting Student list.
     */
    public static List<Student> getStudentList(List<? extends Student> list) {
        List<Student> students = new ArrayList<>();
        for (Student student : list) {
            students.add(student);
        }
        return students;
    }

    /**
     * Splits up to all the tutors that can take multiple students in charge in a
     * given list. Number of splits is determined by the number of tutored students
     * without an available tutors. Order of splits is random. Returns the list with
     * potential student splits. Current max number of tutored students that a tutor
     * can take in charge is 2, thinking about going up to 3.
     * 
     * @param list List of tutors to process.
     * @param diff Number of potential splits.
     * @return List of tutors with all available or necessary splits.
     */
    public static List<Tutor> tutorsSplit(List<Tutor> list, int diff) {
        boolean noMoreAvailableStudents = false;
        while (diff > 0 && !noMoreAvailableStudents) {
            Iterator<Tutor> it = list.iterator();
            while (it.hasNext()) {
                Tutor tuteur = it.next();
                if (tuteur.getNbofTutored() == 2) {
                    list.remove(tuteur);
                    list.add(new Tutor(tuteur.getName() + "(α)", tuteur.average * 1.5, tuteur.level, tuteur.absences,
                            tuteur.motivation, 1));
                    list.add(new Tutor(tuteur.getName() + "(β)", tuteur.average * 1.5, tuteur.level, tuteur.absences,
                            tuteur.motivation, 1));
                    break;
                }
                if (!it.hasNext()) {
                    noMoreAvailableStudents = true;
                    break;
                }
            }
            diff--;
        }
        return list;
    }

    /**
     * Orders a given list of student, then removes the last {@code diff} students
     * of this list, placing them in a waiting list that is returned. Order is
     * arbitrary. Order does not change after building the waiting list as it does
     * not impact the assignment.
     * 
     * @see graphs.rapport.Tutored#compareTo(Student)
     * @see graphs.rapport.Tutor#compareTo(Student)
     * 
     * @param list list to sort and build the waiting list from.
     * @param diff number of students to remove.
     * @return The waiting list of
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

    // /**
    // * Retrieves a student in a list of student given its name.
    // *
    // * @param studentName the name to look for.
    // * @param list the list to search.
    // * @return the student associated with the name.
    // * @throws IllegalArgumentException if there was no match between a name and
    // the
    // * students in the list.
    // * @deprecated
    // */
    // public static Student retrieveStudent(String studentName, List<? extends
    // Student> list) {
    // for (Student student : list) {
    // if (student.getName() == studentName) {
    // return student;
    // }
    // }
    // throw new IllegalArgumentException("Student name could not be found");
    // } useless. je la garde en artefact parce que l'aimais bien cette méthode :(

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
        switch (motivation) {
            case 'A':
                return 0.8;
            case 'B':
                return 1;
            case 'C':
                return 1.2;
            default:
                throw new IllegalArgumentException("The motivation character is not valid (A, B or C).");
        }
    }

    /**
     * Calculates the average from all students in a given list.
     * 
     * @param list students to get the average from.
     * @return the average of all students.
     */
    public static double getAverage(List<? extends Student> list) {
        int sum = 0;
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
    public static double getAbsenceAverage(List<? extends Student> list) {
        int sum = 0;
        for (Student student : list) {
            sum += student.getAbsences();
        }
        return sum / list.size();
    }

    /**
     * Searches for 2 students (one tutored student: the key, and one tutor student:
     * the value) through the entry set of a given Map.
     * 
     * @param map mapping of Tutored students and Tutor students.
     * @param tutored tutored student to look for.
     * @param tutor tutor student to look for.
     * @return true if the association 'tutored:tutor' exists, false otherwise.
     */
    public static boolean areStudentsInMap(Map<Tutored, Tutor> map, Tutored tutored, Tutor tutor) {
        for (Map.Entry<Tutored, Tutor> entrySet : map.entrySet()) {
            if (entrySet.getKey().equals(tutored) && entrySet.getValue().equals(tutor)) {
                return true;
            }
        }
        return false;
    }
}