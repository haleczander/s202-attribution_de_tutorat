package graphs.tests;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

/**
 * Classe pour tester une affectation avec un calcul utilisant les moyennes
 */
public class MoyenneCalcul {
    // tableau étudiants à aider
    private static String[][] partieN = new String[][] { { "Jean", "8.63", "1" },
            { "Harry", "7.97", "1" },
            { "Paul", "10.26", "1" },
            { "Timothé", "9.81", "1" } };

    private static String[][] testN = new String[][] { { "Claude", "Allard", "9.8", "1" },
            { "Madeleine", "Barre", "6.9", "1" },
            { "Sabine", "Besnard", "12.7", "1" },
            { "Hugues", "Bigot", "0.2", "1" },
            { "Lucas", "Bouchet", "17.3", "1" },
            { "Alexandria", "Boulay", "12.5", "1" },
            { "Anouk", "Brun", "10.5", "1" },
            { "Hortense", "Chauveau", "9.1", "1" },
            { "David", "Colin", "7.0", "1" },
            { "Amélie", "Dijoux", "9.7", "1" } };

    // tableau étudiants tutorat jsp quoi
    private static String[][] partieF = new String[][] { { "Antoine", "13.64", "3" },
            { "Romain", "14.12", "3" },
            { "Florence", "16.39", "2" },
            { "Camille", "14.54", "2" } };

    private static String[][] testF = new String[][] { { "Vincent", "Muller", "9.3", "2" },
            { "Jacqueline", "Pons", "13.2", "2" },
            { "Pénélope", "Renault", "13.2", "2" },
            { "Nicolas", "Roche", "13.1", "2" },
            { "Juliette", "Traore", "9.8", "2" },
            { "Sophie", "Vallee", "15.8", "2" },
            { "Édouard", "Auger", "13.9", "3" },
            { "Olivier", "Gallet", "11.3", "3" },
            { "Inès", "Gautier", "9.3", "3" },
            { "Franck", "Hebert", "11.9", "3" } };

    // listes
    List<Etudiant> listEtuN = new ArrayList<>();
    List<Etudiant> listEtuF = new ArrayList<>();

    public static double coeffAvg = 1;
    public static double coeffLevel = 1;
    public static double coeffAvgN = coeffAvg / 2;

    public static void main(String[] args) {
        MoyenneCalcul ok = new MoyenneCalcul();

        for (String[] strings : partieN) {
            ok.listEtuN.add(new Etudiant(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }
        for (String[] strings : partieF) {
            ok.listEtuF.add(new Etudiant(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2])));
        }

        // liste de noms parce que c'est de la merde
        List<String> nomsN = new ArrayList<>();
        List<String> nomsF = new ArrayList<>();

        for (Etudiant etudiant : ok.listEtuN) {
            nomsN.add(etudiant.name);
        }
        for (Etudiant etudiant : ok.listEtuF) {
            nomsF.add(etudiant.name);
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
                calculN = coeffAvgN * etudiantN.average;
                calculF = coeffAvg * (20 - etudiantF.average) + coeffLevel * Math.abs(etudiantF.level - 3);
                total = calculN / calculF;
                graph.ajouterArete(etudiantN.name, etudiantF.name, total);
            }
        }

        // pour check
        System.out.println(graph.toString() + "\n");

        // on fait une affectation
        CalculAffectation<String> affectation = new CalculAffectation<>(graph, nomsN, nomsF);

        // pour check
        System.out.println("affectation: " + affectation.getAffectation() + "\n");
        System.out.println("coût total: " + affectation.getCout());
    }

    // résultats avec le truc
    // coeffAvg 1 et coeffLevel 1 : affectation: [Arete(Harry, Florence),
    // Arete(Jean, Romain), Arete(Timothé, Antoine), Arete(Paul, Camille)]
    // coeffAvg 1 et coeffLevel 0 : affectation: [Arete(Harry, Florence),
    // Arete(Jean, Camille), Arete(Timothé, Romain), Arete(Paul, Antoine)]
    // coeffAvg 1 et coeffLevel 0.5: affectation: [Arete(Harry, Florence),
    // Arete(Jean, Romain), Arete(Timothé, Camille), Arete(Paul, Antoine)]
    // coeffAvg 0.75 et coeffLevel 1: affectation: [Arete(Jean, Antoine),
    // Arete(Paul, Camille), Arete(Harry, Romain), Arete(Timothé, Florence)]
    
    // idées pour la suite : faire prévaloir les étudiants qui ont des moyennes critiques
    // pour les étudiants tutorés
    // -> moyenne < 10 => moyenne compte 0.75 fois
    // -> moyenne < 8 => moyenne compte 0.5 fois (donc 2 fois plus basse)

    // pour les tuteurs : garder cette idée de coefficient pour la moyenne et pour l'année
    // je pensais peut être à faire genre comme si l'année était une moyenne sur 20 (ou moyenne sur 3)
    // et on fait la moyenne des 2 moyennes pour optenir une moyenne, puis on pourrait soustraire
    // ce résultat à la moyenne d'un étudiant tutoré.
}
