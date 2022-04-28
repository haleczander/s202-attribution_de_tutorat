package graphs.rapport;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Class that represent an assignment of two groups of students : one is
 * tutored, the other is tutoring.
 */
public class Assignment {
    private List<Tutored> TutoredStudents;
    private List<Tutor> TutorStudents;
    private boolean tutorsSplit;
    private CalculAffectation<Student> assignment;
    private List<Student> waitingList;

    /**
     * Instanciation
     * 
     * @param students    String list as follows : [name, number of students to take
     *                    in charge, average, level].
     * @param tutorsSplit True if students that can be in charge of multiple
     *                    students must do so, false othersise.
     * @throws IllegalArgumentException if the level of one of the students is not
     *                                  between 1 and 3 included.
     * @throws NumberFormatException    if one of the string cannot be converted to
     *                                  a necessary type.
     */
    public Assignment(String[][] students, boolean tutorsSplit) {
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
        this.assignment = affectation();
    }

    public Assignment(String[][] etudiants) {
        this(etudiants, true);
    }

    /**
     * Creates an assignment from 2 lists of students of the object.
     * 
     * @return CalculAffectation<Student> The resulting assignment;
     */
    private CalculAffectation<Student> affectation() {
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
     * Method that returns a textual representation of the assignment. Includes a
     * waiting list if there is one.
     * 
     * @param getGraph includs a textual representation of the bipartite graphs if
     *                 true.
     * @return String Assignment.
     */
    public String getTextAffectation(boolean getGraph) {
        StringBuilder s = new StringBuilder();
        if (getGraph) {
            s.append(graphSetup().toString() + "\n\n");
        }
        s.append("affectation: " + this.assignment.getAffectation() + "\n");
        if (!waitingList.isEmpty()) {
            s.append("waiting list: " + this.waitingList);
        }
        return s.toString();
    }

    public String getTextAffectation() {
        return this.getTextAffectation(false);
    }

    public List<Arete<Student>> getAffectation() {
        return this.assignment.getAffectation();
    }

    public List<Student> getWaitingList() {
        return List.copyOf(this.waitingList);
    }

    /**
     * Method that returns a textual representation of the minimal cost of the
     * assignment.
     * 
     * @return String Minimal cost.
     */
    public String getTextCout() {
        return "coût total: " + this.assignment.getCout();
    }

    public double getCout() {
        return this.assignment.getCout();
    }
}
