package graphs.rapport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Cas qui représente une affectation dans le cas ou il y a plus de tutorés que de tuteurs.
 * Explication de notre méthode au fur et à mesure en commentaire.
 */
public final class CasPlusDeTutores {

    private CasPlusDeTutores() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        // tableau d'étudiants tutorés (extrait des données pour tester)
        String[][] donneesTutores = new String[][] { { "Claude", "Allard", "9.8", "1" },
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
        String[][] donneesTuteurs = new String[][] { { "Vincent", "1", "9.3", "2" },
            { "Jacqueline", "1", "13.2", "2" },
            { "Pénélope", "1", "13.2", "2" },
            { "Nicolas", "1", "13.1", "2" },
            { "Juliette", "1", "9.8", "2" },
            { "Sophie", "2", "15.8", "2" },
            { "Édouard", "1", "13.9", "3" },
            { "Olivier", "2", "11.3", "3" },
            /*{ "Inès", "1", "9.3", "3" },*/
            /*{ "Franck", "1", "11.9", "3" }*/ };

        // listes d'étudiants
        List<Tutore> listeTutores = new ArrayList<>();
        List<Tuteur> listeTuteurs = new ArrayList<>();

        // on fou les étudiants dans une liste lol
        for (String[] strings : donneesTutores) {
            listeTutores.add(new Tutore(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3])));
        }

        // solution temporaire : pour les tuteurs, on remplace le nom par le nombre de tutorés qu'il peuvent prendre
        for (String[] strings : donneesTuteurs) {
            listeTuteurs.add(new Tuteur(strings[0], Double.parseDouble(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[1])));
        }

        // test : s'il y a plus de tutorés que de tuteurs, on va split un tuteur en deux pour accomoder le plus de monde
        // comment : en augmentant son poids pour éviter qu'il ait une surcharge avec deux étudiants trop faibles

        // cette partie du programme = on *tente* de faire le split pour pouvoir avoir un bon nombre de tuteurs
        // s'il y a trop de tuteur qui peuvent se split c'est pas grave parce que diff atteindra 0 avant
        // s'il n'y a pas assez de tuteur à split, bah on continue à boucler parce que j'ai pas de meilleure approche pour le moment
        // autre point à réfléchir : est ce qu'on fait un tri pour pas que n'importe quel étudiant se retrouve avec 2 tutorés
        // à méditer je vous invite à me laisser un commentaire si vous avez une idée et si quelqu'un lit mes com un jour mdr
        int diff = listeTutores.size() - listeTuteurs.size();
        while (diff > 0) {
            Iterator<Tuteur> it = listeTuteurs.iterator();
            while (it.hasNext()) {
                Tuteur tuteur = it.next();
                if (tuteur.nbTutores == 2) {
                    listeTuteurs.remove(tuteur);
                    listeTuteurs.add(new Tuteur(tuteur.name + "α", tuteur.average * 1.5, tuteur.level, 1));
                    listeTuteurs.add(new Tuteur(tuteur.name + "β", tuteur.average * 1.5, tuteur.level, 1));
                    break;
                }
            }
            diff--;
        }
        // TODO : modifier ce truc dégueulasse faut faire autrement là c'est pas beau
        // si on parcours toute la liste d'étudiant et que y'en a plus qui peuvent se split ça sert à rien de continuer
        // de boucler sur la principale
        // solution : mettre !it.hasNext() après le it.next(), si vrai diff = 0

        // si la différence n'est toujours pas nulle bah là c'est dommage mais c'est un autre cas de figure
        diff = listeTutores.size() - listeTuteurs.size();
        if (diff > 0) {
            System.out.println("Il y a trop de tutorés, il faut en enlever (" + diff + ").");
            System.out.println("Voir l'autre classe pour le cas : \"Mise en attente d'étudiants\".");
            return;
        } else if (listeTutores.size() - listeTuteurs.size() < 0) {
            System.out.println("Il y a trop de tuteurs, il faut en enlever (" + Math.abs(diff) + ").");
            System.out.println("Voir l'autre classe pour le cas : \"Mise en attente d'étudiants\".");
            return;
        }

        // maintenant si j'ai bien fait le truc on peut tout à fait faire la liste de nom et l'affectation
        // parce qu'on a le même nombre de tuteur et de tutorés

        Affectation affectation = new Affectation(listeTutores, listeTuteurs);
        System.out.println(affectation.obtenirAffectation(true) + "\n");
        System.out.println(affectation.obtenirCout() + "\n");
    }
}
