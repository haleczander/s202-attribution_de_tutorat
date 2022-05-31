package graphs.affectation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import oop.Student;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;

/**
 * Class that represents an assignment of two groups of students : one is
 * tutored, the other is tutoring. Encapsulates the {@code CalculAffectation}
 * class to add a level of abstraction and hide data processing and heaving
 * calculations.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 * @see #Assignment(List, List)
 * @see #getAssignment()
 * @see fr.ulille.but.sae2_02.graphes.CalculAffectation
 */
public class Assignment {

    /**
     * a list of tutored students to process the assignment with.
     */
    private List<Tutored> tutored;

    /**
     * a list of tutor students to process the assignment with.
     */
    private List<Tutor> tutors;

    /**
     * whether the tutors will be split if needed or not.
     */
    private boolean polyTutor;

    /**
     * the waiting list in case there are too many students.
     */
    private List<Student> waitingList;

    /**
     * Map of all duos who should be assigned together.
     */
    private Map<Tutored, Tutor> forcedAssignments;

    /**
     * Map of all duos who should not be assigned together.
     */
    private Map<Tutored, Tutor> forbiddenAssignments;

    /**
     * cost of the assignment.
     */
    private double assignmentCost;

    /**
     * Teacher in charge of the assignment.
     */
    private Teacher teacher;

    private Assignment() {
        this.polyTutor = true;
        this.forcedAssignments = new HashMap<>();
        this.forbiddenAssignments = new HashMap<>();
        this.waitingList = new ArrayList<>();
        this.assignmentCost = 0;
        this.teacher = null;
    }

    /**
     * Constructs an assignment with supplied tutored and tutors lists.
     * 
     * @param tutored List of tutored students that will be taken in charge.
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     */
    public Assignment(List<Tutored> tutored, List<Tutor> tutors) {
        this();
        this.tutored = tutored;
        this.tutors = tutors;
    }

    /**
     * Constructs an assignment with supplied students list. Student will be
     * dispatched automatically.
     * 
     * @param students List of students to dispatch.
     */
    public Assignment(List<Student> students) {
        this();
        dispatchStudents(students);
    }

    /**
     * Constructs an assignment with supplied students list and teacher. Students
     * will be dispatched automatically.
     * 
     * @param students List of students to dispatch.
     * @param teacher  Teacher overseeing the tutoring.
     */
    public Assignment(List<Student> students, Teacher teacher) {
        this(students);
        this.teacher = teacher;
    }

    /**
     * Constructs an assignment with supplied lists of tutored and tutors and
     * teacher.
     * 
     * @param tutored List of tutored students that will be taken in charge.
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     * @param teacher Teacher overseeing the tutoring.
     */
    public Assignment(List<Tutored> tutored, List<Tutor> tutors, Teacher teacher) {
        this(tutored, tutors);
        this.teacher = teacher;
    }

    /**
     * Constructs an assignment with a teacher.
     * 
     * @param teacher Teacher overseeing the tutoring.
     */
    public Assignment(Teacher teacher) {
        this();
        this.teacher = teacher;
    }

    /**
     * Adds a duo of tutored and tutor student to assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */
    public void addForcedAssignments(Tutored tutored, Tutor tutor) {
        if (this.forcedAssignments.containsKey(tutored)) {
            throw new IllegalArgumentException("This forced assignment already exists.");
        }
        this.forcedAssignments.put(tutored, tutor);
    }

    /**
     * Adds a duo of tutored and tutor students to not assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */
    public void addForbiddenAssignments(Tutored tutored, Tutor tutor) {
        if (this.forbiddenAssignments.containsKey(tutored)) {
            throw new IllegalArgumentException("This forbidden assignment already exists.");
        }
        this.forbiddenAssignments.put(tutored, tutor);
    }

    /**
     * Removes a forced assigment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forced
     *                                  assignment with anyone.
     */
    public void removeForcedAssignment(Tutored tutored) {
        if (!this.forcedAssignments.containsKey(tutored)) {
            throw new IllegalArgumentException("This forced assignment does not exist.");
        }
        this.forcedAssignments.remove(tutored);
    }

