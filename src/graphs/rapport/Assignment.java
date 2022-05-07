package graphs.rapport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Class that represents an assignment of two groups of students : one is
 * tutored, the other is tutoring. Encapsulates the {@code CalculAffectation}
 * class to add a level of abstraction and hide data processing and heaving
 * calculations.
 * 
 * @author Léopold V.
 * @see #Assignment(List, List)
 * @see #getAssignment()
 * @see fr.ulille.but.sae2_02.graphes.CalculAffectation
 */
public class Assignment {
    /**
     * a list of tutored students to process the assignment with.
     */
    private List<Tutored> tutoredStudents;

    /**
     * a list of tutor students to process the assignment with.
     */
    private List<Tutor> tutorStudents;

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
    private Map<Tutored, Tutor> manualAssignments;

    /**
     * Map of all duos who should not be assigned together.
     */
    private Map<Tutored, Tutor> forbiddenAssignments;

    private double assignmentCost;


    private Assignment() {
        this.polyTutor = true;
        this.manualAssignments = new HashMap<>();
        this.forbiddenAssignments = new HashMap<>();
        this.waitingList = new ArrayList<>();
        this.assignmentCost = 0;
    }

    /**
     * Instantiation of an assignment.
     * 
     * @param tutored List of tutored students to have be taken in charge of. (?)
     * @param tutors  List of tutor students that will take in charge tutored
     *                students.
     */
    public Assignment(List<Tutored> tutored, List<Tutor> tutors) {
        this();

        this.tutoredStudents = tutored;
        this.tutorStudents = tutors;
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
        if (Tools.isTutoredKey(this.manualAssignments, tutored)) {
            throw new IllegalArgumentException("This forced assignment already exists.");
        } else {
            this.manualAssignments.put(tutored, tutor);
        }
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
        if (Tools.isTutoredKey(this.forbiddenAssignments, tutored)) {
            throw new IllegalArgumentException("This forbidden assignment already exists.");
        } else {
            this.forbiddenAssignments.put(tutored, tutor);
        }
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
        if (Tools.isTutoredKey(this.manualAssignments, tutored)) {
            this.manualAssignments.remove(tutored);
        } else {
            throw new IllegalArgumentException("This forced assignment does not exist.");
        }
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
        if (Tools.isTutoredKey(this.forbiddenAssignments, tutored)) {
            this.forbiddenAssignments.remove(tutored);
        } else {
            throw new IllegalArgumentException("This forbidden assignment does not exist.");
        }
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
     * Creates an assignment from 2 lists of students of the object.
     * 
     * @return the resulting assignment;
     */
    private CalculAffectation<Student> assignment() {
        this.waitingList.clear();
        setStudentWeight();

        List<Tutored> duplicateTutored = Tools.duplicateTutoredList(this.tutoredStudents);
        List<Tutor> duplicateTutor = Tools.duplicateTutorList(this.tutorStudents);

        listArrange(duplicateTutored, duplicateTutor);

        GrapheNonOrienteValue<Student> graph = graphSetup(duplicateTutored, duplicateTutor);

        CalculAffectation<Student> c = new CalculAffectation<>(graph, Tools.getStudentList(duplicateTutored),
        Tools.getStudentList(duplicateTutor));

        this.assignmentCost = c.getCout();

        return c;
    }

    /**
     * Method that returns a undirected weighted graph (bipartite graphs) from 2
     * lists of students of the object.
     * 
     * @return the resulting graph.
     * 
     * @see Tutored#getWeight(double, double)
     * @see Tutor#getWeight(double, double)
     * @see Tools#areStudentsInMap(Map, Tutored, Tutor)
     */
    private GrapheNonOrienteValue<Student> graphSetup(List<Tutored> duplicateTutored, List<Tutor> duplicateTutor) {
        GrapheNonOrienteValue<Student> graph = new GrapheNonOrienteValue<>();

        studentsAsEdges(graph, duplicateTutored);
        studentsAsEdges(graph, duplicateTutor);

        double weight;

        // System.out.println(tutoredAverageAvg);
        // System.out.println(tutoredAbsenceAvg);

        // System.out.println(tutorAverageAvg);
        // System.out.println(tutorAbsenceAvg + "\n");

        for (Tutored tutored : duplicateTutored) {
            for (Tutor tutor : duplicateTutor) {
                weight = tutored.getWeight()
                        + tutor.getWeight();

                if (Tools.areStudentsInMap(this.manualAssignments, tutored, tutor)) {
                    weight = -1000;
                }

                if (Tools.areStudentsInMap(this.forbiddenAssignments, tutored, tutor)) {
                    weight = 1000;
                }

                graph.ajouterArete(tutored, tutor, weight);
            }
        }
        return graph;
    }

    /**
     * Adds all students that are in the list given as edges in a graph.
     * 
     * @param graph graph to add edges to.
     * @param list  students to add to the graph.
     */
    private void studentsAsEdges(GrapheNonOrienteValue<Student> graph, List<? extends Student> list) {
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
    private void listArrange(List<Tutored> duplicateTutored, List<Tutor> duplicateTutor) {
        int diff =  duplicateTutored.size() - duplicateTutor.size();

        if (polyTutor) {
            duplicateTutor = Tools.tutorsSplit(duplicateTutor, diff);
        }

        diff = duplicateTutored.size() - duplicateTutor.size();
        if (diff > 0) {
            // tutored in waiting list
            waitingList = Tools.waitingListBuilder(duplicateTutored, Math.abs(diff));
        } else if (diff < 0) {
            // tutors in waiting list
            waitingList = Tools.waitingListBuilder(duplicateTutor, Math.abs(diff));
        }

        diff = duplicateTutored.size() - duplicateTutor.size();
        if (diff != 0) {
            throw new IllegalArgumentException("marche pas dommage");
        }
    }

    private void setStudentWeight() {
        double tutoredAverageAvg = Tools.getAverage(tutoredStudents);
        double tutoredAbsenceAvg = Tools.getAbsenceAverage(tutoredStudents);

        double tutorAverageAvg = Tools.getAverage(tutorStudents);
        double tutorAbsenceAvg = Tools.getAbsenceAverage(tutorStudents);

        for (Student student : this.tutoredStudents) {
            student.setWeight(tutoredAverageAvg, tutoredAbsenceAvg);
        }
        for (Student student : this.tutorStudents) {
            student.setWeight(tutorAverageAvg, tutorAbsenceAvg);
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
    public String getTextAssignment(boolean getGraph) {
        StringBuilder string = new StringBuilder();
        // if (getGraph) {
        //     string.append(graphSetup().toString() + "\n\n");
        // }
        string.append("assignment: " + this.getAssignment() + "\n");
        if (!waitingList.isEmpty()) {
            string.append("waiting list: " + this.waitingList);
        }
        return string.toString();
    }

    /**
     * Method that returns a textual representation of the assignment. Includes a
     * waiting list if there is one. Does not include a graph.
     * 
     * @return the assignment as text.
     */
    public String getTextAssignment() {
        return getTextAssignment(false);
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
        return this.assignmentCost;
    }
}
