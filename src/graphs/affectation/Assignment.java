package graphs.affectation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import oop.Resource;
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
    private Resource resource;

    private List<Tutored> tutored;
    private List<Tutor> tutors;

    private boolean polyTutor;

    private List<Student> waitingList;

    private Set<Edge> forcedAssignments;
    private Set<Edge> forbiddenAssignments;

    private double assignmentCost;

    private double tutoredGradesAverage;
    private double tutorGradesAverage;

    private double tutoredAbsenceAverage;
    private double tutorAbsenceAverage;

    private Teacher teacher;

    private Assignment(Resource resource) {
        this.polyTutor = true;
        this.forcedAssignments = new HashSet<>();
        this.forbiddenAssignments = new HashSet<>();
        this.waitingList = new ArrayList<>();
        this.assignmentCost = 0;
        this.teacher = null;

        this.tutoredGradesAverage = 0;
        this.tutorGradesAverage = 0;

        this.tutoredAbsenceAverage = 0;
        this.tutorAbsenceAverage = 0;

        this.resource = resource;
    }

    /**
     * Constructs an assignment with supplied tutored and tutors lists.
     * 
     * @param tutored List of tutored students that will be taken in charge.
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     */
    public Assignment(List<Tutored> tutored, List<Tutor> tutors, Resource resource) {
        this(resource);
        this.tutored = tutored;
        this.tutors = tutors;
        updateAverages();
    }

    /**
     * Constructs an assignment with supplied students list. Student will be
     * dispatched automatically.
     * 
     * @param students List of students to dispatch.
     */
    public Assignment(Set<Student> students, Resource resource) {
        this(resource);
        addStudent(students);
        updateAverages();
    }

    /**
     * Constructs an assignment with supplied students list and teacher. Students
     * will be dispatched automatically.
     * 
     * @param students List of students to dispatch.
     * @param teacher  Teacher overseeing the tutoring.
     */
    public Assignment(Set<Student> students, Teacher teacher, Resource resource) {
        this(students, resource);
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
    public Assignment(List<Tutored> tutored, List<Tutor> tutors, Teacher teacher, Resource resource) {
        this(tutored, tutors, resource);
        this.teacher = teacher;
    }

    /**
     * Constructs an assignment with a teacher.
     * 
     * @param teacher Teacher overseeing the tutoring.
     */
    public Assignment(Teacher teacher, Resource resource) {
        this(resource);
        this.teacher = teacher;
    }

    private void updateAverages() {
        double[] values = Assignment.computeAverages(tutored, resource);
        this.tutoredAbsenceAverage = values[0];
        this.tutoredGradesAverage = values[1];

        values = Assignment.computeAverages(tutors, resource);
        this.tutorAbsenceAverage = values[0];
        this.tutorGradesAverage = values[1];
    }

    private static double[] computeAverages(List<? extends Student> students, Resource resource) {
        int abs = 0;
        double avg = 0;
        for (Student student : students) {
            abs += student.getAbsences();
            avg += student.getGrade(resource);
        }
        return new double[] { abs / students.size(), avg / students.size() };
    }

    /**
     * Adds a duo of tutored and tutor student to assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */
    public boolean addForcedAssignments(Tutored tutored, Tutor tutor) {
        return this.forcedAssignments.add(new Edge(tutored, tutor));
    }

    /**
     * Adds a duo of tutored and tutor students to not assign together.
     * 
     * @param tutored the tutored student.
     * @param tutor   the tutor student.
     * 
     * @throws IllegalArgumentException if this duo has already been put in the map.
     */
    public boolean addForbiddenAssignments(Tutored tutored, Tutor tutor) {
        return this.forbiddenAssignments.add(new Edge(tutored, tutor));
    }

    /**
     * Removes a forced assigment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forced
     *                                  assignment with anyone.
     */
    public boolean removeForcedAssignment(Tutored tutored, Tutor tutor) {
        return this.forcedAssignments.remove(new Edge(tutored, tutor));
    }

    /**
     * Removes a forbidden assignment for a tutored student.
     * 
     * @param tutored the tutored student.
     * 
     * @throws IllegalArgumentException if the tutored student has no forbidden
     *                                  assignment with anyone.
     */
    public boolean removeForbiddenAssignment(Tutored tutored, Tutor tutor) {
        return this.forbiddenAssignments.remove(new Edge(tutored, tutor));
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
    public void addStudent(Set<Student> students) {
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

        if (!student.getGrades().containsKey(this.resource)) {
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

        for (Tutored tutoreds : duplicateTutored) {
            for (Tutor tutor : duplicateTutor) {
                Edge duo = new Edge(tutoreds, tutor);

                if (this.forcedAssignments.contains(duo)) {
                    weight = -1000;
                } else if (this.forbiddenAssignments.contains(duo)) {
                    weight = 1000;
                } else {
                    weight = tutoreds.getWeight(resource, tutoredGradesAverage, (int) tutoredAbsenceAverage,
                            teacher.getAverageWeighting(), teacher.getAbsenceWeighting(), teacher.getLevelWeighting())
                            + tutor.getWeight(resource, tutorGradesAverage, (int) tutorAbsenceAverage,
                                    teacher.getAverageWeighting(), teacher.getAbsenceWeighting(),
                                    teacher.getLevelWeighting());
                }

                graph.ajouterArete(tutoreds, tutor, weight);
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
            // tutor = Tools.tutorsSplit(tutor, diff);
            tutor = Tools.tutorsSplit(tutor, this.resource, diff);
        }

        diff = tutored.size() - tutor.size();
        if (diff > 0) {
            // tutored in waiting list
            // waitingList = Tools.waitingListBuilder(tutored, Math.abs(diff));
            waitingList = Tools.waitingListBuilder(tutored, this.resource, Math.abs(diff));
        } else if (diff < 0) {
            // tutors in waiting list
            // waitingList = Tools.waitingListBuilder(tutor, Math.abs(diff));
            waitingList = Tools.waitingListBuilder(tutor, this.resource, Math.abs(diff));
        }

        diff = tutored.size() - tutor.size();
        if (diff != 0) {
            throw new IllegalArgumentException("marche pas dommage");
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