    /**
     * Removes a forbidden assignment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forbidden
     *                                  assignment with anyone.
     */
    public void removeForbiddenAssignment(Tutored tutored) {
        if (!this.forbiddenAssignments.containsKey(tutored)) {
            throw new IllegalArgumentException("This forbidden assignment does not exist.");
        }
        this.forbiddenAssignments.remove(tutored);
    }

    public void removeStudent(Tutor student) {
        this.tutors.remove(student);
    }

    public void removeStudent(Tutored student) {
        this.tutored.remove(student);
    }

    /**
     * Sets whether or not tutors should be split if needed. Default value is true.
     * 
     * @param polyTutor true if tutors should be doubled, false otherwise.
     */
    public void setPolyTutor(boolean polyTutor) {
        this.polyTutor = polyTutor;
    }

    /**
     * Toggles polytutor.
     */
    public void togglePolyTutor() {
        this.polyTutor = !this.polyTutor;
    }

    /**
     * Returns {@code true} if the polytutor is activated on this assignment, false
     * otherwise.
     * 
     * @return {@code true} if polytutor is on.
     */
    public boolean isPolyTutorOn() {
        return this.polyTutor;
    }

    /**
     * Dispatches all students in the correct list : tutored or tutors.
     * 
     * @param students a list of all students to dispatch.
     */
    private void dispatchStudents(List<Student> students) {
        for (Student s : students) {
            addStudent(s);
        }
    }

    /**
     * Adds a student in the correct list.
     * 
     * @param student Student to add to the assignment.
     * @return true if student was added, false otherwise.
     */
    public boolean addStudent(Student student) {
        if (this.tutored.contains(student) || this.tutors.contains(student)) {
            return false;
        }
        if (student.getLevel() == 1) {
            return this.tutored.add((Tutored) student);
        } else {
            return this.tutors.add((Tutor) student);
        }
    }

    /**
     * Creates an assignment from 2 lists of students of the object.
     * Steps of the method :
     * 1. computes the weight of all students.
     * 2. duplicates the lists of students to work with them.
     * 3. arrages the lists of students
     * 4. builds a graph from both new lists.
     * 5. compute the assignment, saves the cost of it, then returns the assignment.
     * 
     * @return the resulting assignment
     * 
     * @see #listArrangeTutor std1 = new Tutor("A", 12.1, 2, 3, 'A');
     */
    private CalculAffectation<Student> assignment() {
        this.waitingList.clear();
        computeStudentsWeight();

        List<Tutored> duplicateTutored = new ArrayList<>();
        duplicateTutored.addAll(this.tutored);

        List<Tutor> duplicateTutor = new ArrayList<>();
        duplicateTutor.addAll(this.tutors);

        listArrange(duplicateTutored, duplicateTutor);

        GrapheNonOrienteValue<Student> graph = graphSetup(duplicateTutored, duplicateTutor);

        CalculAffectation<Student> calcul = new CalculAffectation<>(graph, Tools.getStudentList(duplicateTutored),
                Tools.getStudentList(duplicateTutor));

        this.assignmentCost = calcul.getCout();

        return calcul;
    }

    /**
     * Returns an undirected weighted graph (bipartite graph) from 2 lists of
     * students.
     * 
     * @return the resulting graph.
     * 
     * @see Tutored#getWeight(double, double)
     * @see Tutor#getWeight(double, double)
     */
    private GrapheNonOrienteValue<Student> graphSetup(List<Tutored> duplicateTutored, List<Tutor> duplicateTutor) {
        GrapheNonOrienteValue<Student> graph = new GrapheNonOrienteValue<>();

        addVertices(graph, duplicateTutored);
        addVertices(graph, duplicateTutor);

        double weight;

        for (Tutored tutored : duplicateTutored) {
            for (Tutor tutor : duplicateTutor) {
                if (this.forcedAssignments.containsKey(tutored) && this.forcedAssignments.get(tutored).equals(tutor)) {
                    weight = -1000;
                } else if (this.forbiddenAssignments.containsKey(tutored)
                        && this.forbiddenAssignments.get(tutored).equals(tutor)) {
                    weight = 1000;
                } else {
                    weight = tutored.getWeight() + tutor.getWeight();
                }

                graph.ajouterArete(tutored, tutor, weight);
            }
        }
        return graph;
    }

