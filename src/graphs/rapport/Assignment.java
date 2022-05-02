package graphs.rapport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Class that represent an assignment of two groups of students : one is
 * tutored, the other is tutoring.
 * 
 * @see #Assignment(String[][], boolean, Map)
 * @see #getAssignment()
 */
public class Assignment {
    private List<Tutored> TutoredStudents;
    private List<Tutor> TutorStudents;
    private boolean tutorsSplit;
    private CalculAffectation<Student> assignment;
    private List<Student> waitingList;
    private Map<String, String> forcedAssignments;
    private List<Arete<Student>> pendingEdges;

    /**
     * Instantiation of an assignment. Assignment will be done right away with or
     * without spliting tutors depending on the need to do so and/or the parameter
     * specified, and students in the Map specified will be assigned manually.
     * 
     * @param students          String list as follows : [name, number of students
     *                          to take in charge, average, level]. Number of
     *                          students to take in change is irrelevant for tutored
     *                          students.
     * @param tutorsSplit       True if students that can be in charge of multiple
     *                          students must do so, false othersise.
     * @param forcedAssignments Map of Tutored and Tutor students (in this order) to
     *                          be assigned together.
     * @throws IllegalArgumentException if the level of one of the students is not
     *                                  between 1 and 3 included.
     * @throws NumberFormatException    if one of the string cannot be converted to
     *                                  a necessary type.
     */
    public Assignment(String[][] students, boolean tutorsSplit, Map<String, String> forcedAssignments) {
        TutoredStudents = new ArrayList<>();
        TutorStudents = new ArrayList<>();

        for (String[] strings : students) {
            String level = strings[3];

            if ("1".equals(level)) {
                TutoredStudents
                        .add(new Tutored(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
            } else if ("2".equals(level) || "3".equals(level)) {
                TutorStudents.add(new Tutor(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]),
                        Integer.parseInt(strings[1])));
            } else {
                throw new IllegalArgumentException("Please enter a valid level (1-3)");
            }
        }
        this.tutorsSplit = tutorsSplit;
        this.forcedAssignments = forcedAssignments;
        this.pendingEdges = new ArrayList<>();
        this.waitingList = new ArrayList<>();
        this.assignment = assignment();
    }

    /**
     * Instantiate an assignment with {@code tutorsSplit} and
     * {@code forcedAssignments} default values (true and an empty Map).
     * 
     * @param students String list as follows : [name, number of students to take in
     *                 charge, average, level]. Number of students to take in change
     *                 is irrelevant for tutored students.
     * 
     * @see #Assignment(String[][], boolean, Map)
     */
    public Assignment(String[][] students) {
        this(students, true, new HashMap<String, String>());
    }

    /**
     * Instantiate an assignment with {@code forcedAssignment} default value (empty
     * Map)
     * 
     * @param students    String list as follows : [name, number of students to take
     *                    in charge, average, level]. Number of students to take in
     *                    change is irrelevant for tutored students.
     * @param tutorsSplit True if students that can be in charge of multiple
     *                    students must do so, false othersise.
     * 
     * @see #Assignment(String[][], boolean, Map)
     */
    public Assignment(String[][] students, boolean tutorsSplit) {
        this(students, tutorsSplit, new HashMap<String, String>());
    }

    /**
     * Instantiate an assignment with {@code tutorsSplit} default value (true)
     * 
     * @param students          String list as follows : [name, number of students
     *                          to take in charge, average, level]. Number of
     *                          students to take in change is irrelevant for tutored
     *                          students.
     * @param forcedAssignments Map of Tutored and Tutor students (in this order) to
     *                          be assigned together.
     * 
     * @see #Assignment(String[][], boolean, Map)
     */
    public Assignment(String[][] students, Map<String, String> forcedAssignments) {
        this(students, true, forcedAssignments);
    }

    /**
     * Creates an assignment from 2 lists of students of the object.
     * 
     * @return CalculAffectation<Student> The resulting assignment;
     */
    private CalculAffectation<Student> assignment() {
        assignmentForce();

        listArrange();

        GrapheNonOrienteValue<Student> graph = graphSetup();

        return new CalculAffectation<Student>(graph, StreamlineUtils.getEtudiantList(TutoredStudents),
                StreamlineUtils.getEtudiantList(TutorStudents));
    }

