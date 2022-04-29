package graphs.rapport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Utility class that regroups multiple static methods useful for list
 * streamlining, assignment, and retrieving information.
 */
public final class StreamlineUtils {
    private StreamlineUtils() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    /**
     * Returns a Student list from a list of any children class of Student.
     * 
     * @param list Student list to convert.
     * @return Resulting Student list.
     */
    public static List<Student> getEtudiantList(List<? extends Student> list) {
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
                if (tuteur.nbofTutored == 2) {
                    list.remove(tuteur);
                    list.add(new Tutor(tuteur.name + "α", tuteur.average * 1.5, tuteur.level, 1));
                    list.add(new Tutor(tuteur.name + "β", tuteur.average * 1.5, tuteur.level, 1));
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

    /**
     * Retrieves a student in a list of student given its name.
     * 
     * @param studentName the name to look for.
     * @param list the list to search.
     * @return the student associated with the name.
     * @throws IllegalArgumentException if there was no match between a name and the students in the list.
     */
    public static Student retrieveStudent(String studentName, List<? extends Student> list) {
        for (Student student : list) {
            if (student.getName() == studentName) {
                return student;
            }
        }
        throw new IllegalArgumentException("Student name could not be found");
    }
}