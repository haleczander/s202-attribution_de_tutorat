package graphs.tests;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Classe pour tester une affectation avec un calcul utilisant les moyennes
 */
public class MoyenneCalcul {
    // tableau étudiants à aider
    private static String[][] partieN = new String[][]{{"Jean","8.63","1"},{"Harry","7.97","1"},{"Paul","10.26","1"},{"Timothé","9.81","1"}};
    
    // tableau étudiants tutorat jsp quoi 
    private static String[][] partieF = new String[][]{{"Antoine","13.64","3"},{"Romain","14.12","3"},{"Florence","16.39","2"},{"Camille","14.54","2"}};

    // listes
    ArrayList<Etudiant> listEtuN = new ArrayList<>();
    ArrayList<Etudiant> listEtuF = new ArrayList<>();

    public static void main(String[] args) {
        MoyenneCalcul ok = new MoyenneCalcul();

        for (String[] strings : partieN) {
            ok.listEtuN.add(new Etudiant(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }
        for (String[] strings : partieF) {
            ok.listEtuF.add(new Etudiant(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }

        // liste de nom parce que c'est de la merde
        List<String> nomsN = new ArrayList<>();
        List<String> nomsF = new ArrayList<>();

        for (Etudiant etudiant : ok.listEtuN) {
            nomsN.add(etudiant.nom);
        }
        for (Etudiant etudiant : ok.listEtuF) {
            nomsF.add(etudiant.nom);
        }

        // nouveau graph
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();

        // on ajoute tous les sommets (utiliser la liste à la place plus tard)
        for (String string : nomsN) {
            graph.ajouterSommet(string);
        }
        for (String string : nomsF) {
            graph.ajouterSommet(string);
        }

        // on fait un savant calcul pour faire les arêtes
        double calculN, calculF, total;
        for (Etudiant etudiantN : ok.listEtuN) {
            for (Etudiant etudiantF : ok.listEtuF) {
                calculN = etudiantN.moyenne * etudiantN.niveau;
                calculF = etudiantF.moyenne * etudiantF.niveau;
                total = calculF - calculN;
                graph.ajouterArete(etudiantN.nom, etudiantF.nom, total);
            }
        }

        // pour check
        System.out.println(graph.toString() + "\n");

        // on fait une affectation
        CalculAffectation<String> affectation = new CalculAffectation<>(graph, nomsN, nomsF);

        // pour check
        System.out.println(affectation.getAffectation() + "\n");
        System.out.println(affectation.getCout());
    }

    // faire un classement : partieN = plus mauvais en premier et partieF = meilleur en premier
    // poids des arêtes = classement partieF - classement partieN

    // probablement de la grosse merde : voir la classe Rang pour un meilleur truc :)
}
