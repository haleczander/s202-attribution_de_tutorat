package graphs.rapport;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Classe qui représente une affectation.
 * Je crois que je peux faire des graphes d'étudiants pour moins me faire chier
 * mais ça marche donc yes.
 * Et c'est plus facile pour récupérer les noms directement donc voilà.
 */
public class Affectation {
    List<Tutore> etudiantsTutores;
    List<Tuteur> etudiantsTuteurs;

    // TODO : ajouter un boolean pour prendre en charge ou pas le split d'étudiants
    public Affectation(List<Tutore> etudiantsTutores, List<Tuteur> etudiantsTuteurs) {
        this.etudiantsTutores = etudiantsTutores;
        this.etudiantsTuteurs = etudiantsTuteurs;
    }

    // TODO : changer le type d'affectation en Etudiant
    /**
     * Méthode qui fait une affectation à partir de deux listes d'étudiants
     * 
     * @return CalculAffectation<String> Le calcul d'affectation résultant
     */
    private CalculAffectation<String> affectation() {
        List<String> nomsTutores = getNameList(this.etudiantsTutores);
        List<String> nomsTuteurs = getNameList(this.etudiantsTuteurs);

        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();
        graph = graphSetup();

        return new CalculAffectation<String>(graph, nomsTutores, nomsTuteurs);
    }

    /**
     * Méthode qui crée et retourne un graphe non orienté valué à partir des deux
     * liste d'étudiants de l'objet
     * 
     * @return GrapheNonOrienteValue<String> Le graphe résultant
     */
    private GrapheNonOrienteValue<String> graphSetup() {
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();

        for (String string : getNameList(this.etudiantsTutores)) {
            graph.ajouterSommet(string);
        }
        for (String string : getNameList(this.etudiantsTuteurs)) {
            graph.ajouterSommet(string);
        }

        double poids;
        for (Etudiant tutore : this.etudiantsTutores) {
            for (Etudiant tuteur : this.etudiantsTuteurs) {
                poids = 1 / tuteur.level + 20 / tuteur.average + tutore.average / 20;
                graph.ajouterArete(tutore.name, tuteur.name, poids);
            }
        }
        return graph;
    }

    /**
     * Méthode statique qui crée une liste de noms d'étudiants à partir d'une liste
     * d'étudiant
     * 
     * @param listEtu Liste d'étudiants (tutorés ou non) dont il faut extraire les
     *                noms
     * @return List<String> La liste de noms des étudiants
     */
    public static List<String> getNameList(List<? extends Etudiant> listEtu) {
        List<String> noms = new ArrayList<>();
        for (Etudiant etu : listEtu) {
            noms.add(etu.name);
        }
        return noms;
    }

    /**
     * Méthode qui permet d'obtenir une représentation textuelle de l'affectation
     * 
     * @param getGraph 0 ou 1 booléens. Si true : représentation textuelle du graph
     *                 incluse.
     * @return String Affectation résultante sous forme textuelle.
     */
    public String obtenirAffectation(boolean getGraph) {
        StringBuilder s = new StringBuilder();
        if (getGraph) {
            s.append(graphSetup().toString() + "\n\n");
        }
        s.append("affectation: " + this.affectation().getAffectation());
        return s.toString();
    }

    public String obtenirAffectation() {
        return this.obtenirAffectation(false);
    }

    /**
     * Méthode qui retourne le coût minimal de l'affectation sous forme textuelle
     * 
     * @return String Coût minimal
     */
    public String obtenirCout() {
        return "coût total: " + this.affectation().getCout();
    }
}
