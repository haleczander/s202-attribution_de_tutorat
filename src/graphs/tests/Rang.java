package graphs.tests;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;
import graphs.rapport.Etudiant;
import graphs.rapport.Tutore;

/**
 * Classe pour tester un affectation en triant les étudiants préalablement en fonction de leur rang
 */
public class Rang {
    // étudiants à aider (partieN)
    private static String[][] partieN = new String[][] { { "Jean", "8.63", "1" }, { "Harry", "7.97", "1" },
            { "Paul", "10.26", "1" }, { "Timothé", "9.81", "1" } };

    // étudiants aideurs (partieF)
    private static String[][] partieF = new String[][] { { "Antoine", "13.64", "3" }, { "Romain", "14.12", "3" },
            { "Florence", "16.39", "2" }, { "Camille", "14.54", "2" } };

    // attributes
    List<Etudiant> listEtuN = new ArrayList<>();
    List<Etudiant> listEtuF = new ArrayList<>();

    // /**
    //  * Trie les étudiants dans l'ordre croissant de leur réussite
    //  * @see graphs.tests.Etudiant#compareToN(Etudiant)
    //  */
    // public void sortListN() {
    //     this.listEtuN.sort((e1, e2) -> e1.compareToN(e2));
    // }

    // /**
    //  * Trie les étudiants dans l'ordre décroissant de leur réussite
    //  * @see graphs.tests.Etudiant#compareToF(Etudiant)
    //  */
    // // compare les étudiants aideurs
    // public void sortListF() {
    //     this.listEtuF.sort((e1, e2) -> e1.compareToF(e2));
    // }

    public static void main(String[] args) {
        // la classe avec les listes
        Rang ok = new Rang();

        // on fout les étudiants dans les listes
        for (String[] strings : partieN) {
            ok.listEtuN.add(new Tutore(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }

        for (String[] strings : partieF) {
            ok.listEtuF.add(new Tutore(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }

        // on fait aussi une liste de prénom envie de mourir
        List<String> nomsN = new ArrayList<>();
        for (Etudiant etudiant : ok.listEtuN) {
            nomsN.add(etudiant.name);
        }
        List<String> nomsF = new ArrayList<>();
        for (Etudiant etudiant : ok.listEtuF) {
            nomsF.add(etudiant.name);
        }

        // on affiche la liste de base, on la sort, puis on la réaffiche
        System.out.println("OG: " + ok.listEtuN.toString());
        // ok.sortListN();
        System.out.println("Sorted: " + ok.listEtuN.toString() + "\n");

        // la même mais pour les étudiants aideurs :)
        System.out.println("OG: " + ok.listEtuF.toString());
        // ok.sortListF();
        System.out.println("Sorted: " + ok.listEtuF.toString() + "\n");

        // nouveau graph
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();

        for (String string : nomsN) {
            graph.ajouterSommet(string);
        }
        for (String string : nomsF) {
            graph.ajouterSommet(string);
        }

        // on fait les arêtes : poids = rangN - rangF
        for (int i = 0; i < ok.listEtuN.size(); i++) {
            Etudiant etudiantN = ok.listEtuN.get(i);
            for (int j = 0; j < ok.listEtuF.size(); j++) {
                Etudiant etudiantF = ok.listEtuF.get(j);
                graph.ajouterArete(etudiantN.name, etudiantF.name, Math.abs(i - j));
            }
        }

        System.out.println(graph.toString() + "\n");

        // affectation
        CalculAffectation<String> blblbl = new CalculAffectation<>(graph, nomsN, nomsF);

        System.out.println(blblbl.getAffectation() + "\n");
        System.out.println(blblbl.getCout());

        // result = [Arete(Paul, Camille), Arete(Harry, Romain), Arete(Timothé, Florence), Arete(Jean, Antoine)]
        // normalement c'est bon mais j'ai pas le truc que j'avais fait chez moi donc on verra
    }
}
