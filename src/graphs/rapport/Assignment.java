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
 * @see #Assignment(String[][])
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
    private boolean tutorsSplit;

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
    private Map<Tutored, Tutor> noAssignments;

    private Assignment() {
        this.tutorsSplit = true;
        this.manualAssignments = new HashMap<>();
        this.noAssignments = new HashMap<>();
        this.waitingList = new ArrayList<>();
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
     * Sets a Map of duos of students to be manually assigned.
     * 
     * @param forcedAssignments the Map of tutored and tutor students to assign.
     */
    public void setForcedAssignments(Map<Tutored, Tutor> forcedAssignments) {
        this.manualAssignments = forcedAssignments;
    }

    /**
     * Sets a Map of duos of students to prevent assignment.
     * 
     * @param noAssignments the Map of tutored and tutor students to not assign.
     */
    public void setNoAssignments(Map<Tutored, Tutor> noAssignments) {
        this.noAssignments = noAssignments;
    }

    /**
     * Sets whether or not tutors should be split if needed.
     * 
     * @param tutorsSplit true if tutors should be doubled, false otherwise.
     */
    public void setTutorsSplit(boolean tutorsSplit) {
        this.tutorsSplit = tutorsSplit;
    }

    /**
     * Creates an assignment from 2 lists of students of the object.
     * 
     * @return CalculAffectation<Student> The resulting assignment;
     */
    private CalculAffectation<Student> assignment() {
        listArrange();

        GrapheNonOrienteValue<Student> graph = graphSetup();

        return new CalculAffectation<>(graph, Tools.getStudentList(tutoredStudents),
                Tools.getStudentList(tutorStudents));
    }

    /**
     * Method that returns a undirected weighted graph (bipartite graphs) from 2
     * lists of students of the object. Also uses
     * 
     * @return the resulting graph.
     */
    private GrapheNonOrienteValue<Student> graphSetup() {
        GrapheNonOrienteValue<Student> graph = new GrapheNonOrienteValue<>();

        for (Student student : this.tutoredStudents) {
            graph.ajouterSommet(student);
        }
        for (Student student : this.tutorStudents) {
            graph.ajouterSommet(student);
        }

        double weight;
        for (Tutored tutored : this.tutoredStudents) {
            for (Tutor tutor : this.tutorStudents) {
                weight = Tools.calcul(tutored, tutor);

                // TODO : virer cette merde autre part. c'est dégueulasse.
                for (Map.Entry<Tutored, Tutor> entrySet : this.manualAssignments.entrySet()) {
                    if (entrySet.getKey().equals(tutored) && entrySet.getValue().equals(tutor)) {
                        weight = -1;
                    }
                }

                for (Map.Entry<Tutored, Tutor> entrySet : this.noAssignments.entrySet()) {
                    if (entrySet.getKey().equals(tutored) && entrySet.getValue().equals(tutor)) {
                        weight = 50;
                    }
                }

                graph.ajouterArete(tutored, tutor, weight);
            }
        }
        return graph;
    }

    /**
     * Modifies the lists of students of the object in order to have 2 lists of
     * the same size and suitable for an assignment.
     * 
     * @see graphs.rapport.Tools#tutorsSplit(List, int)
     * @see graphs.rapport.Tools#waitingListBuilder(List, int)
     */
    private void listArrange() {
        int diff = tutoredStudents.size() - tutorStudents.size();

        if (tutorsSplit) {
            this.tutorStudents = Tools.tutorsSplit(tutorStudents, diff);
        }

        diff = tutoredStudents.size() - tutorStudents.size();
        if (diff > 0) {
            // tutored in waiting list
            waitingList = Tools.waitingListBuilder(tutoredStudents, Math.abs(diff));
        } else if (diff < 0) {
            // tutors in waiting list
            waitingList = Tools.waitingListBuilder(tutorStudents, Math.abs(diff));
        }

        diff = tutoredStudents.size() - tutorStudents.size();
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
     * @return String Assignment.
     */
    public String getTextAssignment(boolean getGraph) {
        StringBuilder string = new StringBuilder();
        if (getGraph) {
            string.append(graphSetup().toString() + "\n\n");
        }
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
     * @return String Assignment.
     */
    public String getTextAssignment() {
        return getTextAssignment(false);
    }

    /**
     * Method that gives acces to an <b>immutable</b> copy of a list of edges of
     * students that represent an assignment.
     * 
     * @return a copy of the assignment.
     */
    public List<Arete<Student>> getAssignment() {
        return List.copyOf(this.assignment().getAffectation());
    }

    /**
     * Method that returns an <b>immutable</b> copy of the waiting list of an
     * assignment.
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
     * @return String Minimal cost.
     */
    public String getTextCost() {
        return "cost: " + getCost();
    }

    /**
     * Returns the minimal cost of the assignment.
     * 
     * @return double Minimal cost.
     */
    public double getCost() {
        return this.assignment().getCout();
    }
}