    /**
     * Adds all students that are in the list given as nodes in a given graph.
     * 
     * @param graph graph to add nodes to.
     * @param list  students to add to the graph.
     */
    private void addVertices(GrapheNonOrienteValue<Student> graph, List<? extends Student> list) {
        for (Student student : list) {
            graph.ajouterSommet(student);
        }
    }

    /**
     * Modifies the lists of students of the object in order to have 2 lists of
     * the same size and suitable for an assignment.
     * 
     * @see Tools#tutorsSplit(List, int)
     * @see Tools#waitingListBuilder(List, int)
     */
    private void listArrange(List<Tutored> tutored, List<Tutor> tutor) {
        int diff = tutored.size() - tutor.size();

        if (polyTutor) {
            tutor = Tools.tutorsSplit2(tutor, diff);
        }

        diff = tutored.size() - tutor.size();
        if (diff > 0) {
            // tutored in waiting list
            waitingList = Tools.waitingListBuilder(tutored, Math.abs(diff));
        } else if (diff < 0) {
            // tutors in waiting list
            waitingList = Tools.waitingListBuilder(tutor, Math.abs(diff));
        }

        diff = tutored.size() - tutor.size();
        if (diff != 0) {
            throw new IllegalArgumentException("marche pas dommage");
        }
    }

    private void computeStudentsWeight() {
        computeStudentWeight(this.tutored);
        computeStudentWeight(this.tutors);
    }

    /**
     * Calculates the average of all students in a given list, then sets the weight
     * to all students in the list.
     * 
     * @param list list of students.
     * 
     * @see Tools#computeAverage(List)
     * @see Tools#computeAbsenceAverage(List)
     * @see Student#setWeight(double, double)
     */
    private void computeStudentWeight(List<? extends Student> list) {
        double avgAvg = Tools.computeAverage(list);
        double absAvg = Tools.computeAbsenceAverage(list);
        for (Student s : list) {
            s.setWeight(avgAvg, absAvg);
        }
    }

    /**
     * Method that returns a textual representation of the assignment. Includes a
     * waiting list if there is one.
     * 
     * @param getGraph includes a textual representation of the bipartite graphs if
     *                 true.
     * @return the assignment as text.
     */
    public String getTextAssignment() {
        StringBuilder string = new StringBuilder();
        string.append("Arêtes:\t\t " + this.getAssignment().toString().replaceAll("Arete", "") + "\n");
        if (!waitingList.isEmpty()) {
            string.append("En attente:\t " + this.waitingList + "\n");
        }
        string.append("Coût total:\t " + (double) (((int) (1000 * this.getCost())) / 1000.00) + "\n\n");
        return string.toString();
    }

    /**
     * Method that gives acces to an <strong>immutable</strong> copy of a list of
     * edges of students that represent an assignment.
     * 
     * @return a copy of the assignment.
     */
    public List<Arete<Student>> getAssignment() {
        return List.copyOf(this.assignment().getAffectation());
    }

    /**
     * Method that returns an <strong>immutable</strong> copy of the waiting list of
     * an assignment.
     * 
     * @return a copy of the waiting list.
     */
    public List<Student> getWaitingList() {
        return List.copyOf(this.waitingList);
    }

    /**
     * Method that returns a textual representation of the minimal cost of the
     * assignment.
     * 
     * @return minimal cost.
     */
    public String getTextCost() {
        return "cost: " + getCost();
    }

    /**
     * Returns the minimal cost of the assignment.
     * 
     * @return minimal cost.
     */
    public double getCost() {
        return this.assignmentCost % 1000 + (this.assignmentCost % 1000 < 0 ? 1000 : 0);
    }

    public void printScenario(String id, String title) {
        System.out.println("\033[4m" + "Cas " + id + " : " + title + "\033[0m");
        System.out.println(this.getTextAssignment());
    }

    @Override
    public String toString() {
        return "[Tuteurs: " + this.tutors.size() + ", Tutorés: " + this.tutored.size() + ", Attente: "
                + this.waitingList.size() + "]";
    }
}
