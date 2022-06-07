package graphs.affectation;

import java.util.List;

import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import oop.Student;
import oop.Tutor;
import oop.Tutored;
import utility.Couples;

public class Graphs {
        /**
     * Returns an undirected weighted graph (bipartite graph) from 2 lists of
     * students.
     * 
     * @return the resulting graph.
     * 
     * @see Tutored#getWeight(double, double)
     * @see Tutor#getWeight(double, double)
     */
    public static GrapheNonOrienteValue<Student> getGraph(List<Tutored> duplicateTutored, List<Tutor> duplicateTutor, Tutorat tutorat) {
        GrapheNonOrienteValue<Student> graph = new GrapheNonOrienteValue<>();

        addVertices(graph, duplicateTutored);
        addVertices(graph, duplicateTutor);

        for (Tutored tutoreds : duplicateTutored) {
            for (Tutor tutor : duplicateTutor) {
                graph.ajouterArete(tutoreds, tutor, computeEdgeWeight(tutoreds, tutor, tutorat));
            }
        }
        return graph;
    }

    private static double computeEdgeWeight(Tutored tutored, Tutor tutor, Tutorat tutorat){
        if (Couples.exists(tutorat.getForcedCouples(), tutored, tutor)){
            return -Tutorat.getForcedAffectationWeight();
        } 
        if (Couples.exists(tutorat.getForbiddenCouples(), tutored, tutor)){
            return Tutorat.getForcedAffectationWeight();
        } 
        return tutored.getWeight(tutorat) + tutor.getWeight(tutorat);
    }

    /**
     * Adds all students that are in the list given as nodes in a given graph.
     * 
     * @param graph graph to add nodes to.
     * @param list  students to add to the graph.
     */
    private static void addVertices(GrapheNonOrienteValue<Student> graph, List<? extends Student> list) {
        for (Student student : list) {
            graph.ajouterSommet(student);
        }
    }
}