    /**
     * Method that returns a undirected weighted graph (bipartite graphs) from 2
     * lists of students of the object.
     * 
     * @return GrapheNonOrienteValue<String> Resulting graph.
     */
    private GrapheNonOrienteValue<Student> graphSetup() {
        GrapheNonOrienteValue<Student> graph = new GrapheNonOrienteValue<>();

        for (Student student : this.TutoredStudents) {
            graph.ajouterSommet(student);
        }
        for (Student student : this.TutorStudents) {
            graph.ajouterSommet(student);
        }

        double poids;
        for (Student tutored : this.TutoredStudents) {
            for (Student tutor : this.TutorStudents) {
                // TODO : modifier "1 / tuteur.average" en "20 / tuteur.average"
                poids = 1 / tutor.level + 1 / tutor.average + tutored.average / 20;
                graph.ajouterArete(tutored, tutor, poids);
            }
        }
        return graph;
    }

    /**
     * Modifies the lists of students of the object in order to have 2 lists of
     * the same size and suitable for an assignment.
     * 
     * @see graphs.rapport.StreamlineUtils#tutorsSplit(List, int)
     * @see graphs.rapport.StreamlineUtils#waitingListBuilder(List, int)
     */
    private void listArrange() {
        int diff = TutoredStudents.size() - TutorStudents.size();

        if (tutorsSplit) {
            this.TutorStudents = StreamlineUtils.tutorsSplit(TutorStudents, diff);
        }

        diff = TutoredStudents.size() - TutorStudents.size();
        if (diff > 0) {
            // tutored in waiting list
            waitingList = StreamlineUtils.waitingListBuilder(TutoredStudents, Math.abs(diff));
        } else if (diff < 0) {
            // tutors in waiting list
            waitingList = StreamlineUtils.waitingListBuilder(TutorStudents, Math.abs(diff));
        }

        diff = TutoredStudents.size() - TutorStudents.size();
        if (diff != 0) {
            throw new NullPointerException("marche pas dommage");
        }
    }

    /**
     * Forces an assignment by creating an edge between 2 students and removing them
     * of their respective lists. Assignment is based on {@link #forcedAssignments}
     * Map.
     */
    private void assignmentForce() {
        Set<Entry<String, String>> set = this.forcedAssignments.entrySet();
        for (Map.Entry<String, String> entry : set) {
            Tutored tutored = (Tutored) StreamlineUtils.retrieveStudent(entry.getKey(), this.TutoredStudents);
            Tutor tutor = (Tutor) StreamlineUtils.retrieveStudent(entry.getValue(), this.TutorStudents);
            Arete<Student> arete = new Arete<Student>(tutored, tutor);
            this.pendingEdges.add(arete);
            this.TutoredStudents.remove(tutored);
            this.TutorStudents.remove(tutor);
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
        StringBuilder s = new StringBuilder();
        if (getGraph) {
            s.append(graphSetup().toString() + "\n\n");
        }
        s.append("assignment: " + this.getAssignment() + "\n");
        if (!waitingList.isEmpty()) {
            s.append("waiting list: " + this.waitingList);
        }
        return s.toString();
    }

    /**
     * Returns a textual representation of the assignment with {@code getGraph}
     * default value (false).
     * 
     * @return String Assignment.
     * 
     * @see #getTextAssignment(boolean)
     */
    public String getTextAssignment() {
        return this.getTextAssignment(false);
    }

    /**
     * Method that gives acces to an <b>immutable</b> copy of a list of edges of
     * students that represent an assignment.
     * 
     * @return a copy of the assignment.
     */
    public List<Arete<Student>> getAssignment() {
        List<Arete<Student>> aretes = this.assignment.getAffectation();
        aretes.addAll(this.pendingEdges);
        return List.copyOf(aretes);
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
        return "cost: " + this.assignment.getCout();
    }

    /**
     * Returns the minimal cost of the assignment.
     * 
     * @return double Minimal cost.
     */
    public double getCost() {
        return this.assignment.getCout();
    }

    /**
     * Reassign the current assignment and <b>overwrites it</b> using a new Map of
     * manually added assignments. Please beware and make sure you have no need of
     * the previous assignment anymore as it will unretrievable.
     * 
     * @param manualAssignments new assignments.
     */
    public void changeManualAssignments(Map<String, String> manualAssignments) {
        for (Arete<Student> edge : this.pendingEdges) {
            Tutored tutored = (Tutored) edge.getExtremite1();
            Tutor tutor = (Tutor) edge.getExtremite2();
            this.TutoredStudents.add(tutored);
            this.TutorStudents.add(tutor);
        }
        this.waitingList.clear();
        this.pendingEdges.clear();
        this.forcedAssignments = manualAssignments;
        assignment();
    }

    /**
     * Reassign the current assignment with no manual assignment.
     * 
     * @see #changeManualAssignments(Map)
     */
    public void changeManualAssignments() {
        this.changeManualAssignments(new HashMap<>());
    }
}
