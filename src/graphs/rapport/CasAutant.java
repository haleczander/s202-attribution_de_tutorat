package graphs.rapport;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class CasAutant {
    // liste des étudiants tutorés : extrait des données pour tester
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

    // liste des étudiants tuteurs : extrait des données poir tester
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

    // création des listes d'étudiants
    List<Etudiant> listEtuN = new ArrayList<>();
    List<Etudiant> listEtuF = new ArrayList<>();

    public static void main(String[] args) {
        // on fait l'objet parce que c'est moins chiant mais on peut tout faire dans le main
        // je le referais plus tard avec tout dans le main mais là je voulais pondre un truc rapidement
        CasAutant cas = new CasAutant();

        // je transforme le tableau de strings en étudiants et je les fous dans la liste
        for (String[] strings : testN) {
            cas.listEtuN.add(new Etudiant(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        for (String[] strings : testF) {
            cas.listEtuF.add(new Etudiant(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        // on fait des listes avec les prénoms des étudiants c'est un peu obligé pour instancier l"affectation
        List<String> nomsN = new ArrayList<>();
        List<String> nomsF = new ArrayList<>();

        for (Etudiant etudiant : cas.listEtuN) {
            nomsN.add(etudiant.name);
        }

        for (Etudiant etudiant : cas.listEtuF) {
            nomsF.add(etudiant.name);
        }

        // nouveau graph!!!
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<>();

        // ajouter les noms en tant que sommet du graph
        for (String string : nomsN) {
            graph.ajouterSommet(string);
        }

        for (String string : nomsF) {
            graph.ajouterSommet(string);
        }

        // calcul du poids de l'affecation PUIS  on ajoute l'arête entre les étudiants
        double poids;
        for (Etudiant etudiantN : cas.listEtuN) {
            for (Etudiant etudiantF : cas.listEtuF) {
                poids = (1 / etudiantF.level) + (1 / etudiantF.average) + (etudiantN.average / 20);
                graph.ajouterArete(etudiantN.name, etudiantF.name, poids);
            }
        }

        // on affiche le graphe juste pour voir
        System.out.println(graph.toString() + "\n");

        // on fait une petite affectation!
        CalculAffectation<String> affectation = new CalculAffectation<>(graph, nomsN, nomsF);

        // on affiche l'affectation
        System.out.println("affectation: " + affectation.getAffectation() + "\n");
        System.out.println("coût total: " + affectation.getCout());

        // affectation: [Arete(David, Franck), Arete(Madeleine, Vincent), Arete(Hugues,
        // Pénélope), Arete(Amélie, Olivier), Arete(Alexandria, Inès), Arete(Claude,
        // Juliette), Arete(Lucas, Édouard), Arete(Sabine, Nicolas), Arete(Hortense,
        // Sophie), Arete(Anouk, Jacqueline)]
        // le cas le plus simple : même nombre de tuteurs et de tutorés dans voilà
        // c'est pas mal!
        // la suite : soon tm (vraiment parce qu'on a presque plus de temps)
    }
}
