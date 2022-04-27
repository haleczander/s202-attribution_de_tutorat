package graphs.rapport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class CasPlus {
    public static void main(String[] args) {
        // tableau d'étudiants tutorés (extrait des données pour tester)
        String[][] testN = new String[][] { { "Claude", "Allard", "9.8", "1" },
            { "Madeleine", "Barre", "6.9", "1" },
            { "Sabine", "Besnard", "12.7", "1" },
            { "Hugues", "Bigot", "0.2", "1" },
            { "Lucas", "Bouchet", "17.3", "1" },
            { "Alexandria", "Boulay", "12.5", "1" },
            { "Anouk", "Brun", "10.5", "1" },
            { "Hortense", "Chauveau", "9.1", "1" },
            { "David", "Colin", "7.0", "1" },
            { "Amélie", "Dijoux", "9.7", "1" } };

        // tableau d'étudiants tuteurs (extrait des données pour tester)
        String[][] testF = new String[][] { { "Vincent", "Muller", "9.3", "2" },
            { "Jacqueline", "Pons", "13.2", "2" },
            { "Pénélope", "Renault", "13.2", "2" },
            { "Nicolas", "Roche", "13.1", "2" },
            { "Juliette", "Traore", "9.8", "2" },
            { "Sophie", "Vallee", "15.8", "2" },
            { "Édouard", "Auger", "13.9", "3" },
            { "Olivier", "Gallet", "11.3", "3" },
            { "Inès", "Gautier", "9.3", "3" },
            { "Franck", "Hebert", "11.9", "3" } };

        // listes d'étudiants
        List<Etudiant> listeN = new ArrayList<Etudiant>();
        List<Tuteur> listeF = new ArrayList<Tuteur>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : testN) {
            listeN.add(new Etudiant(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        for (String[] strings : testF) {
            listeF.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), 1));
        }

        // test : si y'a plus de tutorés que de tuteurs, on va split un tuteur en deux pour accomoder le plus de monde
        // comment : en augmentant son poids pour éviter qu'il ait une surcharge avec deux étudiants trop faibles

        // cette partie du programme = on *tente* de faire le split pour pouvoir avoir un bon nombre de tuteurs
        // s'il y a trop de tuteur qui peuvent se split c'est pas grave parce que diff atteindra 0 avant
        // s'il n'y a pas assez de tuteur à split, bah on continue à boucler parce que j'ai pas de meilleure approche pour le moment
        // autre point à réfléchir : est ce qu'on fait un tri pour pas que n'importe quel étudiant se retrouve avec 2 tutorés
        // à méditer je vous invite à me laisser un commentaire si vous avez une idée et si quelqu'un lit mes com un jour mdr
        int diff = listeN.size() - listeF.size();
        Iterator<Tuteur> it = listeF.iterator();
        while (diff > 0) {
            while (it.hasNext()) {
                Tuteur t = it.next();
                if (t.nbTutores == 2) {
                    listeF.remove(t);
                    listeF.add(new Tuteur(t.name, t.average, t.level, 1));
                    listeF.add(new Tuteur(t.name, t.average, t.level, 1));
                    break;
                }
            }
            diff--;
        }

        // si la différence n'est toujours pas nulle bah là c'est dommage mais c'est un autre cas de figure
        if (listeN.size() - listeF.size() != 0) {
            System.out.println("Il y a trop de tutorés, il faut en enlever (" + diff + ").");
            System.out.println("Voir l'autre classe pour le cas : \"Mise en attente d'étudiants tutorés\"");
            return;
        }

        // maintenant si j'ai bien fait le truc on peut tout à fait faire la liste de nom et l'affectation
        // parce qu'on a le même nombre de tuteur et de tutorés

        // on crée la liste de nom
        List<String> listeNomN = new ArrayList<String>();
        for (Etudiant e : listeN) {
            listeNomN.add(e.name);
        }

        List<String> listeNomF = new ArrayList<String>();
        for (Tuteur t : listeF) {
            listeNomF.add(t.name);
        }

        // nouveau graph
        GrapheNonOrienteValue<String> graph = new GrapheNonOrienteValue<String>();

        // on ajoute les sommets
        for (String string : listeNomN) {
            graph.ajouterSommet(string);
        }

        for (String string : listeNomF) {
            graph.ajouterSommet(string);
        }

        // calcul du poids et on met les arêtes dans le graphe voilà classique
        // redondant donc faudra le foutre dans une classe un truc comme ça mais bon pour l'instant osef
        double poids;
        for (Etudiant etudiantN : listeN) {
            for (Etudiant etudiantF : listeF) {
                poids = (1 / etudiantF.level) + (1 / etudiantF.average) + (etudiantN.average / 20);
                graph.ajouterArete(etudiantN.name, etudiantF.name, poids);
            }
        }

        // on affiche tjr pour voir mieux
        System.out.println(graph);

        // c'est parti pour l'affectation
        CalculAffectation<String> affectation = new CalculAffectation<>(graph, listeNomN, listeNomF);

        // on affiche tout
        System.out.println("affectation: " + affectation.getAffectation() + "\n");
        System.out.println("coût total: " + affectation.getCout());
    }
}
