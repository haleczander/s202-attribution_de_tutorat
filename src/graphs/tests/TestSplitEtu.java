package graphs.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graphs.rapport.Tuteur;
import graphs.rapport.Tutore;

public final class TestSplitEtu {

    private TestSplitEtu() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // pour tester le split de tuteurs qui peuvent prendre plusieurs tutorés dans le cas où il manque des tuteurs
        // soon tm
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
        String[][] testF = new String[][] { { "Vincent", "1", "9.3", "2" },
            { "Jacqueline", "1", "13.2", "2" },
            { "Pénélope", "1", "13.2", "2" },
            { "Nicolas", "1", "13.1", "2" },
            { "Juliette", "1", "9.8", "2" },
            { "Sophie", "2", "15.8", "2" },
            { "Édouard", "2", "13.9", "3" },
            { "Olivier", "1", "11.3", "3" },
            /*{ "Inès", "1", "9.3", "3" },*/
            /*{ "Franck", "1", "11.9", "3" }*/ };

        // listes d'étudiants
        List<Tutore> listeN = new ArrayList<>();
        List<Tuteur> listeF = new ArrayList<>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : testN) {
            listeN.add(new Tutore(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        for (String[] strings : testF) {
            listeF.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[1])));
        }

        System.out.println(listeN.size());
        System.out.println(listeF.size());

        // test : si y'a plus de tutorés que de tuteurs, on va split un tuteur en deux pour accomoder le plus de monde
        // comment : en augmentant son poids pour éviter qu'il ait une surcharge avec deux étudiants trop faibles

        // cette partie du programme = on *tente* de faire le split pour pouvoir avoir un bon nombre de tuteurs
        // s'il y a trop de tuteur qui peuvent se split c'est pas grave parce que diff atteindra 0 avant
        // s'il n'y a pas assez de tuteur à split, bah on continue à boucler parce que j'ai pas de meilleure approche pour le moment
        // autre point à réfléchir : est ce qu'on fait un tri pour pas que n'importe quel étudiant se retrouve avec 2 tutorés
        // à méditer je vous invite à me laisser un commentaire si vous avez une idée et si quelqu'un lit mes com un jour mdr
        int diff = listeN.size() - listeF.size();
        System.out.println("diff de base : " + diff);
        while (diff > 0) {
            Iterator<Tuteur> it = listeF.iterator();
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
            System.out.println("nouvelle diff : " + diff);
        }

        // si la différence n'est toujours pas nulle bah là c'est dommage mais c'est un autre cas de figure
        if (listeN.size() - listeF.size() != 0) {
            System.out.println("Il y a trop de tutorés, il faut en enlever (" + diff + ").");
            System.out.println("Voir l'autre classe pour le cas : \"Mise en attente d'étudiants tutorés\"");
            return;
        }
            
        System.out.println("Pog");
        System.out.println(listeN.size());
        System.out.println(listeF.size());

        System.out.println(listeN.toString());
        System.out.println(listeF.toString());
    }
}
