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
 * tutored, the other is tutoring.
 * 
 * @see #Assignment(String[][])
 * @see #getAssignment()
 */
public class Assignment {
    // rappel perso : D'ABORD tutoré PUIS tuteur
    private List<Tutored> TutoredStudents;
    private List<Tutor> TutorStudents;
    private boolean tutorsSplit;
    private CalculAffectation<Student> assignment;
    private List<Student> waitingList;
    private Map<Tutored, Tutor> forcedAssignments;
    private Map<Tutored, Tutor> noAssignments;

    private Assignment() {
        TutoredStudents = new ArrayList<>();
        TutorStudents = new ArrayList<>();
        this.tutorsSplit = true;
        this.forcedAssignments = new HashMap<>();
        this.noAssignments = new HashMap<>();
        this.waitingList = new ArrayList<>();
    }

    /**
     * Instantiation of an assignment.
     * 
     * @param students          String list as follows : [name, number of students
     *                          to take in charge, average, level]. Number of
     *                          students to take in change is irrelevant for tutored
     *                          students.
     * 
     * @throws IllegalArgumentException if the level of one of the students is not
     *                                  between 1 and 3 included.
     */
    public Assignment(String[][] students) throws IllegalArgumentException {
        this();

        for (String[] strings : students) {
            String name = strings[0];
            String nbOfTutored = strings[1];
            int number = 0;
            Double average;
            int level;

            try { 
                average = Double.parseDouble(strings[2]);
                level = Integer.parseInt(strings[3]);
            } catch (NumberFormatException e) {
                System.err.println("Unable to parse values.");
                throw e;
            }

            if (level == 1) {
                this.TutoredStudents.add(new Tutored(name, average));
            } else if (level == 2 || level == 3) {
                try {
                    number = Integer.parseInt(nbOfTutored);
                } catch (NumberFormatException e) {
                    System.err.println("Please enter an integer for the number of tutored students a tutor can take in charge.");
                }
                this.TutorStudents.add(new Tutor(name, average, level, number));
            } else {
                throw new IllegalArgumentException("Level of any students must be between 1 and 3 included.");
            }
        }
    }

    public Assignment(List<Tutored> tutored, List<Tutor> tutors) {
        this();

        this.TutoredStudents = tutored;
        this.TutorStudents = tutors;
    }

    public void setForcedAssignments(Map<Tutored, Tutor> forcedAssignments) {
        this.forcedAssignments = forcedAssignments;
    }

    public void setNoAssignments(Map<Tutored, Tutor> noAssignments) {
        this.noAssignments = noAssignments;
    }

    public void setTutorsSplit(boolean tutorsSplit) {
        this.tutorsSplit = tutorsSplit;
    }

    /**
     * Creates an assignment from 2 lists of students of the object.
     * 
     * @return CalculAffectation<Student> The resulting assignment;
     */
    private void assignment() {
        listArrange();

        GrapheNonOrienteValue<Student> graph = graphSetup();

        this.assignment = new CalculAffectation<Student>(graph, StreamlineUtils.getEtudiantList(TutoredStudents),
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
        int countForced = 0;
        int countNo = 50;
        for (Student tutored : this.TutoredStudents) {
            for (Student tutor : this.TutorStudents) {
                // TODO : modifier "1 / tuteur.average" en "20 / tuteur.average"
                poids = 1 / tutor.level + 1 / tutor.average + tutored.average / 20;

                for (Map.Entry<Tutored, Tutor> entrySet : this.forcedAssignments.entrySet()) {
                    if (entrySet.getKey().equals(tutored) && entrySet.getValue().equals(tutor)) {
                        poids = countForced--;
                    }
                }
                for (Map.Entry<Tutored, Tutor> entrySet : this.noAssignments.entrySet()) {
                    if (entrySet.getKey().equals(tutored) && entrySet.getValue().equals(tutor)) {
                        poids = countNo;
                        countNo = countNo + 50;
                    }
                }

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
     * Method that returns a textual representation of the assignment. Includes a
     * waiting list if there is one.
     * 
     * @param getGraph includes a textual representation of the bipartite graphs if
     *                 true.
     * @return String Assignment.
     */
    public String getTextAssignment(boolean getGraph) {
        assignment();

        StringBuilder s = new StringBuilder();
        // if (getGraph) {
        //     s.append(graphSetup().toString() + "\n\n");
        // }
        s.append("assignment: " + this.getAssignment() + "\n");
        if (!waitingList.isEmpty()) {
            s.append("waiting list: " + this.waitingList);
        }
        return s.toString();
    }

    /**
     * Method that gives acces to an <b>immutable</b> copy of a list of edges of
     * students that represent an assignment.
     * 
     * @return a copy of the assignment.
     */
    public List<Arete<Student>> getAssignment() {
        assignment();
        return List.copyOf(this.assignment.getAffectation());
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
        assignment();
        return this.assignment.getCout();
    }
}
